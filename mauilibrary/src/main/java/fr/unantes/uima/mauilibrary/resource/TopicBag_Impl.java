package fr.unantes.uima.mauilibrary.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import com.entopix.maui.util.Topic;

public class TopicBag_Impl implements TopicsBag, SharedResourceObject {

	private List<Topic> allTopics;
	public void load(DataResource arg0) throws ResourceInitializationException {
		// TODO Auto-generated method stub
		allTopics = new ArrayList<Topic>();
	}

	public void addTopic(Topic topic) {
		// TODO Auto-generated method stub
		allTopics.add(topic);
	}
	

}
