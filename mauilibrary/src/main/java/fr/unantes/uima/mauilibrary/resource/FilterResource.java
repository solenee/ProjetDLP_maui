package fr.unantes.uima.mauilibrary.resource;

import weka.filters.Filter;

public interface FilterResource {
	public Filter getMainFilter();
	
	/**
	 * 
	 * @param filename
	 * @param documentText
	 * @param topicsString "" if there is no manual topic; list of topics string separated by "\n" otherwise
	 */
	public void input(String filename, String documentText, String topicsString) throws Exception;
	
}
