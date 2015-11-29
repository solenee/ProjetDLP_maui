package fr.unantes.uima.mauilibrary.resource;

import org.apache.uima.resource.DataResource;

import weka.filters.Filter;

import com.entopix.maui.filters.MauiFilter;

public class MauiFilterResource extends FilterResource_ImplBase {

	private MauiFilter mauiFilter;
	
	public Filter getMainFilter() {
		return mauiFilter;
	}

	@Override
	public void initialize(DataResource arg0) {
		// TODO Auto-generated method stub
		mauiFilter = new MauiFilter();
	}

}
