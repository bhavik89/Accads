package com.dynamicgraph;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;

public class LineGraph {
	
	private GraphicalView view;
	
	private TimeSeries dataset = new TimeSeries("FFT");
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	
	private XYSeriesRenderer renderer = new XYSeriesRenderer();
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	
	public LineGraph(){
		
		mDataset.addSeries(dataset);
		
		renderer.setColor(Color.RED);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setXTitle("Frequency");
		mRenderer.setYTitle("Power");
		
		mRenderer.addSeriesRenderer(renderer);
	}
	
	public GraphicalView getView(Context context){
		
		
		view = ChartFactory.getLineChartView(context, mDataset, mRenderer);
				
		return view;
		
	}
	
	public void addNewPoint(Point p){
		dataset.add(p.getX(), p.getY());
	}

}
