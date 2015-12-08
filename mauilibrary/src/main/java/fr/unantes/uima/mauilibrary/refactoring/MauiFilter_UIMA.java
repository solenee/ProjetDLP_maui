package fr.unantes.uima.mauilibrary.refactoring;

/*
 *    MauiTopicExtractor.java
 *    Copyright (C) 2001-2014 Eibe Frank, Alyona Medelyan
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.entopix.maui.filters.MauiFilter;
import com.entopix.maui.filters.MauiPhraseFilter;
import com.entopix.maui.filters.NumbersFilter;
import com.entopix.maui.stemmers.PorterStemmer;
import com.entopix.maui.stemmers.Stemmer;
import com.entopix.maui.stopwords.Stopwords;
import com.entopix.maui.stopwords.StopwordsEnglish;
import com.entopix.maui.util.Candidate;
import com.entopix.maui.util.Counter;
import com.entopix.maui.util.Topic;
import com.entopix.maui.vocab.Vocabulary;
import com.entopix.maui.wikifeatures.WikiFeatures;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weka.core.Attribute;
import weka.core.Capabilities;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.Capabilities.Capability;
import weka.filters.Filter;
import weka.classifiers.Classifier;
import weka.classifiers.meta.Bagging;

/**
 * Adapt MauiFilter into a UIMA resource
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
