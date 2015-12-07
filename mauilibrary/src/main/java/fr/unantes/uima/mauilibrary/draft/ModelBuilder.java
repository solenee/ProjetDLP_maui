package fr.unantes.uima.mauilibrary.draft;

import fr.unantes.uima.mauilibrary.types.FileDescription;

public interface ModelBuilder {
	public void addToModel(FileDescription fDesc, String document);
	public boolean saveModel(String outputFile);
}
