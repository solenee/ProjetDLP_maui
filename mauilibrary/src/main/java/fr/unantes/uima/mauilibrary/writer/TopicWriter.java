package fr.unantes.uima.mauilibrary.writer;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class TopicWriter extends JCasAnnotator_ImplBase{
		private List<String> stringbuffer;
		public static final String PATH_FILE = "resourceDestFilename";
		@ConfigurationParameter(name = PATH_FILE, mandatory = true, defaultValue="/tmp/wordcounter.csv")
		private String resourceDestFilename;
	
		@Override
		public void process(JCas aJCas) throws AnalysisEngineProcessException {
			AnnotationIndex<Annotation>	anAnnotationIndex = aJCas.getAnnotationIndex();
            for (Annotation token : anAnnotationIndex) {
            	stringbuffer.add(token.getClass().getSimpleName());  
	        	}     	
		}
		
		@Override
		public void collectionProcessComplete() throws AnalysisEngineProcessException {
			try {
	        	/* file opening*/
	            File file = new File(resourceDestFilename);
	            FileWriter fw = new FileWriter(file.getAbsoluteFile());
	            BufferedWriter bw = new BufferedWriter(fw);
	            /* move from annotation */
	            for (String S: stringbuffer) {
		        	bw.write(S);  
		        	}     
	            bw.close();
	
	            System.out.println("File create "+resourceDestFilename);
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			super.collectionProcessComplete();
		}



	}
