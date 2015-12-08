package fr.unantes.uima.mauilibrary.resource;

import org.apache.uima.UIMAFramework;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import com.entopix.maui.stemmers.FrenchStemmer;
import com.entopix.maui.stemmers.GermanStemmer;
import com.entopix.maui.stemmers.NoStemmer;
import com.entopix.maui.stemmers.PorterStemmer;
import com.entopix.maui.stemmers.SpanishStemmer;
import com.entopix.maui.stemmers.Stemmer;

public class StemmerResource_MauiImpl implements StemmerResource, SharedResourceObject{

	Logger logger = UIMAFramework.getLogger(StemmerResource_MauiImpl.class);
	
	/*public static final String PARAM_LANGUAGE = "LANGUAGE";
	@ConfigurationParameter(name = PARAM_LANGUAGE,
			description = "default language for the text",
			mandatory = false, defaultValue = "en")*/
	private String language;
	
	/** Maui Stemmer object */
	private Stemmer mauiStemmer;
	
	public void load(DataResource aData) throws ResourceInitializationException {
		logger.log(Level.INFO, "language == "+language);
		logger.log(Level.INFO, aData.toString());
		initStemmer();
	}

	public String stem(String str) {
		return mauiStemmer.stem(str);
	}
	
	/**
	 * Get Maui Stemmer object
	 * @return mauiStemmer
	 */
	public Stemmer getMauiStemmer() {
		return mauiStemmer;
	}
	
	private void initStemmer() {
		if (language == null) {
			mauiStemmer = new NoStemmer();
		} else if (language.equals("en")) {
			mauiStemmer = new PorterStemmer();
		} else if (language.equals("fr")) {
			mauiStemmer = new FrenchStemmer();
		} else if (language.equals("es")) {
			mauiStemmer = new SpanishStemmer();
		} else if (language.equals("de")) {
			mauiStemmer = new GermanStemmer();
		} else {
			mauiStemmer = new NoStemmer();
		}
	}

	public void setLanguage(String language) {
		if ((this.language == null) || (this.language != language)) {
			this.language = language;
			initStemmer();
		}
		logger.log(Level.INFO, "language == "+language);
	}

}
