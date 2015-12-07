package fr.unantes.uima.mauilibrary.notworking;

import java.util.Enumeration;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

import org.apache.uima.UIMAFramework;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import com.entopix.maui.filters.MauiPhraseFilter;
import com.entopix.maui.filters.NumbersFilter;
import com.entopix.maui.filters.MauiFilter.MauiFilterException;
import com.entopix.maui.util.Candidate;
import com.entopix.maui.util.Counter;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;

public class MauiFilterV1 extends Filter implements SharedResourceObject {

	Logger logger = UIMAFramework.getLogger(MauiFilterV1.class);
	
	/**
	 * The dictionary containing the document frequencies
	 */
	public HashMap<String, Counter> globalDictionary = null;
	
	/**
	 * The actual classifier used to compute probabilities
	 */
	public Classifier classifier = null;

	/**
	 * Template for the classifier data
	 */
	private Instances classifierData = null;
	
	/**
	 * Index of attribute containing the name of the file
	 */
	public int fileNameAtt = 0;

	/**
	 * Index of attribute containing the documents
	 */
	public int documentAtt = 1;

	/**
	 * Index of attribute containing the keyphrases
	 */
	public int keyphrasesAtt = 2;

	/**
	 * The number of features describing a phrase
	 */
	private int numFeatures = 13;

	/**
	 * The punctuation filter used by this filter
	 */
	private MauiPhraseFilter phraseFilter = null;

	/**
	 * The numbers filter used by this filter
	 */
	private NumbersFilter numbersFilter = null;

	/**
	 * Maximum length of phrases
	 */
	public int maxPhraseLength = 5;

	/**
	 * Minimum length of phrases
	 */
	public int minPhraseLength = 1;

	/**
	 * Number of human indexers (times a keyphrase appears in the keyphrase set)
	 */
	public int numIndexers = 1;

	/**
	 * Is class value nominal or numeric? *
	 */
	public boolean nominalClassValue = true;

	/**
	 * The minimum number of occurences of a phrase
	 */
	public int minOccurFrequency = 1;

	/**
	 * The number of documents in the global frequencies corpus
	 */
	public int numDocs = 0;

	/** Indices of attributes in classifierData */
	// General features
	public int tfIndex = 0; // term frequency (freq feature)
	public int idfIndex = 1; // inverse document frequency (freq feature)
	public int tfidfIndex = 2; // TFxIDF (basic)
	public int firstOccurIndex = 3; // position of the first occurrence (basic)
	public int lastOccurIndex = 4; // position of the last occurrence (position)
	public int spreadOccurIndex = 5; // spread of occurrences (position)
	public int domainKeyphIndex = 6; // domain keyphraseness
	public int lengthIndex = 7; // term length
	public int generalityIndex = 8; // generality

	/**
	 * Returns the index of the normalized candidate form in the output ARFF
	 * file.
	 */
	public int getNormalizedFormIndex() {
		return documentAtt;
	}

	/**
	 * Returns the index of the most frequent form for the candidate topic or
	 * the original form of it in the vocabulary in the output ARFF file.
	 */
	public int getOutputFormIndex() {
		return documentAtt;
	}

	/**
	 * Returns the index of the candidates' probabilities in the output ARFF
	 * file.
	 */
	public int getProbabilityIndex() {
		// 2 indexes for phrase forms
		return documentAtt + numFeatures + 1;
	}

	/**
	 * Returns the index of the candidates' ranks in the output ARFF file.
	 */
	public int getRankIndex() {
		return getProbabilityIndex() + 1;
	}

	public int getDocumentAtt() {
		return documentAtt;
	}

	public void setDocumentAtt(int documentAtt) {
		this.documentAtt = documentAtt;
	}

	public int getKeyphrasesAtt() {
		return keyphrasesAtt;
	}

	public void setKeyphrasesAtt(int keyphrasesAtt) {
		this.keyphrasesAtt = keyphrasesAtt;
	}

	
	/////////////////////////// ADDITION
	transient HashMap<Instance, HashMap<String, Integer>> hashKeyphrasesDict = null;
	transient HashMap<Instance, HashMap<String, Candidate>> allCandidates = null;
	
	
	///////////////////////////////:
	/**
	 * Input an instance for filtering. Ordinarily the instance is processed and
	 * made available for output immediately. Some filters require all instances
	 * be read before producing output.
	 * 
	 * @param instance
	 *            the input instance
	 * @return true if the filtered instance may now be collected with output()
	 *         ie if we are in the testing phase.
	 * @exception Exception
	 *                if the input instance was not of the correct format or if
	 *                there was a problem with the filtering.
	 */
	@SuppressWarnings("unchecked")
	public boolean input(Instance instance) throws Exception {

		if (getInputFormat() == null) {
			throw new Exception("No input instance format defined");
		}
		if (m_NewBatch) {
			resetQueue();
			m_NewBatch = false;
		}

		try {
			phraseFilter.input(instance);
			phraseFilter.batchFinished();
			instance = phraseFilter.output();
		} catch (Exception e) {
			throw new Exception("Error applying PhraseFilter ");
		}

		try {
			numbersFilter.input(instance);
			numbersFilter.batchFinished();
			instance = numbersFilter.output();
		} catch (Exception e) {
			throw new Exception("Error applying NumbersFilter ");
		}

		if (globalDictionary == null) {

			bufferInput(instance);
			return false;

		} else {

			FastVector vector = convertInstance(instance, false);
			Enumeration<Instance> en = vector.elements();
			while (en.hasMoreElements()) {
				Instance inst = en.nextElement();
				push(inst);
			}
			return true;
		}

	}

