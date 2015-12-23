package fr.unantes.uima.mauilibrary.nativeTests;

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

import fr.unantes.uima.mauilibrary.pipeline.MauiPipelines;

/**
 * Test that using the UIMA encapsulation of maui yields the same results (see test_v0.results) as
 * using the native library (see console)
 * 
 * @author solenee
 * 
 */
public class TestFunctionalCorrectness {

	static String language = "en";

	static String modelNameMAUI = "src/test/resources/results/maui_model";
	static String modelNameUIMA = "src/test/resources/results/modelUIMA";

	static String trainDir = "src/test/resources/data/automatic_tagging/train";

	static String testDir = "src/test/resources/data/automatic_tagging/test";

	@Before
	public void testUIMA() throws Exception {
		// 1- Build model
		MauiPipelines.buildExtrationModel(language, modelNameUIMA, trainDir);

		// 2- Extract topics
		String resultsFilePath = "src/test/resources/results/test_v0.results";
		MauiPipelines.extractTopics(language, modelNameUIMA, testDir,
				resultsFilePath);
	}

	@Test
	public void testMAUI() throws Exception {

		// --------------
		// 1- Build model
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
		String vocabulary = "none";// "src/test/resources/data/vocabularies/agrovoc_fr.rdf.gz";
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
		MauiFilter filter = modelBuilder.buildModel(DataLoader
				.loadTestDocuments(trainDir));
		modelBuilder.saveModel(filter);

		// ------------------
		// 2- Extract topics
		// maui objects
		MauiTopicExtractor topicExtractor = new MauiTopicExtractor();

		// Settings for the topic extractor
		topicExtractor.inputDirectoryName = testDir;
		topicExtractor.modelName = modelNameMAUI;
		topicExtractor.vocabularyName = vocabulary;
		topicExtractor.vocabularyFormat = format;
		topicExtractor.stemmer = stemmer;
		topicExtractor.stopwords = stopwords;
		topicExtractor.documentLanguage = language;
		// topicExtractor.topicsPerDocument = numTopicsToExtract;
		topicExtractor.cutOffTopicProbability = 0.0;
		topicExtractor.serialize = true;

		// Run topic extractor
		topicExtractor.loadModel();
		topicExtractor.extractTopics(DataLoader.loadTestDocuments(testDir));
	}

}
