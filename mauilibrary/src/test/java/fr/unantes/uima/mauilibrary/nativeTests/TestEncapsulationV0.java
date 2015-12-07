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

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import fr.unantes.uima.mauilibrary.annotator.CandidateExtractorWrapper;
import fr.unantes.uima.mauilibrary.annotator.CandidateExtractor_TF;
import fr.unantes.uima.mauilibrary.annotator.Classifier_ImplBase;
import fr.unantes.uima.mauilibrary.annotator.ModelBuilderV0;
import fr.unantes.uima.mauilibrary.draft.CandidateExtractorDraft;
import fr.unantes.uima.mauilibrary.model.ModelBuilderUIMA;
import fr.unantes.uima.mauilibrary.notworking.KeyphraseExtractor;
import fr.unantes.uima.mauilibrary.reader.DocumentsReader;
import fr.unantes.uima.mauilibrary.reader.ManualTopicsReader;
import fr.unantes.uima.mauilibrary.reader.ManualTopicsReaderV0;
import fr.unantes.uima.mauilibrary.refactoring.MauiFilterV0;
import fr.unantes.uima.mauilibrary.resource.MauiFilterResource;
import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.TopicBag_Impl;
import fr.unantes.uima.mauilibrary.tokenizer.LineSplitter;
import fr.unantes.uima.mauilibrary.tokenizer.WhitespaceTokenizer;
import fr.unantes.uima.mauilibrary.writer.SyntaxicAnnotationWriter;
import fr.unantes.uima.mauilibrary.writer.TopicWriter;

/**
 * 
 * @author solenee
 *
 */
public class TestEncapsulationV0 {

	@Test
	public void testFrench() throws Exception {
		
		String modelName = "src/test/resources/results/modelUIMA";
		// location of the data
		String trainDir = "src/test/resources/data/term_assignment/train_fr";
		String testDir = "src/test/resources/data/term_assignment/test_fr";
		
		// language specific settings
		String language = "fr";
		
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
	                DocumentsReader.PARAM_DIRECTORY_NAME, testDir,
	                DocumentsReader.DOCUMENT_LANGUAGE, language
	        );
		AnalysisEngine manualTopicsReader = createEngine(ManualTopicsReaderV0.class);
		
		
		
		AnalysisEngine modelBuilder = createEngine(ModelBuilderV0.class,
				ModelBuilderV0.DOCUMENT_LANGUAGE, language,
				ModelBuilderV0.MODEL_NAME, modelName,
				ModelBuilderV0.RES_STEMMER, stemmerResourceDesc,
				ModelBuilderV0.RES_STOPWORDS, stopWordsResourceDesc,
				ModelBuilderV0.RES_MODEL, mauiFilterResourceDesc); // TODO
		
		// Build model : training
		SimplePipeline.runPipeline(
        		reader,
        		manualTopicsReader,
        		modelBuilder
        		);

		//-------------------------------------------
		/*
		mauiFilterResourceDesc =
				createExternalResourceDescription(MauiFilterV0.class, modelName);
		
		AnalysisEngine topicExtractor =  createEngine(CandidateExtractorWrapper.class,
    			CandidateExtractorWrapper.RES_STOPWORDS,stopWordsResourceDesc,
    			CandidateExtractorWrapper.RES_STEMMER, stemmerResourceDesc);

		AnalysisEngine topicWriter = createEngine(TopicWriter.class,
				TopicWriter.PATH_FILE, "src/test/resources/results/test_v1.results"
				);
		
		// Extract topics : test
		SimplePipeline.runPipeline(
        		reader,
        		manualTopicsReader,
        		topicExtractor,
        		topicWriter
        		);
        		*/
	}
}
