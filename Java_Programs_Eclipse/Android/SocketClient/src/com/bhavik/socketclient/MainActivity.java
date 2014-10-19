package com.bhavik.socketclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.R.layout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	EditText etIp, etPort, etMsg;
	Button send_button;
	
	private Socket client;
	private PrintWriter printwriter;
	private String message;
	int port = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etIp = (EditText) findViewById(R.id.ip_addr);
		etPort = (EditText) findViewById(R.id.port_addr);
		etMsg = (EditText) findViewById(R.id.message);
		send_button = (Button) findViewById(R.id.send_message);
		
		
		send_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				message = etMsg.getText().toString();
				etMsg.setText("");
				
				port = Integer.parseInt(etPort.getText().toString());
				
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try{
							client = new Socket(etIp.getText().toString(), port);
							
							printwriter = new PrintWriter(client.getOutputStream());
							printwriter.write(message);
							printwriter.flush();
							printwriter.close();
							client.close();
									
						} catch (IOException e){
							e.printStackTrace();
						}
						
					}
					
				}).start();
				
			}
		});
		
	}

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
