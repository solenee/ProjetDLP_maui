package fr.unantes.uima.mauilibrary.draft;

import java.util.HashMap;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import com.entopix.maui.filters.MauiFilter;
import com.entopix.maui.util.Candidate;

import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource;
import fr.unantes.uima.mauilibrary.resource.StopWordsResource_MauiImpl;
import fr.unantes.uima.mauilibrary.types.CandidateAnnotation;

public class CandidateExtractorWrapper extends JCasAnnotator_ImplBase {
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
	
	private MauiFilter mauiFilter;

	private void update(String language) {
		System.out.println(stopwords);
		System.out.println(stemmer);
		stopwords.setLanguage(language);
		stemmer.setLanguage(language);
		mauiFilter.setStopwords(stopwords.getMauiStopwords());
		mauiFilter.setStemmer(stemmer.getMauiStemmer());
		
	}
	
	public void clone(CandidateAnnotation anno, Candidate cand) {
		anno.setBestFullForm(cand.getBestFullForm());
		anno.setFirstOccurence(cand.getFirstOccurrence());
		anno.setFrequency(cand.getFrequency());
	}
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		mauiFilter = new MauiFilter();
		mauiFilter.setVocabularyName(vocabularyName);
	}
	
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		update(jCas.getDocumentLanguage());
		HashMap<String, Candidate> candidates = mauiFilter.getCandidates(jCas.getDocumentText());
		// Add candidates to the resource and jcas
		for (String key : candidates.keySet()) {
			CandidateAnnotation cAnno = new CandidateAnnotation(jCas);
			cAnno.setName(key);
			//clone(cAnno, candidates.get(key));
			cAnno.addToIndexes();
		}
	}
	
	/*@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		mauiFilter = new MauiFilter();
		mauiFilter.setVocabularyName(vocabularyName);
		stopwords = new StopWordsResource_MauiImpl();
		stemmer = new StemmerResource_MauiImpl();
	}
	
	
	
	public void clone(CandidateAnnotation anno, Candidate cand) {
		anno.setBestFullForm(cand.getBestFullForm());
		anno.setFirstOccurence(cand.getFirstOccurrence());
		anno.setFrequency(cand.getFrequency());
	}
	
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		
	}*/
}
