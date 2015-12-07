package fr.unantes.uima.mauilibrary.tokenizer;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import fr.unantes.uima.mauilibrary.types.LineAnnotation;

public class WhitespaceTokenizer
    extends JCasAnnotator_ImplBase
{

    // Adapt the whitespace tokenizer into a UIMA annotator
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
    	for (LineAnnotation line : JCasUtil.select(jcas, LineAnnotation.class)) {
        String pText = line.getCoveredText();
        int boundary = pText.length();
        int start = 0;
        int end = 0;

        while (start < boundary) {
            while (start < boundary && (Character.isSpaceChar(pText.charAt(start)) || Character.isWhitespace(pText.charAt(start))) ) {
                start++;
            }

            for (end = start; end < boundary && !(Character.isSpaceChar(pText.charAt(end)) || Character.isWhitespace(pText.charAt(end))); end++);
            
            if (start < boundary) {
                Token tokenAnnotation = new Token(jcas);
                tokenAnnotation.setBegin(start+line.getBegin());
                tokenAnnotation.setEnd(end+line.getBegin());
                tokenAnnotation.addToIndexes();
            }
            start = end + 1;
        }
    	}
    }
}
