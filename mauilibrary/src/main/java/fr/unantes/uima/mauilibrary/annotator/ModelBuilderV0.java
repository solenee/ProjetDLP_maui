package fr.unantes.uima.mauilibrary.annotator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import com.entopix.maui.stemmers.Stemmer;
import com.entopix.maui.stopwords.Stopwords;

import fr.unantes.uima.mauilibrary.refactoring.MauiFilter_UIMA;
import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource;
import fr.unantes.uima.mauilibrary.types.FileDescription;

public class ModelBuilderV0 extends JCasAnnotator_ImplBase {

	public static final String DOCUMENT_LANGUAGE = "DOCUMENT_LANGUAGE";
	@ConfigurationParameter(name = DOCUMENT_LANGUAGE,
			mandatory = false, defaultValue = "en")
	private String documentLanguage;
	
	public final static String RES_STOPWORDS = "stopwordsResource";
	@ExternalResource(key = RES_STOPWORDS)
	private StopWordsResource stopwords;
	
	public final static String RES_STEMMER = "stemmerResource";
	@ExternalResource(key = RES_STEMMER)
	private StemmerResource_MauiImpl stemmer;
	
	public final static String RES_MODEL = "RES_MODEL";
	@ExternalResource(key = RES_MODEL)
	private MauiFilter_UIMA extractionModel;
	
	public static final String MODEL_NAME = "MODEL_NAME";
	@ConfigurationParameter(name = MODEL_NAME,
			description = "model name",
			mandatory = true)
	private String modelName;
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		stemmer.setLanguage(documentLanguage); // don't forget!!
		stopwords.setLanguage(documentLanguage);// don't forget!!
		extractionModel.initializeModelForTraining(documentLanguage, stemmer.getMauiStemmer(), stopwords.getMauiStopwords());
	}
	
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		String filename = null;
		String manualTopicsText = null;
		for (FileDescription fDesc : JCasUtil.select(jCas, FileDescription.class)) {
			// should have 1 element
			filename = fDesc.getFileName();
			manualTopicsText = fDesc.getAbsolutePath(); //getManualTopics(); // XXX
		}
		extractionModel.addDocumentToModel(filename, jCas.getDocumentText(), manualTopicsText);
	}

	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		super.collectionProcessComplete();
		extractionModel.finalizeModelBuilding();
		extractionModel.save(modelName);
	}
}

