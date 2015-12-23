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

/**
 * Load manual topics for a given file. They should be stored one per line in a .key file 
 * named following the field getAbsolutePath() of the corresponding FileDescription annotation
 * @author solenee
 *
 */
public class ManualTopicsReaderV0 extends JCasAnnotator_ImplBase {

	Logger logger = UIMAFramework.getLogger(ManualTopicsReaderV0.class);

	private String getManualTopics(String keyFileAbsolutePath) {
		String manualTopics = "";
		try {
			File keyFile = new File(keyFileAbsolutePath);
			if (keyFile.exists()) {
				manualTopics = FileUtils.readFileToString(keyFile);
			} else {
				logger.log(Level.INFO, "No key file found for "+keyFileAbsolutePath.replace(".key", ""));
			}
		} catch (IOException e) {
			manualTopics = "";
			logger.log(Level.ALL,
					"Error while loading "+ keyFileAbsolutePath + "document : " + e.getMessage());
			//throw new RuntimeException();
		}
		return manualTopics;
	}

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		// Initialize ManualTopics
		for (FileDescription fDesc : JCasUtil.select(jcas,
				FileDescription.class)) {
			// should have 0 or 1 element
			// XXX 
			//fDesc.setManualTopics(getManualTopics(fDesc.getAbsolutePath().replace(".txt", ".key")));
			fDesc.setAbsolutePath(getManualTopics(fDesc.getAbsolutePath().replace(".txt", "")+".key"));
		}

	}

}
