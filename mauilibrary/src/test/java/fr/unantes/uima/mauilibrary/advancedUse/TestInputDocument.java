package fr.unantes.uima.mauilibrary.advancedUse;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import org.apache.uima.collection.CollectionReader;
import org.junit.Test;

import fr.unantes.uima.mauilibrary.pipeline.MauiPipelines;
import fr.unantes.uima.mauilibrary.reader.DocumentsReader;

/**
 * Show examples about how to custom your own reader for the input 
 * documents if you don't want to use Maui way (directory with txt files)
 * 
 * When designing your reader you must create a FileDescription annotation 
 * and add it to the index, set the document language and set the Jcas' document text
 * with the content of the document to analyze (training or test file)  
 * @author solenee
 *
 */
public class TestInputDocument {

	@Test
	public void testWebpage() throws Exception {
		// 1- Build model
		String language = "en";
		String modelNameUIMA = "src/test/resources/results/modelUIMA-term_assignment";
		String trainDir = "src/test/resources/data/term_assignment/train";
		// Uncomment only if you deleted the file :p Re-buliding it ttakes a while !
		//MauiPipelines.buildExtrationModel(language, modelNameUIMA, trainDir);
		
		// 2- Extract topics for a web page
		String resultsFilePath = "src/test/resources/advancedUse/results";
		String url = "https://en.wikipedia.org/wiki/Search_engine_indexing#Index_data_structures";
		String cssSelector = "div.mw-content-ltr";
		String keyDirectory = "src/test/resources/advancedUse";
		CollectionReader reader = createReader(WebPageReader.class,
				WebPageReader.PARAM_LANGUAGE, language,
				WebPageReader.PARAM_SELECTOR, cssSelector,
				WebPageReader.PARAM_STORAGE_DIR, keyDirectory,
				WebPageReader.PARAM_URL, url
				);
		MauiPipelines.extractTopics(language, modelNameUIMA, resultsFilePath, reader);
	}

}
