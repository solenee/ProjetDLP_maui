package fr.unantes.uima.mauilibrary.resource;

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
	private String language = "en";
	
	/** Maui Stopwords object */
	private Stopwords mauiStopwords;
	
	public void load(DataResource aData) throws ResourceInitializationException {
		// Create Maui Stopwords object
		logger.log(Level.INFO, "language == "+language);
	}

	public Boolean isStopword(String key) {
		return mauiStopwords.isStopword(key);
	}

	public Stopwords getMauiStopwords() {
		if (mauiStopwords == null) {
			mauiStopwords = StopwordsFactory.makeStopwords(language);
		}
		return mauiStopwords;
	}

	public void setLanguage(String language) {
		this.language = language;		
	}

}
