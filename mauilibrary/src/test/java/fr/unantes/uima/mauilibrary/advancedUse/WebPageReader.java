package fr.unantes.uima.mauilibrary.advancedUse;

import java.io.IOException;

import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import fr.unantes.uima.mauilibrary.reader.DocumentsReader;
import fr.unantes.uima.mauilibrary.types.FileDescription;


/** 
 * Reads only one web page, the content of the css selector passed as argument via PARAM_SELECTOR. 
 * 
 * @author seholie
 *
 */
public class WebPageReader extends JCasCollectionReader_ImplBase {

	/**
	 * The Web page to read
	 */
	private Document doc;

	Logger logger = UIMAFramework.getLogger(WebPageReader.class);
	
	public static final String PARAM_SELECTOR = "SELECTOR";
	@ConfigurationParameter(name = PARAM_SELECTOR,
			description = "css element to analyse",
			mandatory = false, defaultValue = "body")
	private String cssSelector;
	
	public static final String PARAM_URL = "URL";
	@ConfigurationParameter(
	name = PARAM_URL,
	description = "Sets the url of the web page to read",
	mandatory = true, defaultValue = "https://www.facebook.com/")
	private String url;
	
	public static final String PARAM_STORAGE_DIR = "DIRECTORY";
	@ConfigurationParameter(
	name = PARAM_STORAGE_DIR,
	description = "Directory where .key files are supposed to be",
	mandatory = false, defaultValue = "")
	private String keyDirectory;
	
	public static final String PARAM_LANGUAGE = "LANGUAGE";
	@ConfigurationParameter(name = PARAM_LANGUAGE,
			description = "default language for the text",
			mandatory = false, defaultValue = "en")
	private String language;
	
	private int current;
	private final int max = 1; // only one page to read
	
	public Progress[] getProgress() {
		Progress[] res = new ProgressImpl[1];
		res[0] = new ProgressImpl(current, max, Progress.ENTITIES);
		return res;
	}

	public boolean hasNext() throws IOException, CollectionException {
		return (current < max);
	}

	@Override
	public void getNext(JCas jcas) throws IOException, CollectionException {
		Elements text = doc.select(cssSelector);
		jcas.setDocumentText(text.text());
		// Set language
		jcas.setDocumentLanguage(language);
		// Add FileDescription annotation
		FileDescription fDesc = new FileDescription(jcas);
		String pageTitle = doc.select("title").text();
		fDesc.setFileName(pageTitle);
		fDesc.setAbsolutePath(keyDirectory+"/"+pageTitle); // Don't add .key extension here !!!
		fDesc.addToIndexes();
		
		current ++;
	}
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		current = 0;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResourceInitializationException();
		}
	}

}
