package com.bhavik.dynamicxy;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import com.androidplot.Plot;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.*;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.apache.commons.math3.complex.Complex;

public class DynamicXYPlotActivity extends Activity {

	// redraws a plot whenever an update is received:
	private class MyPlotUpdater implements Observer {
		Plot plot;

		public MyPlotUpdater(Plot plot) {
			this.plot = plot;
		}

		@Override
		public void update(Observable o, Object arg) {
			plot.redraw();
		}
	}

	private XYPlot dynamicPlot;
	private MyPlotUpdater plotUpdater;
	SampleDynamicXYDatasource data;
	private Thread myThread;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// android boilerplate stuff
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// get handles to our View defined in layout.xml:
		dynamicPlot = (XYPlot) findViewById(R.id.dynamicXYPlot);

		plotUpdater = new MyPlotUpdater(dynamicPlot);

		// only display whole numbers in domain labels
		dynamicPlot.getGraphWidget().setDomainValueFormat(
				new DecimalFormat("0"));

		// getInstance and position datasets:
		data = new SampleDynamicXYDatasource();
		SampleDynamicSeries sine1Series = new SampleDynamicSeries(data, 0, "FFT");

		// SampleDynamicSeries sine2Series = new SampleDynamicSeries(data, 1,
		// "Sine 2");

		BarFormatter formatter1 = new BarFormatter(Color.rgb(
				0, 0, 0), Color.BLUE);
		//formatter1.getLinePaint().setStrokeJoin(Paint.Join.ROUND);
		//formatter1.getLinePaint().setStrokeWidth(10);
		dynamicPlot.addSeries(sine1Series, formatter1);

		// LineAndPointFormatter formatter2 =
		// new LineAndPointFormatter(Color.rgb(0, 0, 200), null, null, null);
		// formatter2.getLinePaint().setStrokeWidth(10);
		// formatter2.getLinePaint().setStrokeJoin(Paint.Join.ROUND);

		// formatter2.getFillPaint().setAlpha(220);
		// dynamicPlot.addSeries(sine2Series, formatter2);

		// hook up the plotUpdater to the data model:
		data.addObserver(plotUpdater);

		// thin out domain tick labels so they dont overlap each other:
		dynamicPlot.setDomainStepMode(XYStepMode.INCREMENT_BY_VAL);
		dynamicPlot.setDomainStepValue(25);

		dynamicPlot.setRangeStepMode(XYStepMode.INCREMENT_BY_VAL);
		dynamicPlot.setRangeStepValue(100);

		dynamicPlot.setRangeValueFormat(new DecimalFormat("###.#"));

		// uncomment this line to freeze the range boundaries:
		dynamicPlot.setRangeBoundaries(0, 1000, BoundaryMode.FIXED);

		// create a dash effect for domain and range grid lines:
		// DashPathEffect dashFx = new DashPathEffect(
		// new float[] {PixelUtils.dpToPix(3), PixelUtils.dpToPix(3)}, 0);
		// dynamicPlot.getGraphWidget().getDomainGridLinePaint().setPathEffect(dashFx);
		// dynamicPlot.getGraphWidget().getRangeGridLinePaint().setPathEffect(dashFx);

	}

	@Override
	public void onResume() {
		// kick off the data generating thread:
		myThread = new Thread(data);
		myThread.start();
		super.onResume();
	}

	@Override
	public void onPause() {
		data.stopThread();
		super.onPause();
	}

	class SampleDynamicXYDatasource implements Runnable {

		private static final int SAMPLE_SIZE = 255;
		private MyObservable notifier;
		private boolean keepRunning = false;
		private int yVal = 0;

		private ArrayList<Double> frames = new ArrayList<>();
		private DataInputStream din = null;

		public SampleDynamicXYDatasource() {
			try {
				din = new DataInputStream(new FileInputStream("/sdcard/Download/fft2.dat"));
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			while(true){
			try {
				

				Float rl = din.readFloat();
				Float img = din.readFloat();

				rl = (Float.isNaN(rl)) ? 0 : rl;

				img = (Float.isNaN(img)) ? 0 : img;

				Complex c = new Complex(rl, img);
				Random rand = new Random();
				// return 
				frames.add(c.abs() % 1000);

			} catch (Exception e) {
				try {
					din.close();
					break;
				} catch (IOException e1) {
					e1.printStackTrace();
					break;
				}

			}
			}
		}

		// encapsulates management of the observers watching this datasource for
		// update events:
		class MyObservable extends Observable {
			@Override
			public void notifyObservers() {
				setChanged();
				super.notifyObservers();
			}
		}

		{
			notifier = new MyObservable();
		}

		public void stopThread() {
			keepRunning = false;
		}

		// @Override
		public void run() {
			try {
				keepRunning = true;
				boolean isRising = true;
				while (keepRunning) {

					Thread.sleep(200); // decrease or remove to speed up the
										// refresh rate.

					notifier.notifyObservers();

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public int getItemCount(int series) {
			return SAMPLE_SIZE;
		}

		public Number getX(int series, int index) {
			if (index >= SAMPLE_SIZE) {
				throw new IllegalArgumentException();
			}
			return index;
		}

		public Double getY(int series, int index) {

			Random rand = new Random();
			// return rand.nextInt((10 - 0) + 1);
			
			if (yVal > frames.size()-2){
				stopThread();
				return frames.get(yVal);
			}else if(yVal < frames.size()-2)
				return frames.get(yVal++);
			else
				return 0.0;

		}

		public void addObserver(Observer observer) {
			notifier.addObserver(observer);
		}

		public void removeObserver(Observer observer) {
			notifier.deleteObserver(observer);
		}

	}

	class SampleDynamicSeries implements XYSeries {
		private SampleDynamicXYDatasource datasource;
		private int seriesIndex;
		private String title;

		public SampleDynamicSeries(SampleDynamicXYDatasource datasource,
				int seriesIndex, String title) {
			this.datasource = datasource;
			this.seriesIndex = seriesIndex;
			this.title = title;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public int size() {
			return datasource.getItemCount(seriesIndex);
		}

		@Override
		public Number getX(int index) {
			return datasource.getX(seriesIndex, index);
		}

		@Override
		public Number getY(int index) {
			// Random rand = new Random();
			// return rand.nextInt((100 - 0) + 1);
			return datasource.getY(seriesIndex, index);
		}
	}
}