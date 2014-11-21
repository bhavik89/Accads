package com.bhavik.filereader;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import java.io.*;
import java.util.ArrayList;

import org.apache.commons.math3.complex.Complex;

import android.widget.*;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity {
	
	// GUI controls
	EditText txtData;
	Button btnWriteSDFile;
	Button btnReadSDFile;
	Button btnClearScreen;
	Button btnClose;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// bind GUI elements with local controls
		txtData = (EditText) findViewById(R.id.txtData);
		txtData.setHint("Enter some lines of data here...");

		btnWriteSDFile = (Button) findViewById(R.id.btnWriteSDFile);
		
		btnWriteSDFile.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// write on SD card file data in the text box
				try {
					File myFile = new File("/sdcard/Download/mysdfile.txt");
					myFile.createNewFile();
					FileOutputStream fOut = new FileOutputStream(myFile);
					OutputStreamWriter myOutWriter = 
											new OutputStreamWriter(fOut);
					myOutWriter.append(txtData.getText());
					myOutWriter.close();
					fOut.close();
					Toast.makeText(getBaseContext(),
							"Done writing SD 'mysdfile.txt'",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}// onClick
			}); // btnWriteSDFile

				btnReadSDFile = (Button) findViewById(R.id.btnReadSDFile);
				btnReadSDFile.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// write on SD card file data in the text box
				try {
					File myFile = new File("/sdcard/Download/fft2.dat");
					FileInputStream fIn = new FileInputStream(myFile);
					DataInputStream din = new DataInputStream(new FileInputStream("/sdcard/Download/fft2.dat"));
					int num = 0;
					int frame = 0;
					ArrayList<Double> frames = new ArrayList<>();
					while(true){					    
						try{
					    	Float rl = din.readFloat();
					    	Float img = din.readFloat();
					    	
					    	rl = (Float.isNaN(rl)) ? 0 : rl; 
					    	img = (Float.isNaN(img)) ? 0 : img; 
					    	
					    	Complex c = new Complex(rl, img);
					    		
					    	frames.add( c.abs());	    	
					    	num++;
					    	frame++;
					    	
					    	if(frame == 255){
					    		frame = 0;
					    		//System.out.println(frames);
					    		
					    		txtData.append("\n" + Double.toString(frames.get(0)));
					    		frames.clear();
					    	}				    	
					    	
					    }catch(EOFException e){
					    	din.close();
					    	break;
					    }
					}
					
					//BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
					//String aDataRow = "";
					//String aBuffer = "";
//					while ((aDataRow = myReader.readLine()) != null) {
//						aBuffer += aDataRow + "\n";
//					}
//					txtData.setText(aBuffer);
//					myReader.close();
					Toast.makeText(getBaseContext(),
							"Done reading SD 'mysdfile.txt'",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
				}// onClick
				}); // btnReadSDFile

				btnClearScreen = (Button) findViewById(R.id.btnClearScreen);
				btnClearScreen.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// clear text box
						txtData.setText("");
					}
				}); // btnClearScreen

				btnClose = (Button) findViewById(R.id.btnClose);
				btnClose.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// clear text box
						finish();
					}
				}); // btnClose

			}// onCreate
		


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
