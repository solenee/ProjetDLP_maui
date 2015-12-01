package fr.unantes.uima.mauilibrary.utils;

import java.util.Comparator;

import fr.unantes.uima.mauilibrary.types.CandidateAnnotation;

public class CandidatesComparator implements Comparator<CandidateAnnotation> {

	/**
	 * Compare in reverse order of the score
	 */
	public int compare(CandidateAnnotation arg0, CandidateAnnotation arg1) {
		if (arg0.getScore() < arg1.getScore()) return 1;
		if (arg0.getScore() > arg1.getScore()) return -1;
		return 0;
	}

}
