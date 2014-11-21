package com.bhavik.androidplotxy;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.complex.Complex;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

public class MainActivity extends Activity {

	private XYPlot plot;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xy_plot_chart);
		plot = (XYPlot) findViewById(R.id.xyPlot);

		List s1 = getSeries();
		XYSeries series1 = new SimpleXYSeries(s1,
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series 1");

//		List s2 = getSeries(20, 10);
//		XYSeries series2 = new SimpleXYSeries(s2,
//				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series 2");

		LineAndPointFormatter s1Format = new LineAndPointFormatter();
		s1Format.setPointLabelFormatter(new PointLabelFormatter());
		s1Format.configure(getApplicationContext(), R.xml.lpf1);
		plot.addSeries(series1, s1Format);

//		LineAndPointFormatter s2Format = new LineAndPointFormatter();
//		s2Format.setPointLabelFormatter(new PointLabelFormatter());
//		s2Format.configure(getApplicationContext(), R.xml.lpf2);
//		plot.addSeries(series2, s2Format);

		plot.setTicksPerRangeLabel(1);
		plot.getGraphWidget().setDomainLabelOrientation(0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private List getSeries() {
		List<Integer> series = new ArrayList();
		
		
	    int num = 0;
	    int frame = 0;
	    ArrayList<Double> frames = new ArrayList<>();
	    DataInputStream din = null;
	    
	    try{ 	
	    	din = new DataInputStream(new FileInputStream("/sdcard/Download/fft2.dat"));
	    	
	    	for(int i = 0; i<256; i++){
	    		Float rl = din.readFloat();
	    		Float img = din.readFloat();
	    	
	    		rl = (Float.isNaN(rl)) ? 0 : rl; 
	    	
	    		img = (Float.isNaN(img)) ? 0 : img; 
	    	
	    		Complex c = new Complex(rl, img);
	    		
	    		frames.add(c.abs());	    	
	    	} 	
	    	
	    }catch(Exception e){
	    	try {
				din.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    	
	    }    
	
		return frames;
	}
}