	// -------------------------------
	// Convert instance
	// ----------------------------------

	// 1. Case of training
	/**
	 * Sets output format and converts pending input instances.
	 */
	@SuppressWarnings("unchecked")
	private void convertPendingInstances() {

		logger.log(Level.INFO, "--- Converting pending instances");

		// Create output format for filter
		FastVector atts = new FastVector();
		for (int i = 1; i < getInputFormat().numAttributes(); i++) {
			if (i == documentAtt) {
				atts.addElement(new Attribute("Candidate_name",
						(FastVector) null)); // 0
				atts.addElement(new Attribute("Candidate_original",
						(FastVector) null)); // 1

				atts.addElement(new Attribute("Term_frequency")); // 0
				atts.addElement(new Attribute("IDF")); // 1
				atts.addElement(new Attribute("TFxIDF")); // 2
				atts.addElement(new Attribute("First_occurrence")); // 3
				atts.addElement(new Attribute("Last_occurrence")); // 4
				atts.addElement(new Attribute("Spread")); // 5
				atts.addElement(new Attribute("Domain_keyphraseness")); // 6
				atts.addElement(new Attribute("Length")); // 7
				atts.addElement(new Attribute("Generality")); // 8
				atts.addElement(new Attribute("Node_degree")); // 9
				atts.addElement(new Attribute("Wikipedia_keyphraseness")); // 10
				atts.addElement(new Attribute("Wikipedia_inlinks")); // 11
				atts.addElement(new Attribute("Wikipedia_generality")); // 12

				atts.addElement(new Attribute("Probability")); // 16
				atts.addElement(new Attribute("Rank")); // 17

			} else if (i == keyphrasesAtt) {
				if (nominalClassValue) {
					FastVector vals = new FastVector(2);
					vals.addElement("False");
					vals.addElement("True");
					atts.addElement(new Attribute("Keyphrase?", vals));
				} else {
					atts.addElement(new Attribute("Keyphrase?"));
				}
			} else {
				atts.addElement(getInputFormat().attribute(i));
			}
		}

		Instances outFormat = new Instances("mauidata", atts, 0);
		setOutputFormat(outFormat);

		// Convert pending input instances into output data
		for (int i = 0; i < getInputFormat().numInstances(); i++) {
			Instance current = getInputFormat().instance(i);
			FastVector vector = convertInstance(current, true);
			Enumeration<Instance> en = vector.elements();
			while (en.hasMoreElements()) {
				Instance inst = (Instance) en.nextElement();
				push(inst);
			}
		}
	}

	// 2. Case of testing (..., false)
	
