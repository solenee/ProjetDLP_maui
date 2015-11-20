package fr.unantes.uima.mauilibrary.resource;

/**
 * Interface modelling a stemmer  
 * @author solenee
 *
 */
public interface StemmerResource {
	
	/**
	 * Maui Stemmer implementation
	 * @param str
	 * @return stem of str
	 * used in Maui in Vocabulary.pseudoPhrasze(String), MauiFilter.pseudoPhrase(String)  
	 */
	public String stem(String str); 
	
}
