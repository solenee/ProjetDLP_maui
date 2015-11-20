package fr.unantes.uima.mauilibrary.resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.entopix.maui.stemmers.Stemmer;
import com.entopix.maui.stopwords.Stopwords;
import com.entopix.maui.vocab.VocabularyStore;
import com.entopix.maui.vocab.Vocabulary.VocabularyException;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * Interface which models the vocabulary list ;
 * Builds an index with the content of the controlled vocabulary.
 * Accepts vocabularies as rdf files (SKOS format) and in plain text format:
 * vocabulary_name.en (with "ID TERM" per line) - descriptors & non-descriptors
 * vocabulary_name.use (with "ID_NON-DESCR \t ID_DESCRIPTOR" per line)
 * vocabulary_name.rel (with "ID \t RELATED_ID1 RELATED_ID2 ... " per line)
 */

public interface VocabularyResource {
	/** Initializes vocabulary from a file path
	 *
	 * Given the file path to the vocabulary and the format, 
	 * it first checks whether this file exists:<br>
	 * - vocabularyName.rdf or vocabularyName.rdf.gz if skos format is selected<br>
	 * - or a set of 3 flat txt files starting with vocabularyName and with extensions<br>
	 * <li>.en (id term) - the path to this file should be supplied as the main parameters
	 * <li>.use (non-descriptor \t descriptor)
	 * <li>.rel (id \t related_id1 related_id2 ...)
	 * If the required files exist, the vocabulary index is built.
	 * Used in CrossValidation Test
	 *
	 * @param vocabularyName The name of the vocabulary file (before extension).
	 * @param vocabularyFormat The format of the vocabulary (skos or text).
	 * @throws IOException
	 * @throws VocabularyException 
	 * */
	public void initializeVocabulary(String vocabularyName, String vocabularyFormat);

}
