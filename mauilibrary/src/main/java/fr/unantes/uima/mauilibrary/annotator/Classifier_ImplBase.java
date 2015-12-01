package fr.unantes.uima.mauilibrary.annotator;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Logger;

import fr.unantes.uima.mauilibrary.types.CandidateAnnotation;
import fr.unantes.uima.mauilibrary.types.TopicAnnotation;
import fr.unantes.uima.mauilibrary.utils.CandidatesComparator;

public class Classifier_ImplBase extends JCasAnnotator_ImplBase implements Classifier {

	Logger logger = UIMAFramework.getLogger(Classifier_ImplBase.class);

	public static final String TOPIC_PER_DOCUMENT = "TOPIC_PER_DOCUMENT";
	@ConfigurationParameter(name = TOPIC_PER_DOCUMENT, description = "number of topics per documents", mandatory = true)
	private int topicsPerDocument;
	
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		List<CandidateAnnotation> candidates  = new ArrayList<CandidateAnnotation>(JCasUtil.select(jCas, CandidateAnnotation.class));
		Collections.sort(candidates, new CandidatesComparator());
		for (int i=0; i <topicsPerDocument && i<candidates.size(); i++) {
			CandidateAnnotation elected  = candidates.get(i);
			TopicAnnotation tAnno = new TopicAnnotation(jCas);
			tAnno.setText(elected.getLemme());
			tAnno.setScore(elected.getScore());
			tAnno.addToIndexes();
		}
	}

}
