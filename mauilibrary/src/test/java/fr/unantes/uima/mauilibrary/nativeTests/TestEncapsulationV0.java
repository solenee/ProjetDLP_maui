package fr.unantes.uima.mauilibrary.nativeTests;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.io.File;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ExternalResourceDescription;
import org.junit.Before;
import org.junit.Test;

import com.entopix.maui.filters.MauiFilter;
import com.entopix.maui.main.MauiModelBuilder;
import com.entopix.maui.main.MauiTopicExtractor;
import com.entopix.maui.stemmers.FrenchStemmer;
import com.entopix.maui.stemmers.PorterStemmer;
import com.entopix.maui.stemmers.Stemmer;
import com.entopix.maui.stopwords.Stopwords;
import com.entopix.maui.stopwords.StopwordsEnglish;
import com.entopix.maui.stopwords.StopwordsFrench;
import com.entopix.maui.util.DataLoader;

import fr.unantes.uima.mauilibrary.annotator.ModelBuilderV0;
import fr.unantes.uima.mauilibrary.annotator.TopicExtractorV0;
import fr.unantes.uima.mauilibrary.reader.DocumentsReader;
import fr.unantes.uima.mauilibrary.reader.ManualTopicsReaderV0;
import fr.unantes.uima.mauilibrary.refactoring.MauiFilterV0;
import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource_MauiImpl;
import fr.unantes.uima.mauilibrary.writer.TopicWriter;

/**
 * 
 * @author solenee
 *
 */
public class TestEncapsulationV0 {

//	static String language = "fr";
	static String language = "en";
	
//	static String modelName = "src/test/resources/data/models/maui_model";
	static String modelName = "src/test/resources/results/modelUIMA";
	
//	static String trainDir = "src/test/resources/data/term_assignment/train_fr";
	static String trainDir = "src/test/resources/data/automatic_tagging/train";
//	static String trainDir = "src/test/resources/data/term_assignment/train";
	
//	static String testDir = "src/test/resources/data/term_assignment/test_fr";
	static String testDir = "src/test/resources/data/automatic_tagging/test";
//	static String testDir = "src/test/resources/data/term_assignment/test"; //OutOfMemory : MAUI. OK : UIMA
	
	@Before
	public void testFrenchBuildModel() throws Exception {
		
		String modelNameUIMA = "src/test/resources/results/modelUIMA";
		// location of the data
		
		// UIMA :  Stopwords resource
		ExternalResourceDescription stopWordsResourceDesc =
				createExternalResourceDescription(StopWordsResource_MauiImpl.class, "xx");
		// UIMA :  Stemmer resource
		ExternalResourceDescription stemmerResourceDesc =
				createExternalResourceDescription(StemmerResource_MauiImpl.class,"xx");
		
		// UIMA :  MauiFilter resource
	    ExternalResourceDescription mauiFilterResourceDesc = 
	    		createExternalResourceDescription(MauiFilterV0.class, "xx");

		// UIMA : Run in UIMA pipeline
		CollectionReader reader = createReader(
	                DocumentsReader.class, 
	                DocumentsReader.PARAM_DIRECTORY_NAME, trainDir,
	                DocumentsReader.DOCUMENT_LANGUAGE, language
	        );
		AnalysisEngine manualTopicsReader = createEngine(ManualTopicsReaderV0.class);
		
		
		AnalysisEngine modelBuilder = createEngine(ModelBuilderV0.class,
				ModelBuilderV0.DOCUMENT_LANGUAGE, language,
				ModelBuilderV0.MODEL_NAME, modelNameUIMA,
				ModelBuilderV0.RES_STEMMER, stemmerResourceDesc,
				ModelBuilderV0.RES_STOPWORDS, stopWordsResourceDesc,
				ModelBuilderV0.RES_MODEL, mauiFilterResourceDesc); 
		
		
		// OK :D
		// Build model : training
		SimplePipeline.runPipeline(
        		reader,
        		manualTopicsReader,
        		modelBuilder
        		);
	}
	
