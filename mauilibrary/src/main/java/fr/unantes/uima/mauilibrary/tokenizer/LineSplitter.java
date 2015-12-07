package fr.unantes.uima.mauilibrary.tokenizer;

import java.text.BreakIterator;
import java.util.StringTokenizer;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import fr.unantes.uima.mauilibrary.types.LineAnnotation;

public class LineSplitter extends JCasAnnotator_ImplBase {

	public void splitWithLineInstance(JCas jcas) {
		String document = jcas.getDocumentText();
        BreakIterator tokenizer = BreakIterator.getLineInstance();
		tokenizer.setText(document);
        int start = tokenizer.first();
        
        for (int end = tokenizer.next(); end != BreakIterator.DONE;
        		start = end, end = tokenizer.next()) {
        	if (!document.substring(start,end).matches("(\\s)*")) {
	            LineAnnotation a = new LineAnnotation(jcas);
	            a.setBegin(start);
	            a.setEnd(end);
	            a.addToIndexes(); // don't forget !!!!
        	}
        }
	}
	
	public void splitWithSentenceInstance(JCas jcas) {
		String document = jcas.getDocumentText();
        BreakIterator tokenizer = BreakIterator.getSentenceInstance();
		tokenizer.setText(document);
        int start = tokenizer.first();
        
        for (int end = tokenizer.next(); end != BreakIterator.DONE;
        		start = end, end = tokenizer.next()) {
        	if (!document.substring(start,end).matches("(\\s)*")) {
	            LineAnnotation a = new LineAnnotation(jcas);
	            a.setBegin(start);
	            a.setEnd(end);
	            a.addToIndexes(); // don't forget !!!!
        	}
        }
	}
	
	// Adapt the line tokenizer into a UIMA annotator
	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		/*StringTokenizer tok = new StringTokenizer(text, "\n");
		StringTokenizer tok = new StringTokenizer(text, "\n");
		int pos = 0;
		int totalFrequency = 0;
		int firstWord = 0;
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			*/
		splitWithSentenceInstance(jcas);
	}

}