	/**
	 * Converts an instance into a FastVector
	 */
	private FastVector convertInstance(Instance instance, boolean training) {

		FastVector vector = new FastVector();

		String fileName = instance.stringValue(fileNameAtt);

		logger.log(Level.INFO, "-- Converting instance for document " + fileName);

		// Get the key phrases for the document
		HashMap<String, Integer> hashKeyphrases = null;

		if (!instance.isMissing(keyphrasesAtt)) {
			hashKeyphrases = hashKeyphrasesDict.get(instance); // XXX Addition
		}

		// Get the document text
		String documentText = instance.stringValue(documentAtt);

		// Compute the candidate topics
		HashMap<String, Candidate> candidateList;
		if (allCandidates != null && allCandidates.containsKey(instance)) {
			candidateList = allCandidates.get(instance);
		} else {
			throw new RuntimeException("Candidates are missing"); 
		}

		logger.log(Level.INFO, candidateList.size() + " candidates ");

		// Set indices for key attributes
		int tfidfAttIndex = documentAtt + 2;
		int distAttIndex = documentAtt + 3;
		int probsAttIndex = documentAtt + numFeatures;

		int countPos = 0;
		int countNeg = 0;

		// Go through the phrases and convert them into instances
		for (Candidate candidate : candidateList.values()) {

			if (candidate.getFrequency() < minOccurFrequency) {
				continue;
			}

			String name = candidate.getName();
			String orig = candidate.getBestFullForm();

			double[] vals = computeFeatureValues(candidate, training,
					hashKeyphrases, candidateList);

			Instance inst = new Instance(instance.weight(), vals);

			inst.setDataset(classifierData);

			double[] probs = null;
			try {
				// Get probability of a phrase being key phrase
				probs = classifier.distributionForInstance(inst);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Exception while getting probability for candidate "
						+ candidate.getName());
				continue;
			}

			double prob = probs[0];
			if (nominalClassValue) {
				prob = probs[1];
			}

			// Compute attribute values for final instance
			double[] newInst = new double[instance.numAttributes()
					+ numFeatures + 2];

			int pos = 0;
			for (int i = 1; i < instance.numAttributes(); i++) {

				if (i == documentAtt) {

					// output of values for a given phrase:

					// 0 Add phrase
					int index = outputFormatPeek().attribute(pos)
							.addStringValue(name);
					newInst[pos++] = index;

					// 1 Add original version
					if (orig != null) {
						index = outputFormatPeek().attribute(pos)
								.addStringValue(orig);
					} else {
						index = outputFormatPeek().attribute(pos)
								.addStringValue(name);
					}

					// 2
					newInst[pos++] = index;

					// Add features
					newInst[pos++] = inst.value(tfIndex); // 3
					newInst[pos++] = inst.value(idfIndex); // 4
					newInst[pos++] = inst.value(tfidfIndex); // 5
					newInst[pos++] = inst.value(firstOccurIndex); // 6
					newInst[pos++] = inst.value(lastOccurIndex); // 7
					newInst[pos++] = inst.value(spreadOccurIndex); // 8
					newInst[pos++] = inst.value(domainKeyphIndex); // 9
					newInst[pos++] = inst.value(lengthIndex); // 10
					newInst[pos++] = inst.value(generalityIndex); // 11
					newInst[pos++] = 0; //inst.value(nodeDegreeIndex); // 12
					newInst[pos++] = 0; //inst.value(invWikipFreqIndex); // 13
					newInst[pos++] = 0; //inst.value(totalWikipKeyphrIndex); // 14
					newInst[pos++] = 0; //inst.value(wikipGeneralityIndex); // 15

					// Add probability
					probsAttIndex = pos;
					newInst[pos++] = prob; // 16

					// Set rank to missing (computed below)
					newInst[pos++] = Instance.missingValue(); // 17

				} else if (i == keyphrasesAtt) {
					newInst[pos++] = inst.classValue();
				} else {
					newInst[pos++] = instance.value(i);
				}
			}

			Instance ins = new Instance(instance.weight(), newInst);
			ins.setDataset(outputFormatPeek());
			vector.addElement(ins);

			if (inst.classValue() == 0) {
				countNeg++;
			} else {
				countPos++;
			}

		}

		// Sort phrases according to their distance (stable sort)
		double[] vals = new double[vector.size()];
		for (int i = 0; i < vals.length; i++) {
			vals[i] = ((Instance) vector.elementAt(i)).value(distAttIndex);
		}
		FastVector newVector = new FastVector(vector.size());
		int[] sortedIndices = Utils.stableSort(vals);
		for (int i = 0; i < vals.length; i++) {
			newVector.addElement(vector.elementAt(sortedIndices[i]));
		}
		vector = newVector;

		// Sort phrases according to their tfxidf value (stable sort)
		for (int i = 0; i < vals.length; i++) {
			vals[i] = -((Instance) vector.elementAt(i)).value(tfidfAttIndex);
		}
		newVector = new FastVector(vector.size());
		sortedIndices = Utils.stableSort(vals);
		for (int i = 0; i < vals.length; i++) {
			newVector.addElement(vector.elementAt(sortedIndices[i]));
		}
		vector = newVector;

		// Sort phrases according to their probability (stable sort)
		for (int i = 0; i < vals.length; i++) {
			vals[i] = 1 - ((Instance) vector.elementAt(i)).value(probsAttIndex);
		}
		newVector = new FastVector(vector.size());
		sortedIndices = Utils.stableSort(vals);
		for (int i = 0; i < vals.length; i++) {
			newVector.addElement(vector.elementAt(sortedIndices[i]));
		}
		vector = newVector;

