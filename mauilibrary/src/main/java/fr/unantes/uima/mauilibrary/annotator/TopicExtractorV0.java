package fr.unantes.uima.mauilibrary.annotator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import com.entopix.maui.util.Topic;

import fr.unantes.uima.mauilibrary.refactoring.MauiFilter_UIMA;
import fr.unantes.uima.mauilibrary.types.CandidateAnnotation;
import fr.unantes.uima.mauilibrary.types.FileDescription;
import fr.unantes.uima.mauilibrary.types.TopicAnnotation;
import fr.unantes.uima.mauilibrary.utils.CandidatesComparator;

public class TopicExtractorV0 extends JCasAnnotator_ImplBase {

	public final static String RES_MODEL = "RES_MODEL";
	@ExternalResource(key = RES_MODEL)
	private MauiFilter_UIMA extractionModel;
	
	//TODO Define as parameter
	private int topicsPerDocument = 8;
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
	}
	
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		String filename = null;
		String manualTopicsText = null;
		for (FileDescription fDesc : JCasUtil.select(jCas, FileDescription.class)) {
			// should have 1 element
			filename = fDesc.getFileName();
			manualTopicsText = fDesc.getManualTopics();
		}
		List<Topic> topics = extractionModel.extractTopics(filename, jCas.getDocumentText(),
				manualTopicsText, topicsPerDocument);
		List<CandidateAnnotation> candidates  = new ArrayList<CandidateAnnotation>(JCasUtil.select(jCas, CandidateAnnotation.class));
		Collections.sort(candidates, new CandidatesComparator());
		for (Topic elected : topics) {
			TopicAnnotation tAnno = new TopicAnnotation(jCas);
			tAnno.setText(elected.getId()); //TODO test with getTitle
			tAnno.setScore(elected.getProbability());
			tAnno.addToIndexes();
		}
	}
	
	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		super.collectionProcessComplete();
		extractionModel.close();
	}

}
