package fr.unantes.uima.mauilibrary.reader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import fr.unantes.uima.mauilibrary.types.FileDescription;
import fr.unantes.uima.mauilibrary.types.ManualTopic;

public class ManualTopicsReader extends JCasAnnotator_ImplBase {

	Logger logger = UIMAFramework.getLogger(ManualTopicsReader.class);

	private void indexManualTopics(String keyFileAbsolutePath, JCas jcas) {
		try {
			File keyFile = new File(keyFileAbsolutePath);
			if (keyFile.exists()) {
				String manualTopics = FileUtils.readFileToString(keyFile);
				for (String mTopic : manualTopics.split("\n")) {
					if (mTopic.length() > 1) {
						ManualTopic topic = new ManualTopic(jcas);
						topic.setAbsolutePath(keyFileAbsolutePath);
						topic.setTopic(mTopic);
						topic.addToIndexes();
					}
				}
			logger.log(Level.INFO, "No key file found for "+keyFileAbsolutePath.replace(".key", ".txt"));
			}
		} catch (IOException e) {
			logger.log(Level.ALL,
					"Error while loading "+ keyFileAbsolutePath + "document : " + e.getMessage());
			throw new RuntimeException();
		}
	}

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		// Initialize ManualTopics
		for (FileDescription fDesc : JCasUtil.select(jcas,
				FileDescription.class)) {
			// should have 0 or 1 element
			indexManualTopics(fDesc.getAbsolutePath().replace(".txt", ".key"),
					jcas);
		}

	}

}
