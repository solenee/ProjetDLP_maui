package fr.unantes.uima.mauilibrary.nativeTests;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createExternalResourceDescription;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ExternalResourceDescription;
import org.junit.Test;

import fr.unantes.uima.mauilibrary.draft.CandidateExtractorWrapper;
import fr.unantes.uima.mauilibrary.reader.DocumentsReader;
import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource_MauiImpl;
import fr.unantes.uima.mauilibrary.tokenizer.LineSplitter;
import fr.unantes.uima.mauilibrary.tokenizer.WhitespaceTokenizer;
import fr.unantes.uima.mauilibrary.writer.SyntaxicAnnotationWriter;

/**
 * 
 * @author solenee
 *
 */
public class TestV2_CandidateAnnotations {

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


    	/*AnalysisEngine classifier_TF = createEngine(Classifier_ImplBase.class,
				Classifier_ImplBase.TOPIC_PER_DOCUMENT, 8
				);
				*/
    	AnalysisEngine syntaxicWriter = createEngine(SyntaxicAnnotationWriter.class);
		/*AnalysisEngine writer = createEngine(TopicWriter.class,
				TopicWriter.PATH_FILE, "src/test/resources/results/test_v1.results"
				);
		*/		
		SimplePipeline.runPipeline(
        		reader,
        		//lineSplitter,
        		//tokenizer,
        		candidateExtractor,
        		syntaxicWriter
        		);
	}
}
