package fr.unantes.uima.mauilibrary.writer;
import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import fr.unantes.uima.mauilibrary.types.FileDescription;
import fr.unantes.uima.mauilibrary.types.TopicAnnotation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Write in a file all the topics found by Maui in the analyzed collection
 * @author leopoldC
 *
 */
public class TopicWriter extends JCasAnnotator_ImplBase {
		Logger logger =  UIMAFramework.getLogger(TopicWriter.class);
		
		private StringBuffer stringbuffer;
		
		public static final String PATH_FILE = "resourceDestFilename";
		@ConfigurationParameter(name = PATH_FILE, mandatory = true)
		private String resourceDestFilename;
	
		@Override
		public void initialize(UimaContext context)
			throws ResourceInitializationException {
			super.initialize(context);
			stringbuffer = new StringBuffer(); 
		}
		@Override
		public void process(JCas aJCas) throws AnalysisEngineProcessException {
			String filename = null;
			for (FileDescription fDesc : JCasUtil.select(aJCas, FileDescription.class)) {
				// should be 1
				filename = fDesc.getFileName();
				stringbuffer.append("==================================\nFile name = "+filename+"\n");
			}
			for (TopicAnnotation topic : JCasUtil.select(aJCas, TopicAnnotation.class)) { 
				stringbuffer.append(topic.getText()+"\t"+topic.getScore()+"\n");  
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
	            bw.write(stringbuffer.toString());
	            bw.close();
	
	           logger.log(Level.INFO, "File create "+resourceDestFilename);
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			super.collectionProcessComplete();
		}

	}
