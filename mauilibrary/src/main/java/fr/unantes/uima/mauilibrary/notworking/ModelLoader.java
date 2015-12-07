package fr.unantes.uima.mauilibrary.annotator;

import weka.filters.Filter;

/**
 * Loads a weka.Filter and enables to serialize it  
 * @author solenee
 *
 */
public interface ModelLoader {
	
	public Filter getModel(); 
	
	public void loadModel();
	
	/** 
	 * Serialize model
	 * @param outputFile
	 */
	public void saveModel(String outputFile);
}
