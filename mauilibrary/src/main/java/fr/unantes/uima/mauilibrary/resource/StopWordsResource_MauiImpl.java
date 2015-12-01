package fr.unantes.uima.mauilibrary.resource;

import java.util.regex.Pattern;

import org.apache.uima.UIMAFramework;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import com.entopix.maui.stopwords.Stopwords;
import com.entopix.maui.stopwords.StopwordsFactory;


/**
 * @see StopWordsResource
 * @author solenee
 *
 */
public class StopWordsResource_MauiImpl implements StopWordsResource, SharedResourceObject {

	Logger logger = UIMAFramework.getLogger(StopWordsResource_MauiImpl.class); 
	
	public static final String PARAM_LANGUAGE = "LANGUAGE";
	@ConfigurationParameter(name = PARAM_LANGUAGE,
			description = "default language for the text",
			mandatory = false, defaultValue = "en")
	private String language;
	
	/** Maui Stopwords object */
	private Stopwords mauiStopwords;
	
	public void load(DataResource aData) throws ResourceInitializationException {
		// Create Maui Stopwords object
		logger.log(Level.INFO, "language == "+language);
	}

	public boolean isWord(String key) {
		return Pattern.matches("(\\w|à|â|ä|é|è|ê|ë|î|ï|ô|ö|û|ü|ç)+", key);
	}
	public Boolean isStopword(String key) {
		return mauiStopwords.isStopword(key) || (!isWord(key));
	}

	public Stopwords getMauiStopwords() {
		return mauiStopwords;
	}

	public void setLanguage(String language) {
		if (this.language == null || (this.language != language)) {
			this.language = language;
			mauiStopwords = StopwordsFactory.makeStopwords(language);
		}
		
	}

}
