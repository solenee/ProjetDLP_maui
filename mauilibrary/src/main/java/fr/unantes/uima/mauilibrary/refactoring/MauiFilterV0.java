package fr.unantes.uima.mauilibrary.refactoring;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAFramework;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import com.entopix.maui.filters.MauiFilter;
import com.entopix.maui.stemmers.Stemmer;
import com.entopix.maui.stopwords.Stopwords;
import com.entopix.maui.util.Topic;
import com.entopix.maui.vocab.Vocabulary;

public class MauiFilterV0 implements MauiFilter_UIMA {

	Logger logger = UIMAFramework.getLogger(MauiFilter.class);

	MauiFilter model = new MauiFilter();

	// Default parameters : V0
	static String vocabularyName = "none";
	static int maxPhraseLength = 5;
	static int minPhraseLength = 1;
	static int minNumOccur = 1;
	/**
	 * Cut off threshold for the topic probability. Minimum probability of a
	 * topic as returned by the classifier
	 * 
	 */
	public double cutOffTopicProbability = 0.0;

	// Attributes
	public Instances data = null;

	private void initializeData() throws Exception {
		FastVector atts = new FastVector(3);
		atts.addElement(new Attribute("filename", (FastVector) null));
		atts.addElement(new Attribute("document", (FastVector) null));
		atts.addElement(new Attribute("keyphrases", (FastVector) null));
		data = new Instances("keyphrase_training_data", atts, 0);
		model.setInputFormat(data);
	}

	// -----------------------------------------
	// Step 1 : Build model
	public boolean initializeModel(String documentLanguage, Stemmer stemmer,
			Stopwords stopwords) {
		boolean done = false;
		logger.log(Level.INFO, "-- Building the model... ");

		try {
			initializeData();

			model.setMaxPhraseLength(maxPhraseLength);
			model.setMinPhraseLength(minPhraseLength);
			model.setMinNumOccur(minNumOccur);
			model.setStemmer(stemmer);
			model.setStopwords(stopwords);
			model.setDocumentLanguage(documentLanguage);
			initializeVocabulary(vocabularyName, "", null);

			// set features configurations
			model.setBasicFeatures(true);
			model.setKeyphrasenessFeature(false);
			model.setFrequencyFeatures(true);
			model.setPositionsFeatures(false);
			model.setLengthFeature(true);
			model.setThesaurusFeatures(false);
			model.setWikipediaFeatures(false, null);

			done = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return done;
	}

	// OK
	public void initializeVocabulary(String vocabularyName,
			String vocabularyFormat, Vocabulary vocabulary) {
		model.setVocabularyName(vocabularyName);
		model.setVocabularyFormat(vocabularyFormat);
		model.setVocabulary(vocabulary);
	}

	// OK
	public boolean addDocumentToModel(String filename, String documentText,
			String manualTopicsText) {
		boolean done = false;
		logger.log(Level.INFO, "-- Adding documents as instances... ");
		try {
			double[] newInst = new double[3];
			newInst[0] = data.attribute(0).addStringValue(filename);

			// Adding the text and the topics for the document to the instance
			if (documentText.length() > 0) {
				newInst[1] = data.attribute(1).addStringValue(documentText);
			} else {
				newInst[1] = Instance.missingValue();
			}

			if (manualTopicsText.length() > 0) {
				newInst[2] = data.attribute(2).addStringValue(manualTopicsText);
			} else {
				newInst[2] = Instance.missingValue();
			}

			data.add(new Instance(1.0, newInst));

			model.input(data.instance(0));
			data = data.stringFreeStructure();

			done = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return done;
	}

	// OK
	public boolean finalizeModelBuilding() {
		boolean done = false;
		logger.log(Level.INFO, "-- Building the model... ");
		try {
			model.batchFinished();

			while ((model.output()) != null) {
			}
			done = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return done;
	}

	// OK
	public boolean save(String modelName) {
		boolean done = false;
		try {
			BufferedOutputStream bufferedOut = new BufferedOutputStream(
					new FileOutputStream(modelName));
			ObjectOutputStream out = new ObjectOutputStream(bufferedOut);
			out.writeObject(model);
			out.flush();
			out.close();
			done = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return done;
	}

	// -----------------------------------------
	// Step 2 : Extract topics
	public boolean loadModel(String modelName) {
		boolean done = false;
		try {
			initializeData();
			// TODO
			done = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return done;
	}

	public List<Topic> extractTopics(String filename, String documentText,
			String manualTopicsText, int topicsPerDocument) {
		List<Topic> topics = new ArrayList<Topic>();
		logger.log(Level.INFO, "-- Extracting keyphrases... ");
		try {
			double[] newInst = new double[3];

			newInst[0] = data.attribute(0).addStringValue(filename);

			// Adding the text of the document to the instance
			if (documentText.length() > 0) {
				newInst[1] = data.attribute(1).addStringValue(documentText);
			} else {
				newInst[1] = Instance.missingValue();
			}

			if (manualTopicsText.length() > 0) {
				newInst[2] = data.attribute(2).addStringValue(manualTopicsText);
			} else {
				newInst[2] = Instance.missingValue();
			}

			data.add(new Instance(1.0, newInst));

			model.input(data.instance(0));

			data = data.stringFreeStructure();
			logger.log(Level.INFO, "-- Processing document: " + filename);

			Instance[] topRankedInstances = new Instance[topicsPerDocument];

			// MauiTopics documentTopics = new
			// MauiTopics(document.getFilePath());
			// documentTopics.setPossibleCorrect(document.getTopicsString().split("\n").length);

			Instance inst;
			int index = 0;
			double probability;
			Topic topic;
			String title, id;

			logger.log(Level.INFO, "-- Keyphrases and feature values:");

			// Iterating over all extracted topic instances
			while ((inst = model.output()) != null) {
				probability = inst.value(model.getProbabilityIndex());
				if (index < topicsPerDocument) {
					if (probability > cutOffTopicProbability) {
						topRankedInstances[index] = inst;
						title = topRankedInstances[index].stringValue(model
								.getOutputFormIndex());
						id = "1"; // topRankedInstances[index].
						// stringValue(mauiFilter.getOutputFormIndex() + 1); //
						// TODO: Check
						topic = new Topic(title, id, probability);

						if ((int) topRankedInstances[index]
								.value(topRankedInstances[index]
										.numAttributes() - 1) == 1) {
							topic.setCorrectness(true);
						} else {
							topic.setCorrectness(false);
						}

						topics.add(topic);
						logger.log(Level.INFO, "Topic " + title + " " + id
								+ " " + probability + " > " + topic.isCorrect());

						index++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return topics;
	}

	public boolean close() {
		boolean done = false;
		try {
			model.batchFinished();
			done = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return done;
	}

	public void load(DataResource aData) throws ResourceInitializationException {
		// TODO Load from file : loadModel(modelName)
		/*InputStream inStream = null;
		if (aData == null) {
			return;
		}
		try {
			inStream = aData.getInputStream(); // new BufferedInputStream(new
												// FileInputStream(modelPath));
			ObjectInputStream in = new ObjectInputStream(inStream);
			model = (MauiFilter) in.readObject();
			in.close();
			inStream.close();

		} catch (IOException e) {
			logger.log(Level.WARNING,
					"Error while loading extraction model. Make sure you are at trainig phase");
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Mismatch of the class !\n", e);
			throw new ResourceInitializationException();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (Exception e1) {
				logger.log(Level.SEVERE,
						"Error while loading extraction model !\n", e1);
				// throw new RuntimeException();
			}
		}
		*/
	}

}
