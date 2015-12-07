package fr.unantes.uima.mauilibrary.annotator;

import java.util.HashMap;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import fr.unantes.uima.mauilibrary.draft.CandidateExtractor;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource;
import fr.unantes.uima.mauilibrary.types.CandidateAnnotation;
import fr.unantes.uima.mauilibrary.types.FileDescription;

public class CandidateExtractor_TF extends JCasAnnotator_ImplBase implements CandidateExtractor {

	public final static String RES_STOPWORDS = "stopwordsResource";
	@ExternalResource(key = RES_STOPWORDS)
	private StopWordsResource stopwords;
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
	}
	
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		// Initialize stopwords resource
		stopwords.setLanguage(jCas.getDocumentLanguage());
		
		
		// Compute term frequencies
		HashMap<String, Integer> termFrequencies = new HashMap<String, Integer>();
		for (Token token : JCasUtil.select(jCas, Token.class)) {
			//Discard stopwords
			/*if ( ! stopwords.isStopword(token.getLemma().getValue())) { 
				Integer currentFreq = termFrequencies.get(token.getLemma().getValue());
				if (currentFreq == null) {
					currentFreq = 0;
				}
				termFrequencies.put(token.getLemma().getValue(), currentFreq +1);
			}*/
			if ( ! stopwords.isStopword(token.getCoveredText())) { 
				Integer currentFreq = termFrequencies.get(token.getCoveredText());
				if (currentFreq == null) {
					currentFreq = 0;
				}
				termFrequencies.put(token.getCoveredText(), currentFreq +1);
			}
		}
		
		// Create CandidateAnnotation s
		for (String name : termFrequencies.keySet()) {
			CandidateAnnotation candidate = new CandidateAnnotation(jCas);
			candidate.setName(name);
			candidate.setScore(termFrequencies.get(name));
			candidate.addToIndexes();
		}
	} 

}
