package fr.unantes.uima.mauilibrary.nativeTests;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createExternalResourceDescription;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ExternalResourceDescription;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import fr.unantes.uima.mauilibrary.model.ModelBuilderUIMA;
import fr.unantes.uima.mauilibrary.notworking.KeyphraseExtractor;
import fr.unantes.uima.mauilibrary.reader.DocumentsReader;
import fr.unantes.uima.mauilibrary.reader.ManualTopicsReader;
import fr.unantes.uima.mauilibrary.resource.MauiFilterResource;
import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.TopicBag_Impl;

/**
 * Based on Maui FrenchExampleTest
 * @author solenee
 *
 */
public class RawEncapsulationTest {

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
				createExternalResourceDescription(StopWordsResource_MauiImpl.class, "xx");//,					StopWordsResource_MauiImpl.PARAM_LANGUAGE, language);
//		Stopwords stopwords = new StopwordsFrench(); //->
		
		// UIMA : Stemmer resource
		ExternalResourceDescription stemmerResourceDesc =
				createExternalResourceDescription(StemmerResource_MauiImpl.class,"xx");
		//,						StemmerResource_MauiImpl.PARAM_LANGUAGE, language);
//		Stemmer stemmer = new FrenchStemmer(); //->
		
		// document encoding
		String encoding = "UTF-8";
		
		// vocabulary to use for term assignment
		String vocabulary = "src/test/resources/data/vocabularies/agrovoc_fr.rdf.gz";
		String format = "skos";
		
		// how many topics per document to extract
//		int numTopicsToExtract = 8; TODO Find a way to add this parameter
		
		// maui objects
//		MauiModelBuilder modelBuilder = new MauiModelBuilder();
		/* MauiTopicExtractor topicExtractor = new MauiTopicExtractor(); */
		
		// Settings for the model builder
//		modelBuilder.inputDirectoryName = trainDir;
//		modelBuilder.modelName = modelName;
//		modelBuilder.vocabularyFormat = format;
//		modelBuilder.vocabularyName = vocabulary;
//		modelBuilder.stemmer = stemmer;
//		modelBuilder.stopwords = stopwords;
//		modelBuilder.documentLanguage = language;
//		modelBuilder.documentEncoding = encoding;
		Boolean serialize = true;
		
		// Which features to use?
		Boolean basicFeatures = true;
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
		Boolean saveModel = true;
		
		// UIMA : Run in UIMA pipeline
		CollectionReader reader = createReader(
	                DocumentsReader.class,  DocumentsReader.PARAM_DIRECTORY_NAME, trainDir 
	        );
		AnalysisEngine manualTopicsReader = createEngine(ManualTopicsReader.class);
		ExternalResourceDescription mainFilterResourceDesc =
				createExternalResourceDescription(MauiFilterResource.class,"xx");
		ExternalResourceDescription allTopicsResourceDesc =
				createExternalResourceDescription(TopicBag_Impl.class,"xx");
		AnalysisEngine stanfordSegmenter = createEngine(StanfordSegmenter.class);
		String cutOffTopicProbability = "0.0"; // TODO Find default value in Maui code
		int topicsPerDocument = 8;
		AnalysisEngine keyphraseExtractor = createEngine(KeyphraseExtractor.class,
				KeyphraseExtractor.RES_FILTER, mainFilterResourceDesc,
				KeyphraseExtractor.RES_ALL_TOPICS, allTopicsResourceDesc,
				KeyphraseExtractor.CUT_OFF_TOPIC_PROBABILITY, cutOffTopicProbability,
				KeyphraseExtractor.TOPIC_PER_DOCUMENT, topicsPerDocument);
		AnalysisEngine uimaModelBuilder = createEngine(ModelBuilderUIMA.class, 
				ModelBuilderUIMA.INPUT_DIRECTORY_NAME,trainDir,
				ModelBuilderUIMA.MODEL_NAME,modelName,
				ModelBuilderUIMA.VOCABULARY_FORMAT,format,
				ModelBuilderUIMA.VOCABULARY_NAME, vocabulary,
				ModelBuilderUIMA.DOCUMENT_LANGUAGE, language,
				ModelBuilderUIMA.DOCUMENT_ENCODING, encoding,
				ModelBuilderUIMA.SERIALIZE,serialize,
				ModelBuilderUIMA.BASIC_FEATURES, basicFeatures,
				ModelBuilderUIMA.KEYPHRASENESS_FEATURE,keyphrasenessFeature,
				ModelBuilderUIMA.FREQUENCY_FEATURES,frequencyFeatures,
				ModelBuilderUIMA.POSITIONS_FEATURES,positionsFeatures,
				ModelBuilderUIMA.LENGTH_FEATURE,lengthFeature,
				ModelBuilderUIMA.THESAURUS_FEATURES,thesaurusFeatures,
				ModelBuilderUIMA.SAVE_MODEL,saveModel,
				ModelBuilderUIMA.RES_STOPWORDS,stopWordsResourceDesc,
				ModelBuilderUIMA.RES_STEMMER, stemmerResourceDesc
				);
		SimplePipeline.runPipeline(
        		reader,
        		manualTopicsReader,
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

