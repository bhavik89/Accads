package com.dynamicgraph;

import java.io.*;
import java.util.ArrayList;

import org.achartengine.GraphicalView;
import org.apache.commons.math3.complex.Complex;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DynamicGraphActivity extends ActionBarActivity {

	private static GraphicalView view;
	private LineGraph line = new LineGraph();
	private static Thread thread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_graph);

		thread = new Thread() {
			public void run() {
				DataInputStream din = null;
				try {
					din = new DataInputStream(new FileInputStream(
							"/sdcard/Download/fft2.dat"));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				for (int i = 0; i < 25; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					while (true) {
					  try {
						int num = 0;
						int frame = 0;
						ArrayList<Double> frames = new ArrayList<>();

						float rl = 0;
						float img = 0;
						
						rl = din.readFloat();
						img = din.readFloat();
						

						rl = (Float.isNaN(rl)) ? 0 : rl;
						img = (Float.isNaN(img)) ? 0 : img;

						Complex c = new Complex(rl, img);

						frames.add(c.abs());
						num++;
						frame++;

						if (frame == 255) {
							frame = 0;

							for (int j = 0; j < 256; j++) {
								Point p = new Point(j, frames.get(j));
								line.addNewPoint(p);
							}
							
							frames.clear();
						}
						
						view.repaint();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						try {
							din.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							break;
						}
						break;
					}

				}

			}
			}
		};
		thread.start();
	}

	protected void onStart() {
		super.onStart();
		view = line.getView(this);
		setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dynamic_graph, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
