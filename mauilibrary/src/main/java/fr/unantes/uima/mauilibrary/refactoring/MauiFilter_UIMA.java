package fr.unantes.uima.mauilibrary.refactoring;

import java.util.List;

import org.apache.uima.resource.SharedResourceObject;

import com.entopix.maui.stemmers.Stemmer;
import com.entopix.maui.stopwords.Stopwords;
import com.entopix.maui.util.Topic;

/**
 * Adapts MauiFilter into a UIMA resource via encapsulation or simulation
 */
public interface MauiFilter_UIMA extends SharedResourceObject {
	
	// Step 1 : Build model (initialize classifier)
	public boolean initializeModelForTraining(String documentLanguage, Stemmer stemmer, Stopwords stopwords);
	
	public boolean addDocumentToModel(String filename, String documentText, String manualTopicsText);
	
	public boolean finalizeModelBuilding();
	
	public boolean save(String modelName);
	
	// Step 2 : Test
	public boolean initializeModelForTesting(String documentLanguage, Stemmer stemmer, Stopwords stopwords);
	
	public boolean loadModel(String modelName);
	
	public List<Topic> extractTopics(String filename, String documentText,
			String manualTopicsText, int topicsPerDocument);
	
	public boolean close();
}
