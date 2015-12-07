package fr.unantes.uima.mauilibrary.resource;

import java.util.HashMap;
import java.util.List;

import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import com.entopix.maui.util.Candidate;

import fr.unantes.uima.mauilibrary.types.CandidateAnnotation;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public abstract class FilterResource_ImplBase implements FilterResource, SharedResourceObject {
	
	protected Instances data;
	protected HashMap<Instance, List<CandidateAnnotation>> allCandidates = new HashMap<Instance, List<CandidateAnnotation>>();
	
	public void input(String filename, String documentText, String topicsString) throws Exception {
		double[] newInst = new double[3];

		newInst[0] = data.attribute(0).addStringValue(filename);

		// Adding the text of the document to the instance
		if (documentText.length() > 0) {
			newInst[1] = data.attribute(1).addStringValue(documentText);
		} else {
			newInst[1] = Instance.missingValue();
		}

		if (topicsString.length() > 0) {
			newInst[2] = data.attribute(2).addStringValue(topicsString);
		} else {
			newInst[2] = Instance.missingValue();
		}

		data.add(new Instance(1.0, newInst));

		getMainFilter().input(data.instance(0));
		
	}
	
	public void input(String filename, String documentText, String topicsString, List<CandidateAnnotation> candidates) throws Exception {
		double[] newInst = new double[3];

		newInst[0] = data.attribute(0).addStringValue(filename);

		// Adding the text of the document to the instance
		if (documentText.length() > 0) {
			newInst[1] = data.attribute(1).addStringValue(documentText);
		} else {
			newInst[1] = Instance.missingValue();
		}

		if (topicsString.length() > 0) {
			newInst[2] = data.attribute(2).addStringValue(topicsString);
		} else {
			newInst[2] = Instance.missingValue();
		}

		Instance inst = new Instance(1.0, newInst);
		data.add(inst);

		getMainFilter().input(data.instance(0));
		allCandidates.put(inst, candidates);
		
	}
	public abstract void initialize(DataResource arg0);
	
	public void load(DataResource arg0) throws ResourceInitializationException {
		// TODO Auto-generated method stub
		// Weka data structures
		FastVector atts = new FastVector(3);
		atts.addElement(new Attribute("filename", (FastVector) null));
		atts.addElement(new Attribute("doc", (FastVector) null));
		atts.addElement(new Attribute("keyphrases", (FastVector) null));
		this.data = new Instances("keyphrase_training_data", atts, 0);
		
		initialize(arg0);
	}

}
