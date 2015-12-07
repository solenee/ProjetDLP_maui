package fr.unantes.uima.mauilibrary.notworking;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import com.entopix.maui.filters.MauiFilter;

import weka.filters.Filter;

public class ModelLoader_MauiImpl extends JCasAnnotator_ImplBase implements ModelLoader {

	Logger logger = UIMAFramework.getLogger(ModelLoader_MauiImpl.class);
	private MauiFilter mauiFilter;
	
	public static final String SAVE_MODEL = "SAVE_MODEL";
	@ConfigurationParameter(name = SAVE_MODEL,
			mandatory = false, defaultValue= "true")
	private Boolean shouldSaveModel;
	
	public static final String MODEL_NAME = "MODEL_NAME";
	@ConfigurationParameter(name = MODEL_NAME,
			mandatory = true)
	private String modelName;
	
	public Filter getModel() {
		return mauiFilter;
	}

	public void loadModel() {
		// TODO Auto-generated method stub
		logger.log(Level.WARNING, "Sorry ! Not yet implemented");
		//logger.log(Level.INFO, modelName+" model loaded.");
	}

	public void saveModel(String outputFile) {
		if (mauiFilter != null) {
			try {
				BufferedOutputStream bufferedOut = new BufferedOutputStream(
						new FileOutputStream(modelName));
				ObjectOutputStream out = new ObjectOutputStream(bufferedOut);
				out.writeObject(mauiFilter);
				out.flush();
				out.close();
			} catch (IOException e) {
				logger.log(Level.ALL, "An error occured while saving "+modelName+" model.");
				e.printStackTrace();
			}
			logger.log(Level.INFO, modelName+" model saved.");
		}
		
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		loadModel();
		if (shouldSaveModel) {
			saveModel(modelName);
		}
	} 

}
