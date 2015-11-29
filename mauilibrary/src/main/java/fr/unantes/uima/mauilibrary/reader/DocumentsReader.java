package fr.unantes.uima.mauilibrary.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import fr.unantes.uima.mauilibrary.types.FileDescription;
import fr.unantes.uima.mauilibrary.types.ManualTopic;

/**
 * TODO : Index nbDocuments somehow : in MauiFilter resource
 * Note : The model is just a serialized version of MauiFilter object (version Maui)
 * Collection reader for loading all txt files of a given directory
 * It also add the following pieces of information : FileDescription, ManualTopics 
 * Maui : Refactoring of DataLoader
 * @author solenee
 *
 */
public class DocumentsReader extends JCasCollectionReader_ImplBase {

	Logger logger = UIMAFramework.getLogger(DocumentsReader.class);

	public static final String PARAM_DIRECTORY_NAME = "DirectoryName";
	@ConfigurationParameter(name = PARAM_DIRECTORY_NAME,
			description = "The name of the directory of text files to be read",
			mandatory = true)
	private File dir;

	/** Documents list */
	private List<File> documents;
	
	private int i = 0;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		documents = new ArrayList<File>(FileUtils.listFiles(dir,
				new String[] { "txt" }, false));
	}


	public boolean hasNext() throws IOException, CollectionException {
		return i < documents.size();
	}

	public Progress[] getProgress() {
		return new Progress[] { new ProgressImpl(i, documents.size(),
				Progress.ENTITIES) };
	}

	@Override
	public void getNext(JCas jcas) throws IOException, CollectionException {
		File f = documents.get(i);
		String s = FileUtils.readFileToString(f);
		jcas.setDocumentText(s);
		// Initialize FileDescription
		FileDescription fDesc = new FileDescription(jcas);
		fDesc.setAbsolutePath(f.getAbsolutePath());
		fDesc.setFileName(f.getName());
		fDesc.setId(i);
		fDesc.addToIndexes();
		i++;
	}

}
