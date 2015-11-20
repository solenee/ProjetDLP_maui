package fr.unantes.uima.mauilibrary.nativeTests;

import static org.apache.uima.fit.factory.ExternalResourceFactory.createExternalResourceDescription;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ExternalResourceDescription;
import org.junit.Test;

import com.entopix.maui.filters.MauiFilter;
import com.entopix.maui.main.MauiModelBuilder;
import com.entopix.maui.main.MauiTopicExtractor;
import com.entopix.maui.stemmers.FrenchStemmer;
import com.entopix.maui.stemmers.Stemmer;
import com.entopix.maui.stopwords.Stopwords;
import com.entopix.maui.stopwords.StopwordsFrench;
import com.entopix.maui.util.DataLoader;

import fr.unantes.uima.mauilibrary.draft.ModelBuilderUIMA;
import fr.unantes.uima.mauilibrary.reader.DocumentsReader;
import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource_MauiImpl;

/**
 * 
 * @author solenee
 *
 */
public class FrenchExampleTest {

	//@Test
	public void testFrench() throws Exception {
		
		// location of the data
		String trainDir = "src/test/resources/data/term_assignment/train_fr";
		String testDir = "src/test/resources/data/term_assignment/test_fr";
		
		// name of the file for storing the model
		String modelName = "src/test/resources/data/models/french_model";
		
		// language specific settings
		String language = "fr";
		
		// UIMA :  Stopwords resource
		ExternalResourceDescription stopWordsResourceDesc =
				createExternalResourceDescription(StopWordsResource_MauiImpl.class,
						StopWordsResource_MauiImpl.PARAM_LANGUAGE, language);
		Stopwords stopwords = new StopwordsFrench(); //->
		
		// UIMA : Stemmer resource
		ExternalResourceDescription stemmerResourceDesc =
				createExternalResourceDescription(StemmerResource_MauiImpl.class,
						StemmerResource_MauiImpl.PARAM_LANGUAGE, language);
		Stemmer stemmer = new FrenchStemmer(); //->
		
		// document encoding
		String encoding = "UTF-8";
		
		// vocabulary to use for term assignment
		String vocabulary = "src/test/resources/data/vocabularies/agrovoc_fr.rdf.gz";
		String format = "skos";
		
		// how many topics per document to extract
		int numTopicsToExtract = 8;
		
		// maui objects
		MauiModelBuilder modelBuilder = new MauiModelBuilder();
		MauiTopicExtractor topicExtractor = new MauiTopicExtractor();
		
		// Settings for the model builder
//		modelBuilder.inputDirectoryName = trainDir;
//		modelBuilder.modelName = modelName;
//		modelBuilder.vocabularyFormat = format;
//		modelBuilder.vocabularyName = vocabulary;
//		modelBuilder.stemmer = stemmer;
//		modelBuilder.stopwords = stopwords;
//		modelBuilder.documentLanguage = language;
//		modelBuilder.documentEncoding = encoding;
//		modelBuilder.serialize = true;
		
		// Which features to use?
		Boolean keyphrasenessFeature = true;
		Boolean frequencyFeatures = false;  
		Boolean positionsFeatures = true;   
		Boolean lengthFeature = true;       
		Boolean thesaurusFeatures = true;   
//		modelBuilder.setBasicFeatures(true);
//		modelBuilder.setKeyphrasenessFeature(true);
//		modelBuilder.setFrequencyFeatures(false);
//		modelBuilder.setPositionsFeatures(true);
//		modelBuilder.setLengthFeature(true);
//		modelBuilder.setThesaurusFeatures(true);
		
		// Run model builder
//		MauiFilter filter = modelBuilder.buildModel(DataLoader.loadTestDocuments(trainDir));
//		modelBuilder.saveModel(filter);
		// UIMA : Run in UIMA pipeline
		CollectionReader reader = createReader(
	                DocumentsReader.class,  DocumentsReader.PARAM_DIRECTORY_NAME, trainDir 
	        );
		AnalysisEngine uimaModelBuilder = createEngine(ModelBuilderUIMA.class);
		
		SimplePipeline.runPipeline(
        		reader,
        		uimaModelBuilder
        		);
		
		/* TODO 
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
		
		// TODO Refactor TopicExtractor
		// Run topic extractor
		topicExtractor.loadModel();
		topicExtractor.extractTopics(DataLoader.loadTestDocuments(testDir));*/
	}
}
