package fr.unantes.uima.mauilibrary.notworking;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import com.entopix.maui.filters.MauiFilter;
import com.entopix.maui.filters.MauiFilter.MauiFilterException;
import com.entopix.maui.util.DataLoader;
import com.entopix.maui.util.Topic;

import fr.unantes.uima.mauilibrary.model.MauiFilterUIMA;
import fr.unantes.uima.mauilibrary.resource.DFResource;
import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource_MauiImpl;
import fr.unantes.uima.mauilibrary.types.TopicAnnotation;

public class Classifier_MauiFilter extends JCasAnnotator_ImplBase {

	Logger logger = UIMAFramework.getLogger(Classifier_MauiFilter.class);
	
	public static final String TOPIC_PER_DOCUMENT = "TOPIC_PER_DOCUMENT";
	@ConfigurationParameter(name = TOPIC_PER_DOCUMENT, description = "number of topics per documents", mandatory = false, defaultValue="8")
	private int topicsPerDocument;
	
	public static final String MODEL_NAME = "MODEL_NAME";
	@ConfigurationParameter(name = MODEL_NAME,
			description = "model name",
			mandatory = true)
	private String modelName;
	
	public final static String RES_MAIN_FILTER = "RES_MAIN_FILTER";
	@ExternalResource(key = RES_MAIN_FILTER)
	private MauiFilterV1 extractionModel;
	/*
	public final static String RES_FILTER = "filterResource";
	@ExternalResource(key = RES_FILTER, mandatory=true)
	private MauiFilterV1 extractionModel;
	
	public final static String RES_DF = "documentFrequencyResource";
	@ExternalResource(key = RES_DF, mandatory=false)
	private DFResource globalDictionary;
	*/
	
	
	/**
	 * Copied from MauiWrapper
	 * @param text
	 * @param topicsPerDocument
	 * @return
	 * @throws Exception
	 */
   public List<Topic> extractTopicsFromTextAsResults(String text, int topicsPerDocument) throws Exception {

		FastVector atts = new FastVector(3);
		atts.addElement(new Attribute("filename", (FastVector) null));
		atts.addElement(new Attribute("document", (FastVector) null));
		atts.addElement(new Attribute("keyphrases", (FastVector) null));
		Instances data = new Instances("keyphrase_training_data", atts, 0);

		extractionModel.setInputFormat(data);

		/*// set features configurations
		extractionModel.setBasicFeatures(true);
		extractionModel.setKeyphrasenessFeature(false);
		extractionModel.setFrequencyFeatures(true);
		extractionModel.setPositionsFeatures(false);
		extractionModel.setLengthFeature(true);
		extractionModel.setThesaurusFeatures(false);
		extractionModel.setWikipediaFeatures(false, null);
		*/
	    ////////////:
	    /*FastVector atts = new FastVector(3);
        atts.addElement(new Attribute("filename", (FastVector) null));
        atts.addElement(new Attribute("doc", (FastVector) null));
        atts.addElement(new Attribute("keyphrases", (FastVector) null));
        Instances data = new Instances("keyphrase_training_data", atts, 0);
        */

        double[] newInst = new double[3];

        newInst[0] = data.attribute(0).addStringValue("inputFile");
        newInst[1] = data.attribute(1).addStringValue(text);
        newInst[2] = Instance.missingValue();
        data.add(new Instance(1.0, newInst));

        extractionModel.input(data.instance(0));
        
        data = data.stringFreeStructure();
        Instance[] topRankedInstances = new Instance[topicsPerDocument];
        Instance inst;

        // Iterating over all extracted keyphrases (inst)
        while ((inst = extractionModel.output()) != null) {

            int index = (int) inst.value(extractionModel.getRankIndex()) - 1;

            if (index < topicsPerDocument) {
                topRankedInstances[index] = inst;
            }
        }

        List<Topic> topics = new ArrayList<Topic>();
        
        for (int i = 0; i < topicsPerDocument; i++) {
            if (topRankedInstances[i] != null) {
                String id = topRankedInstances[i].stringValue(0);
                String title = topRankedInstances[i].stringValue(extractionModel.getOutputFormIndex());
                double probability = topRankedInstances[i].value(extractionModel.getProbabilityIndex());
                topics.add(new Topic(title, id, probability));
            }
        }
        extractionModel.batchFinished();
        return topics;
   }
   
	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		BufferedInputStream inStream = null;
		MauiFilterV1 model = null;
		try {
			inStream = new BufferedInputStream(
					new FileInputStream(modelName));
			ObjectInputStream in = new ObjectInputStream(inStream);
			model = (MauiFilterV1) in.readObject();
			in.close();
			inStream.close();
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error while loading extraction model from " + modelName + "!\n", e);
			throw new RuntimeException();
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Mismatch of the class in " + modelName + "!\n", e);
			throw new RuntimeException();
		} finally {
			try {
				inStream.close();
			} catch (IOException e1) {
				logger.log(Level.SEVERE, "Error while loading extraction model from " + modelName + "!\n", e1);
				throw new RuntimeException();
			}
		}
	}

	
	/**
	 * Needs : keyphrases, candidates list (tf, idf, bestFullForm) 
	 * Algorithm :
	 * Get all candidates
	 * Create Weka instances using tf, idf, firstOccurrence and bestFullForm as feature values
	 * Train
	 * Select the 'topicsPerDocument' candidates having the best score 
	 */
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
			try {
				List<Topic> topics = extractTopicsFromTextAsResults(jCas.getDocumentText(), topicsPerDocument);
				if (topics== null || topics.isEmpty()) {
					logger.log(Level.INFO, "No topic found");
				}
				for(Topic elected : topics ) {
					// Annotate topics
					TopicAnnotation tAnno = new TopicAnnotation(jCas);
					tAnno.setText(elected.getId());
					tAnno.setScore(elected.getProbability());
					tAnno.addToIndexes();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.SEVERE, "An error occured. Unable to extract topics");
			}
	}
}
