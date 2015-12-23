package fr.unantes.uima.mauilibrary.pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceInitializationException;

import fr.unantes.uima.mauilibrary.annotator.ModelBuilderV0;
import fr.unantes.uima.mauilibrary.annotator.TopicExtractorV0;
import fr.unantes.uima.mauilibrary.reader.DocumentsReader;
import fr.unantes.uima.mauilibrary.reader.ManualTopicsReaderV0;
import fr.unantes.uima.mauilibrary.refactoring.MauiFilterV0;
import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource_MauiImpl;
import fr.unantes.uima.mauilibrary.writer.TopicWriter;

/**
 * Classic configuration of the two use cases : 
 * 1- build model with a training set
 * 2- extract topics from a test set
 */
public class MauiPipelines {

	/**
	 * Build and serialize the extraction model
	 * 
	 * @param language
	 * @param modelName
	 *            : file path where the model should be serialized
	 * @param trainDir
	 *            : directory containing the trainig set : .txt files with the
	 *            corresponding .key files
	 * @throws UIMAException
	 * @throws IOException
	 */
	public static void buildExtrationModel(String language, String modelName,
			String trainDir) throws UIMAException, IOException {
		// Stopwords resource
		ExternalResourceDescription stopWordsResourceDesc = createExternalResourceDescription(
				StopWordsResource_MauiImpl.class, "xx");
		// Stemmer resource
		ExternalResourceDescription stemmerResourceDesc = createExternalResourceDescription(
				StemmerResource_MauiImpl.class, "xx");

		// MauiFilter resource
		// Specify a fictive Url because we want to BUILD the model
		ExternalResourceDescription mauiFilterResourceDesc = createExternalResourceDescription(
				MauiFilterV0.class, "xx");

		// Run in UIMA pipeline
		CollectionReader reader = createReader(DocumentsReader.class,
				DocumentsReader.PARAM_DIRECTORY_NAME, trainDir,
				DocumentsReader.DOCUMENT_LANGUAGE, language);
		AnalysisEngine manualTopicsReader = createEngine(ManualTopicsReaderV0.class);

		AnalysisEngine modelBuilder = createEngine(ModelBuilderV0.class,
				ModelBuilderV0.DOCUMENT_LANGUAGE, language,
				ModelBuilderV0.MODEL_NAME, modelName,
				ModelBuilderV0.RES_STEMMER, stemmerResourceDesc,
				ModelBuilderV0.RES_STOPWORDS, stopWordsResourceDesc,
				ModelBuilderV0.RES_MODEL, mauiFilterResourceDesc);

		// Build model : training
		SimplePipeline.runPipeline(reader, manualTopicsReader, modelBuilder);
	}

	/**
	 * Extract topics using the 
	 * @param language
	 * @param modelName : file path of the serialized extraction model
	 * @param testDir : directory containing the test set : .txt files with the
	 *            corresponding optional .key files
	 * @param resultsFilePath : file path where the extracted topics should be written
	 * @throws UIMAException
	 * @throws IOException
	 */
	public static void extractTopics(String language, String modelName,
			String testDir, String resultsFilePath) throws UIMAException,
			IOException {
		// Standard CollectionReader
		CollectionReader reader = createReader(DocumentsReader.class,
				DocumentsReader.PARAM_DIRECTORY_NAME, testDir,
				DocumentsReader.DOCUMENT_LANGUAGE, language);
		
		extractTopics(language, modelName, resultsFilePath, reader);
	}
	
	/**
	 * Extract topics using a customed CollectionReader
	 * @param language
	 * @param modelName : file path of the serialized extraction model
	 * @param reader : customed CollectionReader to use
	 * @param resultsFilePath : file path where the extracted topics should be written
	 * @throws UIMAException
	 * @throws IOException
	 */
	public static void extractTopics(String language, String modelName, String resultsFilePath, CollectionReader customedCollectionReader) throws UIMAException,
			IOException {
		// Stopwords resource
		ExternalResourceDescription stopWordsResourceDesc = createExternalResourceDescription(
				StopWordsResource_MauiImpl.class, "xx");
		// Stemmer resource
		ExternalResourceDescription stemmerResourceDesc = createExternalResourceDescription(
				StemmerResource_MauiImpl.class, "xx");

		// -------------------------------------------
		// Run in UIMA pipeline
		CollectionReader reader = customedCollectionReader;
		AnalysisEngine manualTopicsReader = createEngine(ManualTopicsReaderV0.class);

		// MauiFilter resource
		ExternalResourceDescription mauiFilterResourceDesc = createExternalResourceDescription(
				MauiFilterV0.class, (new File(modelName)).toURI().toURL());

		AnalysisEngine topicExtractor = createEngine(TopicExtractorV0.class,
				TopicExtractorV0.RES_STEMMER, stemmerResourceDesc,
				TopicExtractorV0.RES_STOPWORDS, stopWordsResourceDesc,
				TopicExtractorV0.RES_MODEL, mauiFilterResourceDesc);

		AnalysisEngine topicWriter = createEngine(TopicWriter.class,
				TopicWriter.PATH_FILE,
				resultsFilePath);

		// Extract topics : test
		SimplePipeline.runPipeline(reader, manualTopicsReader, topicExtractor,
				topicWriter);

	}

}