		// Compute rank of phrases. Check for subphrases that are ranked
		// lower than superphrases and assign probability -1 and set the
		// rank to Integer.MAX_VALUE
		int rank = 1;
		for (int i = 0; i < vals.length; i++) {
			Instance currentInstance = (Instance) vector.elementAt(i);

			// log.info(vals[i] + "\t" + currentInstance);

			// Short cut: if phrase very unlikely make rank very low and
			// continue
			if (Utils.grOrEq(vals[i], 1.0)) {
				currentInstance.setValue(probsAttIndex + 1, Integer.MAX_VALUE);
				continue;
			}

			// Otherwise look for super phrase starting with first phrase
			// in list that has same probability, TFxIDF value, and distance as
			// current phrase. We do this to catch all superphrases
			// that have same probability, TFxIDF value and distance as current
			// phrase.
			int startInd = i;
			while (startInd < vals.length) {
				Instance inst = (Instance) vector.elementAt(startInd);
				if ((inst.value(tfidfAttIndex) != currentInstance
						.value(tfidfAttIndex))
						|| (inst.value(probsAttIndex) != currentInstance
								.value(probsAttIndex))
						|| (inst.value(distAttIndex) != currentInstance
								.value(distAttIndex))) {
					break;
				}
				startInd++;
			}
			currentInstance.setValue(probsAttIndex + 1, rank++);

		}

		return vector;
	}

	/**
	 * Computes the feature values for a given phrase.
	 */
	private double[] computeFeatureValues(Candidate candidate,
			boolean training, HashMap<String, Integer> hashKeyphrases,
			HashMap<String, Candidate> candidates) {
		boolean useBasicFeatures = true;
		boolean useFrequencyFeatures = true;
		boolean useLengthFeature = true;
		
		
		// Compute feature values
		double[] newInst = new double[numFeatures + 1];

		String id = candidate.getName();
		String name = candidate.getName();
		String original = candidate.getBestFullForm();
		String title = candidate.getTitle();

		// Compute TFxIDF
		Counter counterGlobal = (Counter) globalDictionary.get(name); // XXX
		double globalVal = 0;
		if (counterGlobal != null) {
			globalVal = counterGlobal.value();
			if (training) {
				globalVal = globalVal - 1;
			}
		}
		double tf = candidate.getTermFrequency();
		double idf = -Math.log((globalVal + 1) / ((double) numDocs + 1));

		if (useBasicFeatures) {
			newInst[tfidfIndex] = tf * idf;
			newInst[firstOccurIndex] = candidate.getFirstOccurrence();
		}

		if (useFrequencyFeatures) {
			newInst[tfIndex] = tf;
			newInst[idfIndex] = idf;
		}

		if (useLengthFeature) {

			if (original == null) {
				logger.log(Level.WARNING, "Warning! Problem with candidate "
						+ name);
				newInst[lengthIndex] = 1.0;
			} else {
				// String[] words = candidate.getTitle().split(" ");
				String[] words = original.split(" ");
				newInst[lengthIndex] = (double) words.length;
			}
		}

		// Compute class value
		String checkManual = name;

		if (hashKeyphrases == null) { // No author-assigned keyphrases
			// newInst[numFeatures] = Instance.missingValue();
			newInst[numFeatures] = 0;
		} else if (!hashKeyphrases.containsKey(checkManual)) {
			newInst[numFeatures] = 0; // Not a keyphrase
		} else {
			if (nominalClassValue) {
				newInst[numFeatures] = 1; // Keyphrase
			} else {
				double c = (double) (hashKeyphrases.get(checkManual))
						/ numIndexers;
				newInst[numFeatures] = c; // Keyphrase
			}
		}
		logger.log(Level.INFO, candidate.toString() + "\tTFxIDF "
				+ newInst[tfidfIndex] + "\tfirstOccurIndex "
				+ newInst[firstOccurIndex]);

		return newInst;
	}
	
	
	//TODO batchFinished
	@Override
	public boolean batchFinished() throws Exception {
		if (getInputFormat() == null) {
			throw new Exception("No input instance format defined");
		}

		if (globalDictionary == null) {
			// DONE selectCandidates();
			// TODO buildGlobalDictionaries();
		// TODObuildClassifier();
			convertPendingInstances();
		}
		flushInput();
		m_NewBatch = true;
		return (numPendingOutput() != 0);
	}
	
	//// ADDITION
	public void addKeyphrases(Instance instance,
			HashMap<String, Integer> hashKeyphrases) {
		if (hashKeyphrases != null && !hashKeyphrases.isEmpty()) {
			hashKeyphrasesDict.put(instance, hashKeyphrases);
		}
		
	}

	public void addCandidates(Instance instance,
			HashMap<String, Candidate> candidates) {
		if (candidates != null) {
			allCandidates.put(instance, candidates);
		}
	}

	
	public void load(DataResource aData) throws ResourceInitializationException {
		// TODO Auto-generated method stub
		hashKeyphrasesDict = new HashMap<Instance, HashMap<String,Integer>>();
		allCandidates = new HashMap<Instance, HashMap<String,Candidate>>();
	}


	// ------------------------------------
	// TO REFACTOR
	
}
