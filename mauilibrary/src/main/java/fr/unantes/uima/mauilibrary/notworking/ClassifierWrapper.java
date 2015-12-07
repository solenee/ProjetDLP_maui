package fr.unantes.uima.mauilibrary.notworking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

import com.entopix.maui.filters.MauiFilter;
import com.entopix.maui.util.Candidate;
import com.entopix.maui.util.Counter;
import com.entopix.maui.util.Topic;

import fr.unantes.uima.mauilibrary.annotator.Classifier_ImplBase;
import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource_MauiImpl;

public abstract class ClassifierWrapper extends Classifier_ImplBase {
	/*
	public static final String TOPICS_PER_DOCUMENT = "TOPICS_PER_DOCUMENT";
	@ConfigurationParameter(name = TOPICS_PER_DOCUMENT,
			mandatory = false, defaultValue = "8")
	private Integer topicsPerDocument;
	
	
	// Indices of attributes in classifierData
	// General features
	private int tfIndex = 0; // term frequency (freq feature)
	private int idfIndex = 1; // inverse document frequency (freq feature)
	private int tfidfIndex = 2; // TFxIDF  (basic)
	private int firstOccurIndex = 3; // position of the first occurrence (basic)
	private int lastOccurIndex = 4; // position of the last occurrence (position)
	private int spreadOccurIndex = 5; // spread of occurrences (position)
	private int domainKeyphIndex = 6; // domain keyphraseness
	private int lengthIndex = 7; // term length
	private int generalityIndex = 8; // generality
	// TODO
	private Boolean training = Boolean.TRUE;
	int fileNameAtt = 0 ; //TODO
	int keyphrasesAtt = 1; //TODO
	int documentAtt = 1 ; //TODO
	int minOccurFrequency = 0; // TODO
	int numFeatures = 1; // TODO
	boolean nominalClassValue = false; // TODO
	int numDocs = 1; //TODO 
	
	 //Number of human indexers (times a keyphrase appears in the keyphrase set)
	private int numIndexers = 1;
	//Template for the classifier data
	private Instances classifierData = null;
	//The actual classifier used to compute probabilities
	private Classifier classifier = null;
	
	
	//-------------------------------------------------
	 //Not used in this version
	private String vocabularyName = "none";
	
	public final static String RES_STOPWORDS = "stopwordsResource";
	@ExternalResource(key = RES_STOPWORDS)
	private StopWordsResource stopwords;
	
	
	public final static String RES_STEMMER = "stemmerResource";
	@ExternalResource(key = RES_STEMMER)
	private StemmerResource_MauiImpl stemmer;
	
	private MauiFilter mauiFilter;
	
	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		mauiFilter = new MauiFilter();
		mauiFilter.setVocabularyName(vocabularyName);
		stopwords = new StopWordsResource_MauiImpl();
		stemmer = new StemmerResource_MauiImpl();
	}
	
	private void update(String language) {
		System.out.println(stopwords);
		System.out.println(stemmer);
		stopwords.setLanguage(language);
		stemmer.setLanguage(language);
		mauiFilter.setStopwords(stopwords.getMauiStopwords());
		mauiFilter.setStemmer(stemmer.getMauiStemmer());
		
	}
	
	// Computes the feature values for a given phrase.
	private double[] computeFeatureValues(Candidate candidate,
			boolean training, HashMap<String, Counter> hashKeyphrases,
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
				Counter counterGlobal = (Counter) mauiFilter.globalDictionary.get(name); //TODO mauiFilter.globalDictionary 
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
						logger.log(Level.WARNING, "Warning! Problem with candidate " + name);
						newInst[lengthIndex] = 1.0;
					} else {
						// String[] words = candidate.getTitle().split(" ");
						String[] words = original.split(" ");
						newInst[lengthIndex] = (double) words.length;
					}
				}
				
				// Compute class value
				String checkManual = name;
				if (!vocabularyName.equals("none")) {
					checkManual = candidate.getTitle();
				}

				if (hashKeyphrases == null) { // No author-assigned keyphrases
					// newInst[numFeatures] = Instance.missingValue();
					newInst[numFeatures] = 0;
				} else if (!hashKeyphrases.containsKey(checkManual)) {
					newInst[numFeatures] = 0; // Not a keyphrase
				} else {
					if (nominalClassValue) {
						newInst[numFeatures] = 1; // Keyphrase
					} else {
						double c = (double) ((Counter) hashKeyphrases.get(checkManual))
								.value()
								/ numIndexers;
						newInst[numFeatures] = c; // Keyphrase
					}
				}
				  logger.log(Level.INFO, candidate.toString()+
						  "\tTFxIDF " + newInst[tfidfIndex] +
						  "\tfirstOccurIndex " + newInst[firstOccurIndex]
								  );
					
					return newInst;
	}
	
	public void selectTopics(String document, HashMap<String, Candidate> candidateList) {

		FastVector vector = new FastVector();

		Instance instance = null; // TODO Initialize Instance
		String fileName = instance.stringValue(fileNameAtt);

		logger.log(org.apache.uima.util.Level.INFO, "-- Converting instance for document " + fileName);

		// Get the key phrases for the document
		HashMap<String, Counter> hashKeyphrases = null;

		//TODO
//		if (!instance.isMissing(keyphrasesAtt)) {
//			String keyphrases = instance.stringValue(keyphrasesAtt);
//			hashKeyphrases = mauiFilter.getGivenKeyphrases(keyphrases);
//		}

		// Get the document text
		String documentText = instance.stringValue(documentAtt);

		// Compute the candidate topics : DONE
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
				logger.log(Level.SEVERE, "Exception while getting probability for candidate " + candidate.getName());
				continue;
			}

			double prob = probs[0];
			if (nominalClassValue) {
				prob = probs[1];
			}

			// Compute attribute values for final instance
			double[] newInst = new double[instance.numAttributes() + numFeatures + 2];

			int pos = 0;
			for (int i = 1; i < instance.numAttributes(); i++) {

				if (i == documentAtt) {

					// output of values for a given phrase:


					// 0 Add phrase
					int index = mauiFilter.outputFormatPeek().attribute(pos)
							.addStringValue(name);
					newInst[pos++] = index; 

					// 1 Add original version
					if (orig != null) {
						index = outputFormatPeek().attribute(pos).addStringValue(orig);
					} else {
						index = outputFormatPeek().attribute(pos).addStringValue(name);
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
					newInst[pos++] = inst.value(nodeDegreeIndex); // 12
					newInst[pos++] = inst.value(invWikipFreqIndex); // 13
					newInst[pos++] = inst.value(totalWikipKeyphrIndex); // 14
					newInst[pos++] = inst.value(wikipGeneralityIndex); // 15

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

		logger.log(org.apache.uima.util.Level.FINE, countPos + " positive; " + countNeg + " negative instances");

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
	}
	
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		System.out.println("Oh");
		update(jCas.getDocumentLanguage());
		selectTopics(jCas.getDocumentText(), null);
		super.process(jCas);
	}
*/
}
