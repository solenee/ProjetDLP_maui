package fr.unantes.uima.mauilibrary.resource;

import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import com.entopix.maui.stopwords.Stopwords;
import com.entopix.maui.stopwords.StopwordsFactory;

/**
 * @see StopWordsResource
 * @author solenee
 *
 */
public class StopWordsResource_MauiImpl implements StopWordsResource, SharedResourceObject {

	public static final String PARAM_LANGUAGE = "LANGUAGE";
	@ConfigurationParameter(name = PARAM_LANGUAGE,
			description = "default language for the text",
			mandatory = false, defaultValue = "en")
	private String language;
	
	/** Maui Stopwords object */
	private Stopwords mauiStopwords;
	
	public void load(DataResource aData) throws ResourceInitializationException {
		// Create Maui Stopwords object
		mauiStopwords = StopwordsFactory.makeStopwords(language);
	}

	public Boolean isStopword(String key) {
		return mauiStopwords.isStopword(key);
	}

	public Stopwords getMauiStopwords() {
		return mauiStopwords;
	}

}
