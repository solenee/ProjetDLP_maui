/**
 * 
 */
package fr.unantes.uima.mauilibrary.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.entopix.maui.stopwords.Stopwords;

/**
 * Interface which models the stop word list ; 
 * Declare the methods handled by the annotators to access the resource
 * @author solenee 
 */
public interface StopWordsResource {
	/** Test if a word is present in the list */
	public Boolean isStopword (String key);
	
	/** Obtain Maui Stopwords object */
	public Stopwords getMauiStopwords();
	
	public void setLanguage(String language);
}