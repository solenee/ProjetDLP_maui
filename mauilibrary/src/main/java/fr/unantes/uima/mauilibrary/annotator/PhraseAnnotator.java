package fr.unantes.uima.mauilibrary.annotator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import com.entopix.maui.util.Candidate;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource;
import fr.unantes.uima.mauilibrary.types.LineAnnotation;
import fr.unantes.uima.mauilibrary.types.PhraseAnnotation;

/**
 * TODO 
 * @author solenee
 *
 */
public class PhraseAnnotator extends JCasAnnotator_ImplBase{

	/**
	 * The minimum number of occurences of a phrase
	 */
	private int minOccurFrequency = 1;
	
	/**
	 * Maximum length of phrases
	 */
	private int maxPhraseLength = 5;
	
	/**
	 * Minimum length of phrases
	 */
	private int minPhraseLength = 1;
	
	/**
	 * Not used in this version
	 */
	private String vocabularyName = "none";
	
	public final static String RES_STOPWORDS = "stopwordsResource";
	@ExternalResource(key = RES_STOPWORDS)
	private StopWordsResource stopwords;
	
	
	public final static String RES_STEMMER = "stemmerResource";
	@ExternalResource(key = RES_STEMMER)
	private StemmerResource_MauiImpl stemmer;
	
	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
	}
	
	
	/**
	 * Copied from MauiFilter
	 * @return Generates a normalized preudo phrase from a string. A pseudo phrase is a
	 * version of a phrase that only contains non-stopwords, which are stemmed
	 * and sorted into alphabetical order.
	 */
	public String pseudoPhrase(String str) {

		String result = "";

		str = str.toLowerCase();

		// sort words alphabetically
		String[] words = str.split(" ");
		Arrays.sort(words);

		for (String word : words) {

			// remove all stopwords
			if (!stopwords.isStopword(word)) {

				// remove all apostrophes
				int apostr = word.indexOf('\'');
				if (apostr != -1) {
					word = word.substring(0, apostr);
				}

				// ste	mm the remaining words
				word = stemmer.stem(word);

				result += word + " ";
			}
		}
		result = result.trim();
		if (!result.equals("")) {
			return result;
		}
		return null;
	}

	/**
	 * Implements the initialization of candidatesNames in MauiFilter.getCandidates(String)
	 */
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		stopwords.setLanguage(jCas.getDocumentLanguage());
		stemmer.setLanguage(jCas.getDocumentLanguage());
		
		int pos = 0;
		int totalFrequency = 0;
		int firstWord = 0;
		
		HashMap<String, Candidate> candidatesTable = new HashMap<String, Candidate>();
		String[] buffer = new String[maxPhraseLength];
		// Extract strings of a predefined length from text : phrase
		for (LineAnnotation line : JCasUtil.select(jCas, LineAnnotation.class)) {
			List<Token> words =  JCasUtil.selectCovered(Token.class, line);
			int numSeen = 0;
			int it = 0;
			int start = -1;
			int end = start;
			while(it<words.size()) {
				pos++;

				String word = words.get(it).getCoveredText();
				if (start == -1) { 
					start = words.get(it).getBegin(); end = start;
				}

				// Store word in buffer
				for (int i = 0; i < maxPhraseLength - 1; i++) {
					buffer[i] = buffer[i + 1];
				}
				buffer[maxPhraseLength - 1] = word;

				// How many are buffered?
				numSeen++;
				if (numSeen > maxPhraseLength) {
					numSeen = maxPhraseLength;
				}

				// Don't consider phrases that end with a stop word
				if (stopwords.isStopword(buffer[maxPhraseLength - 1])) {
					it++;
					continue;
				}

				// Loop through buffer and add phrases to hashtable
				StringBuffer phraseBuffer = new StringBuffer();
				for (int i = 1; i <= numSeen; i++) {
					if (i > 1) {
						phraseBuffer.insert(0, ' ');
					}
					phraseBuffer.insert(0, buffer[maxPhraseLength - i]);

					// Don't consider phrases that begin with a stop word
					// In free indexing only
					if ((i > 1)
							&& (stopwords.isStopword(buffer[maxPhraseLength - i]))) {
						continue;
					}

					// Only consider phrases with minimum length
					if (i >= minPhraseLength) {

						// each detected candidate phase in its original
						// spelling form
						String form = phraseBuffer.toString();

						// list of candidates extracted for a given original
						// string
						// in case of term assignment more than one possible!
						ArrayList<String> candidateNames = new ArrayList<String>();

						if (vocabularyName.equals("none")) {

							// if it is free keyphrase indexing,
							// get the pseudo phrase of the original spelling
							String phrase = pseudoPhrase(form);
							if (phrase != null) {
								candidateNames.add(phrase);
								PhraseAnnotation phraseA = new PhraseAnnotation(jCas);
								phraseA.setBegin(start); //TODO Fix begin
								phraseA.setEnd(end); // TODO Fix end
								phraseA.addToIndexes();
							}
							totalFrequency++;
							//	log.info(form + ", ");

						} /*TODO else retrieving senses for form into the controlled vocabulary */

						// log.info("...conflating candidates");

						// ignore all those phrases
						// that have empty pseudo phrases or
						// that map to nothing in the vocabulary
						if (!candidateNames.isEmpty()) {

							for (String name : candidateNames) {

								Candidate candidate = candidatesTable.get(name);

								if (candidate == null) {
									// this is the first occurrence of this
									// candidate
									// create a candidate object


									firstWord = pos - i;
									candidate = new Candidate(name, form,
											firstWord);
									totalFrequency++;
									// if it's a controlled vocabulary, this
									// allows
									// retrieve how this topic is refered to
									// by a descriptor
									/* TODO	 if (!vocabularyName.equals("none")) { candidate.setTitle(vocabulary.getTerm(name));} */



								} else {

									// candidate has been observed before
									// update its values
									// log.info(form);
									firstWord = pos - i;
									candidate.recordOccurrence(form, firstWord);
									totalFrequency++;

								}
								if (candidate != null) {
									candidatesTable.put(name, candidate);
								}
							}
						}
					}
				}
				it++;
			}
		}
		Set<String> keys = new HashSet<String>();
		keys.addAll(candidatesTable.keySet());
		for (String key : keys) {
			Candidate candidate = candidatesTable.get(key);
			if (candidate.getFrequency() < minOccurFrequency) {
				candidatesTable.remove(key);
			} else {
				candidate.normalize(totalFrequency, pos);
			}
		}

	}
				

}
