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
import fr.unantes.uima.mauilibrary.draft.CandidateExtractorDraft;
import fr.unantes.uima.mauilibrary.draft.ModelBuilderUIMA;
import fr.unantes.uima.mauilibrary.model.MauiFilterUIMA;
import fr.unantes.uima.mauilibrary.notworking.Classifier_MauiFilter;
import fr.unantes.uima.mauilibrary.notworking.KeyphraseExtractor;
import fr.unantes.uima.mauilibrary.reader.DocumentsReader;
import fr.unantes.uima.mauilibrary.reader.ManualTopicsReader;
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
public class TestV2_ClassifierMauiImpl {

	@Test
	public void testFrench() throws Exception {
		
		// location of the data
		//String trainDir = "src/test/resources/data/term_assignment/train_fr";
		String testDir = "src/test/resources/data/term_assignment/test_fr";
		
		// language specific settings
		String language = "fr";
		
		// UIMA :  Stopwords resource
		ExternalResourceDescription stopWordsResourceDesc =
				createExternalResourceDescription(StopWordsResource_MauiImpl.class, "xx");
		
		// UIMA :  Stemmer resource
		ExternalResourceDescription stemmerResourceDesc =
				createExternalResourceDescription(StemmerResource_MauiImpl.class,"xx");
		// UIMA : Run in UIMA pipeline
		CollectionReader reader = createReader(
	                DocumentsReader.class, 
	                DocumentsReader.PARAM_DIRECTORY_NAME, testDir,
	                DocumentsReader.DOCUMENT_LANGUAGE, language
	        );
		AnalysisEngine lineSplitter =  createEngine(LineSplitter.class);
		AnalysisEngine tokenizer =  createEngine(WhitespaceTokenizer.class);
		AnalysisEngine candidateExtractor =  createEngine(CandidateExtractorWrapper.class,
    			CandidateExtractorWrapper.RES_STOPWORDS,stopWordsResourceDesc,
    			CandidateExtractorWrapper.RES_STEMMER, stemmerResourceDesc);


		ExternalResourceDescription mauiFilterDesc =
				createExternalResourceDescription(MauiFilterUIMA.class,"xx");
    	AnalysisEngine classifierMauiImpl = createEngine(Classifier_MauiFilter.class,
    			Classifier_MauiFilter.TOPIC_PER_DOCUMENT, 8,
    			Classifier_MauiFilter.RES_FILTER, mauiFilterDesc
				);
		
    	AnalysisEngine syntaxicWriter = createEngine(SyntaxicAnnotationWriter.class);
		AnalysisEngine topicWriter = createEngine(TopicWriter.class,
			TopicWriter.PATH_FILE, "src/test/resources/results/test_v2.results"
				);
		
		SimplePipeline.runPipeline(
        		reader,
        		candidateExtractor,
        		syntaxicWriter,
        		classifierMauiImpl,
        		topicWriter
        		);
	}
}
