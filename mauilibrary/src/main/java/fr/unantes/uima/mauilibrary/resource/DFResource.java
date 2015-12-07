package fr.unantes.uima.mauilibrary.resource;

/**
 * Stores document frequencies
 * @author 
 *
 */
public interface DFResource {

	public int getDocumentFrequency(String candidate);
	public void inc(String candidate);
}
