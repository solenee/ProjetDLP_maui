package fr.unantes.uima.mauilibrary.draft;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
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

import com.entopix.maui.filters.MauiFilter;
import com.entopix.maui.filters.MauiFilter.MauiFilterException;
import com.entopix.maui.main.MauiModelBuilder;
import com.entopix.maui.util.DataLoader;
import com.entopix.maui.util.MauiDocument;

import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource;


/**
 * @author seholie
 *
 */
public class ModelBuilderUIMA extends JCasAnnotator_ImplBase {

	Logger logger = UIMAFramework.getLogger(ModelBuilderUIMA.class);
	
	// -------------------------------
	// PARAMETERS
	// -------------------------------
	
	public static final String INPUT_DIRECTORY_NAME = "INPUT_DIRECTORY_NAME";
	@ConfigurationParameter(name = INPUT_DIRECTORY_NAME,
			description = "directory of train documents",
			mandatory = true)
	private String inputDirectoryName;
	
	public static final String MODEL_NAME = "MODEL_NAME";
	@ConfigurationParameter(name = MODEL_NAME,
			description = "model name",
			mandatory = true)
	private String modelName;
	
	public static final String VOCABULARY_FORMAT = "VOCABULARY_FORMAT";
	@ConfigurationParameter(name = VOCABULARY_FORMAT,
			mandatory = true)
	private String vocabularyFormat;
	
	public static final String VOCABULARY_NAME = "VOCABULARY_NAME";
	@ConfigurationParameter(name = VOCABULARY_NAME,
			mandatory = true)
	private String vocabularyName;
	
	public static final String DOCUMENT_LANGUAGE = "DOCUMENT_LANGUAGE";
	@ConfigurationParameter(name = DOCUMENT_LANGUAGE,
			mandatory = false, defaultValue = "en")
	private String documentLanguage;
	
	public static final String DOCUMENT_ENCODING = "DOCUMENT_ENCODING";
	@ConfigurationParameter(name = DOCUMENT_ENCODING,
			mandatory = false, defaultValue = "UTF-8")
	private String documentEncoding;
	
	public static final String SERIALIZE = "SERIALIZE";
	@ConfigurationParameter(name = SERIALIZE,
			mandatory = false, defaultValue = "true")
	private Boolean serialize = true;
	
	public static final String BASIC_FEATURES = "BASIC_FEATURES";
	@ConfigurationParameter(name = BASIC_FEATURES,
			mandatory = true)
	private Boolean basicFeatures; //(true);
	
	public static final String KEYPHRASENESS_FEATURE = "KEYPHRASENESS_FEATURE";
	@ConfigurationParameter(name = KEYPHRASENESS_FEATURE,
			mandatory = true)
	private Boolean keyphrasenessFeature; //(true);
	
	public static final String FREQUENCY_FEATURES = "FREQUENCY_FEATURES";
	@ConfigurationParameter(name = FREQUENCY_FEATURES,
			mandatory = false, defaultValue = "en")
	private Boolean frequencyFeatures; //(false);
	
	public static final String POSITIONS_FEATURES = "POSITIONS_FEATURES";
	@ConfigurationParameter(name = POSITIONS_FEATURES,
			mandatory = true)
	private Boolean positionsFeatures; //(true);
	
	public static final String LENGTH_FEATURE = "LENGTH_FEATURE";
	@ConfigurationParameter(name = LENGTH_FEATURE,
			mandatory = true)
	private Boolean lengthFeature; //(true);
	
	public static final String THESAURUS_FEATURES = "THESAURUS_FEATURES";
	@ConfigurationParameter(name = THESAURUS_FEATURES,
			mandatory = true)
	private Boolean thesaurusFeatures; //(true);
	
	public static final String SAVE_MODEL = "SAVE_MODEL";
	@ConfigurationParameter(name = SAVE_MODEL,
			mandatory = true)
	private Boolean saveModel; //(true); Ajout      
	
	// -------------------------------
	// RESOURCES
	// -------------------------------
	//@ExternalResource(key = RES_KEY)
	//TODO private Stemmer stemmer = stemmer;
	
	public final static String RES_STOPWORDS = "stopwordsResource";
	@ExternalResource(key = RES_STOPWORDS)
	private StopWordsResource stopwords;
	
	public final static String RES_STEMMER = "stemmerResource";
	@ExternalResource(key = RES_STEMMER)
	private StemmerResource_MauiImpl stemmer;
	
	// -------------------------------
	// OTHER ATTRIBUTES
	// -------------------------------
	private MauiModelBuilder mauiModelBuilder;
	
	private List<MauiDocument> documents;
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		
		documents = new ArrayList<MauiDocument>();
		
		mauiModelBuilder = new MauiModelBuilder();
		mauiModelBuilder.inputDirectoryName = inputDirectoryName;
		mauiModelBuilder.modelName = modelName;
		mauiModelBuilder.vocabularyFormat = vocabularyFormat;
		mauiModelBuilder.vocabularyName = vocabularyName;
		
		mauiModelBuilder.setBasicFeatures(basicFeatures);
		mauiModelBuilder.setKeyphrasenessFeature(keyphrasenessFeature);
		mauiModelBuilder.setFrequencyFeatures(frequencyFeatures);
		mauiModelBuilder.setPositionsFeatures(positionsFeatures);
		mauiModelBuilder.setLengthFeature(lengthFeature);
		mauiModelBuilder.setThesaurusFeatures(thesaurusFeatures);
		
		stemmer.setLanguage(documentLanguage);
		mauiModelBuilder.stemmer = stemmer.getMauiStemmer(); 
		
		stopwords.setLanguage(documentLanguage);
		mauiModelBuilder.stopwords = stopwords.getMauiStopwords(); 
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		//TODO
		//File keyFile = new File(file.getAbsoluteFile().toString().replace(".txt", ".key"));
		//String manualTopics = "";
		//if (keyFile.exists()) {
		//	manualTopics = FileUtils.readFileToString(keyFile);
		//}
		// new MauiDocument(file.getName(), file.getAbsolutePath(), textContent, manualTopics);
		
		logger.log(Level.WARNING, "Sorry! Not yet implemented");
	}
	
	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		super.collectionProcessComplete();
		//TODO Raffine !
		MauiFilter filter;
		try {
			// TODO Create 1 MauiDocument for each CAS and give the list to buildModel  
			filter = mauiModelBuilder.buildModel(DataLoader.loadTestDocuments(inputDirectoryName));
			if (saveModel) {
				mauiModelBuilder.saveModel(filter);
			}
		} catch (MauiFilterException e) {
			logger.log(Level.SEVERE, "A MauiFilterException error occured during model building");
			e.printStackTrace();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "An Exception occured during model saving");
			e.printStackTrace();
		}
	}

}
