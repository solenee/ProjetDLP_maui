package fr.unantes.uima.mauilibrary.writer;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import fr.unantes.uima.mauilibrary.types.CandidateAnnotation;
import fr.unantes.uima.mauilibrary.types.LineAnnotation;
import fr.unantes.uima.mauilibrary.types.PhraseAnnotation;

public class SyntaxicAnnotationWriter extends JCasAnnotator_ImplBase {

	Logger logger =  UIMAFramework.getLogger(SyntaxicAnnotationWriter.class);
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		StringBuffer sb = new StringBuffer();		
		sb = new StringBuffer();
		sb.append("================ LINE ==================\n");
		for (LineAnnotation line : JCasUtil.select(aJCas, LineAnnotation.class)) {
			sb.append("[Line] "+line.getCoveredText()+"\n");
		}
		logger.log(Level.INFO , sb.toString());
		
		sb.append("================ TOKEN ==================\n");
		for (Token token : JCasUtil.select(aJCas, Token.class)) {
			sb.append("[Token] "+token.getCoveredText()+"\n");
		}
		logger.log(Level.INFO , sb.toString());
		
		sb = new StringBuffer();
		sb.append("================ PHRASE ==================\n");
		for (PhraseAnnotation phrase : JCasUtil.select(aJCas, PhraseAnnotation.class)) {
			sb.append("[Phrase] ("+phrase.getCoveredText()
					+", "+phrase.getFirstOccurence()+"\n");
		}
		logger.log(Level.INFO , sb.toString());
		
		sb = new StringBuffer();
		sb.append("================ CANDIDATE ==================\n");
		for (CandidateAnnotation candidate : JCasUtil.select(aJCas, CandidateAnnotation.class)) {
			sb.append("[Candidate] (CoveredText : "+candidate.getCoveredText()
					+", Name : "+candidate.getName()
					+", Score : "+candidate.getScore()+"\n");
		}
		logger.log(Level.INFO , sb.toString());
	}

}