	@Before
	public void testFrenchBuildModelMAUI() throws Exception {

		// name of the file for storing the model
		String modelNameMAUI = "src/test/resources/data/models/maui_model";
		
		// language specific settings
		Stemmer stemmer = null;
		Stopwords stopwords = null;
		if (language == "fr") {
			stemmer = new FrenchStemmer();
			stopwords = new StopwordsFrench();
		} else {
			stemmer = new PorterStemmer();
			stopwords = new StopwordsEnglish();			
		}
		String encoding = "UTF-8";
		
		// vocabulary to use for term assignment
		String vocabulary = "none";//"src/test/resources/data/vocabularies/agrovoc_fr.rdf.gz";
		String format = "skos";

		// maui objects
		MauiModelBuilder modelBuilder = new MauiModelBuilder();
		
		// Settings for the model builder
		modelBuilder.inputDirectoryName = trainDir;
		modelBuilder.modelName = modelNameMAUI;
		modelBuilder.vocabularyFormat = format;
		modelBuilder.vocabularyName = vocabulary;
		modelBuilder.stemmer = stemmer;
		modelBuilder.stopwords = stopwords;
		modelBuilder.documentLanguage = language;
		modelBuilder.documentEncoding = encoding;
		modelBuilder.serialize = true;
		
		// Which features to use?
		modelBuilder.setBasicFeatures(true);
		modelBuilder.setKeyphrasenessFeature(false);
		modelBuilder.setFrequencyFeatures(true);
		modelBuilder.setPositionsFeatures(false);
		modelBuilder.setLengthFeature(true);
		modelBuilder.setThesaurusFeatures(false);
		
		// Run model builder
		MauiFilter filter = modelBuilder.buildModel(DataLoader.loadTestDocuments(trainDir));
		modelBuilder.saveModel(filter);
		
	}
	
	@Test
	public void testFrenchExtractTopicsMAUI() throws Exception {
		
		// language specific settings
		Stemmer stemmer = null;
		Stopwords stopwords = null;
		if (language == "fr") {
			stemmer = new FrenchStemmer();
			stopwords = new StopwordsFrench();
		} else {
			stemmer = new PorterStemmer();
			stopwords = new StopwordsEnglish();			
		}
		String encoding = "UTF-8";
		
		// vocabulary to use for term assignment
		String vocabulary = "none";//"src/test/resources/data/vocabularies/agrovoc_fr.rdf.gz";
		String format = "skos";
		
		// maui objects
		MauiTopicExtractor topicExtractor = new MauiTopicExtractor();
		
		// Settings for the topic extractor
		topicExtractor.inputDirectoryName = testDir;
		topicExtractor.modelName = modelName;
		topicExtractor.vocabularyName = vocabulary;
		topicExtractor.vocabularyFormat = format;
		topicExtractor.stemmer = stemmer;
		topicExtractor.stopwords = stopwords;
		topicExtractor.documentLanguage = language;
		//topicExtractor.topicsPerDocument = numTopicsToExtract; 
		topicExtractor.cutOffTopicProbability = 0.0;
		topicExtractor.serialize = true;
		
		// Run topic extractor
		topicExtractor.loadModel();
		topicExtractor.extractTopics(DataLoader.loadTestDocuments(testDir));
	}
	
	@Test
	public void testFrenchExtractTopicsUIMA() throws Exception {

		// UIMA : Stopwords resource
		ExternalResourceDescription stopWordsResourceDesc = createExternalResourceDescription(
				StopWordsResource_MauiImpl.class, "xx");
		// UIMA : Stemmer resource
		ExternalResourceDescription stemmerResourceDesc = createExternalResourceDescription(
				StemmerResource_MauiImpl.class, "xx");

		//-------------------------------------------
		// UIMA : Run in UIMA pipeline
		CollectionReader reader = createReader(DocumentsReader.class,
				DocumentsReader.PARAM_DIRECTORY_NAME, testDir,
				DocumentsReader.DOCUMENT_LANGUAGE, language
				);
		AnalysisEngine manualTopicsReader = createEngine(ManualTopicsReaderV0.class);
		
		// UIMA :  MauiFilter resource
		ExternalResourceDescription mauiFilterResourceDesc =
				createExternalResourceDescription(MauiFilterV0.class, (new File(modelName)).toURI().toURL());
		
		AnalysisEngine topicExtractor =  createEngine(TopicExtractorV0.class,
				TopicExtractorV0.RES_STEMMER, stemmerResourceDesc,
				TopicExtractorV0.RES_STOPWORDS, stopWordsResourceDesc,
				TopicExtractorV0.RES_MODEL, mauiFilterResourceDesc);

		AnalysisEngine topicWriter = createEngine(TopicWriter.class,
				TopicWriter.PATH_FILE, "src/test/resources/results/test_v0.results"
				);
		
		// Extract topics : test
		SimplePipeline.runPipeline(
        		reader,
        		manualTopicsReader,
        		topicExtractor,
        		topicWriter
        		);
	}
}
