package com.graphs;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;


import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;



public class LineGraph {
	public GraphicalView getIntent(Context context){
		
		int x[] = {1,2,3,4,5,6,7,8,9,10};
		int y[] = {23,45,76,23,8,34,76,23,65,80};
		
		TimeSeries series = new TimeSeries("Line1");
		
		for(int i = 0; i<x.length; i++){
			series.add(x[i], y[i]);
		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		
		XYMultipleSeriesRenderer mRenderer  = new XYMultipleSeriesRenderer();
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		
		 return ChartFactory.getLineChartView(context, dataset, mRenderer);
		
		//return intent;
		
	}

}
