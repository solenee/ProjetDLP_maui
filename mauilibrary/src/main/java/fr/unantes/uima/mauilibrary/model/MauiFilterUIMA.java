package fr.unantes.uima.mauilibrary.model;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;

import org.apache.uima.UIMAFramework;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import weka.classifiers.Classifier;
import weka.classifiers.meta.Bagging;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

import com.entopix.maui.filters.MauiFilter;
import com.entopix.maui.filters.MauiFilter.MauiFilterException;
import com.entopix.maui.main.MauiModelBuilder;
import com.entopix.maui.util.Candidate;
import com.entopix.maui.util.Counter;

import fr.unantes.uima.mauilibrary.refactoring.MauiFilter_Public;
import fr.unantes.uima.mauilibrary.resource.DFResource;
import fr.unantes.uima.mauilibrary.resource.StemmerResource_MauiImpl;

public class MauiFilterUIMA extends MauiFilter_Public implements SharedResourceObject {

	Logger logger = UIMAFramework.getLogger(MauiFilterUIMA.class);
	
	
	public Instances outputFormatPeek() {
		return super.outputFormatPeek();
	}
	
	
	
	@Override
	public boolean batchFinished() throws MauiFilterException {
		
		// if (training)
		// XXX Compute candidates for the training set if training
		// XXX Build DFResource and KeyphrasesFrequencyResource
		buildClassifier();
		//convertPendingInstances();
		
		// Weka normal end
		flushInput();
		m_NewBatch = true;
		return (numPendingOutput() != 0);
	}

	public void load(DataResource aData) throws ResourceInitializationException {
		// TODO Auto-generated method stub
		try {
			initialize();
			//InputStream input = aData.getInputStream();
			//TODO Load data
			//input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void initialize() throws MauiFilterException {
		logger.log(Level.INFO, "-- Building the model... ");

		FastVector atts = new FastVector(3);
		atts.addElement(new Attribute("filename", (FastVector) null));
		atts.addElement(new Attribute("document", (FastVector) null));
		atts.addElement(new Attribute("keyphrases", (FastVector) null));
		Instances data = new Instances("keyphrase_training_data", atts, 0);

		this.setVocabularyName("none");
		
		this.setInputFormat(data);

		// set features configurations
		this.setBasicFeatures(true);
		this.setKeyphrasenessFeature(false);
		this.setFrequencyFeatures(true);
		this.setPositionsFeatures(false);
		this.setLengthFeature(true);
		this.setThesaurusFeatures(false);
		this.setWikipediaFeatures(false, null);
        
		//this.setClassifier(classifier);
        
	}
}
