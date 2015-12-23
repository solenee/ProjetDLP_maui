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

/**
 * Encapsulates a Maui Stemmer created according to the language :
 * en : PorterStemmer
 * fr : FrenchStemmer
 * es : SpanishStemmer
 * de : GermanStemmer
 * other : NoStemmer
 * @author solenee
 *
 */
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

	/**
	 * Returns the stem of the given string
	 */
	public String stem(String str) {
		return mauiStemmer.stem(str);
	}
	
	/**
	 * Get Maui Stemmer object
	 * @return mauiStemmer; null if this.language is not supported
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

	/**
	 * Set language and modify the stemmer accordingly if needed
	 */
	public void setLanguage(String language) {
		if ((this.language == null) || (this.language != language)) {
			this.language = language;
			initStemmer();
		}
		logger.log(Level.INFO, "language == "+language);
	}

}
