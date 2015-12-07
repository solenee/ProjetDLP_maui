package fr.unantes.uima.mauilibrary.notworking;

import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import weka.core.Instance;

import com.entopix.maui.filters.MauiFilter;
import com.entopix.maui.util.Topic;

import fr.unantes.uima.mauilibrary.resource.FilterResource;
import fr.unantes.uima.mauilibrary.resource.TopicsBag;
import fr.unantes.uima.mauilibrary.types.FileDescription;
import fr.unantes.uima.mauilibrary.types.ManualTopic;
import fr.unantes.uima.mauilibrary.types.TopicAnnotation;

/**
 * For each document, give the corresponding weka Instance as input to the weka and get the output
 * 
 * @author solenee
 */
public class KeyphraseExtractor extends JCasAnnotator_ImplBase {

	Logger logger = UIMAFramework.getLogger(KeyphraseExtractor.class);

	public static final String TOPIC_PER_DOCUMENT = "TOPIC_PER_DOCUMENT";
	@ConfigurationParameter(name = TOPIC_PER_DOCUMENT, description = "number of topics per documents", mandatory = true)
	private int topicsPerDocument;

	public final static String RES_FILTER = "mainFilterResource";
	@ExternalResource(key = RES_FILTER)
	private FilterResource filter;

	public final static String RES_ALL_TOPICS = "all_topics";
	@ExternalResource(key = RES_ALL_TOPICS)
	private TopicsBag topicsBag;

	/**
	 * Cut off threshold for the topic probability. Minimum probability of a
	 * topic as returned by the classifier
	 * 
	 */
	public static final String CUT_OFF_TOPIC_PROBABILITY = "CUT_OFF_TOPIC_PROBABILITY";
	@ConfigurationParameter(name = CUT_OFF_TOPIC_PROBABILITY, description = "Minimum probability of a topic as returned by the classifier", mandatory = true, defaultValue = "0.0")
	public String probability;
	private double cutOffTopicProbability = 0.0; 
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		cutOffTopicProbability = Double.parseDouble(probability);
		// TODO Load model
		
	}
	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		String filename = null;
		String documentText = jcas.getDocumentText();
		String manualTopicsString = "";
		for (FileDescription fDesc : JCasUtil.select(jcas,
				FileDescription.class)) {
			// should have 0 or 1 element
			filename = fDesc.getFileName();
		}
		for (ManualTopic mTopic : JCasUtil.select(jcas, ManualTopic.class)) {
			manualTopicsString += mTopic.getTopic() + "\n";
		}
		try {
			logger.log(Level.INFO, "Extracting keyphrases from " + filename);
			filter.input(filename, documentText, manualTopicsString);

			// --------------------------------
			logger.log(Level.INFO, "-- Processing document: " + filename);

			Instance[] topRankedInstances = new Instance[topicsPerDocument];

			Instance inst;
			int index = 0;
			double probability;
			Topic topic;
			String title, id;

			// --------------------------------
			logger.log(Level.INFO, "-- Keyphrases and feature values:");

			// Iterating over all extracted topic instances
			while ((inst = filter.getMainFilter().output()) != null) {
				probability = inst.value(((MauiFilter) filter.getMainFilter())
						.getProbabilityIndex()); // TODO Generalize
				if (index < topicsPerDocument) {
					if (probability > cutOffTopicProbability) {
						topRankedInstances[index] = inst;
						title = topRankedInstances[index]
								.stringValue(((MauiFilter) filter
										.getMainFilter()).getOutputFormIndex()); // TODO
																					// Generalize
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
						TopicAnnotation tAnno = new TopicAnnotation(jcas);
						tAnno.setText(title);
						tAnno.addToIndexes();
						logger.log(Level.INFO, "Topic " + title + " " + id
								+ " " + probability + " > " + topic.isCorrect());
						topicsBag.addTopic(new Topic(title, id, probability));
						index++;
					}
				}
			}
			// Maui : allDocumentTopics.add(documentTopics);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		super.collectionProcessComplete();
		try {
			filter.getMainFilter().batchFinished();
		} catch (Exception e) {
			logger.log(Level.INFO, "An error occured during filter.batchFinished() : "+e.getMessage());
			e.printStackTrace();
		}
	}
}
