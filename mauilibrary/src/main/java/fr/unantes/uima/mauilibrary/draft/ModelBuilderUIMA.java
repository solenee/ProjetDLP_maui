package fr.unantes.uima.mauilibrary.draft;

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
	
	//@ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
	private String inputDirectoryName;
	private String modelName;
	private String vocabularyFormat;
	private String vocabularyName;
	private String documentLanguage;
	private String documentEncoding;
	private Boolean serialize = true;
	//@ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
	private Boolean basicFeatures; //(true);
	private Boolean keyphrasenessFeature; //(true);
	private Boolean frequencyFeatures; //(false);
	private Boolean positionsFeatures; //(true);
	private Boolean lengthFeature; //(true);
	private Boolean thesaurusFeatures; //(true);
	
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
	
	private MauiModelBuilder mauiModelBuilder;
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
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
		
		mauiModelBuilder.stemmer = stemmer.getMauiStemmer(); 
		mauiModelBuilder.stopwords = stopwords.getMauiStopwords(); 
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// TODO
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
