package fr.unantes.uima.mauilibrary.draft;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

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

import com.entopix.maui.util.Candidate;
import com.entopix.maui.util.Counter;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import fr.unantes.uima.mauilibrary.notworking.MauiFilterV1;
import fr.unantes.uima.mauilibrary.resource.TopicsBag;
import fr.unantes.uima.mauilibrary.types.FileDescription;
import fr.unantes.uima.mauilibrary.types.ManualTopic;

public class ModelBuilderV2 extends JCasAnnotator_ImplBase {

	public static final String MODEL_NAME = "MODEL_NAME";
	@ConfigurationParameter(name = MODEL_NAME,
			description = "model name",
			mandatory = true)
	private String modelName;
	
	public final static String RES_MAIN_FILTER = "RES_MAIN_FILTER";
	@ExternalResource(key = RES_MAIN_FILTER)
	private MauiFilterV1 mauiFilter;
	
	private Instances data;
	Logger logger = UIMAFramework.getLogger(ModelBuilderV2.class);
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		FastVector atts = new FastVector(3);
		atts.addElement(new Attribute("filename", (FastVector) null));
		atts.addElement(new Attribute("document", (FastVector) null));
		atts.addElement(new Attribute("keyphrases", (FastVector) null));
		data = new Instances("keyphrase_training_data", atts, 0);
		
	}
	public void addToModel(FileDescription fDesc, String document, HashMap<String, Integer> hashKeyphrases, HashMap<String, Candidate> candidates) throws Exception {
		logger.log(Level.INFO, "-- Building the model... ");
		// TODO
		double[] newInst = new double[3];
		newInst[0] = data.attribute(0).addStringValue(fDesc.getFileName());

		// Adding the text and the topics for the document to the instance
		if (document.length() > 0) {
			newInst[1] = data.attribute(1).addStringValue(document);
		} else {
			newInst[1] = Instance.missingValue();
		}

		if (fDesc.getManualTopics() != null && fDesc.getManualTopics().length() > 0) {
			newInst[2] = data.attribute(2).addStringValue(fDesc.getManualTopics());
		} else {
			newInst[2] = Instance.missingValue();
		}

		data.add(new Instance(1.0, newInst));

		Instance instance = data.instance(0);
		mauiFilter.addKeyphrases(instance, hashKeyphrases);
		mauiFilter.addCandidates(instance, candidates);
		mauiFilter.input(instance);
		data = data.stringFreeStructure();
	}
	
	public void buildModel() throws Exception {
		logger.log(Level.INFO,"-- Building the model... ");
		mauiFilter.batchFinished();

		while ((mauiFilter.output()) != null) {
		}
	}
	
	public boolean saveModel(String outputFile) {
		boolean saved = false;
		try {
			BufferedOutputStream bufferedOut = new BufferedOutputStream(
					new FileOutputStream(outputFile));
			ObjectOutputStream out = new ObjectOutputStream(bufferedOut);
			out.writeObject(mauiFilter);
			out.flush();
			out.close();
			saved = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saved;
	}
	
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		String filename = null;
		
		String manualTopicsString = "";
		//TODO
		HashMap<String,Integer> keyprasesFreq = new HashMap<String, Integer>();
		
		FileDescription fDesc = JCasUtil.select(jCas,
				FileDescription.class).iterator().next();	// should have 1 element
		
		
		// TODO
		HashMap<String,Candidate> candidateFreq = new HashMap<String, Candidate>();
		
		
		try {
			addToModel(fDesc, jCas.getDocumentText(), null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		super.collectionProcessComplete();
		try {
		buildModel();
		saveModel(modelName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
