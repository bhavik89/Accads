package com.bhavik.androidserver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ServerStart extends ActionBarActivity {
    
	TextView info, infoip,msg;
	String message = "";
	ServerSocket serverSocket;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_start);
		
		info  = (TextView) findViewById(R.id.info);
		infoip = (TextView) findViewById(R.id.infoip);
		msg = (TextView) findViewById(R.id.msg); 
		
		infoip.setText(getIpAddress());
		
		Thread socketServerThread = new Thread(new SocketServerThread());
		socketServerThread.start();
	}
	

	private String getIpAddress() {
        
		String ip = "";
		
		try{
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
			
			while(enumNetworkInterfaces.hasMoreElements()){
				
				NetworkInterface netInterface = enumNetworkInterfaces.nextElement();
				
				Enumeration<InetAddress> enumInetAddress = netInterface.getInetAddresses();
				
				while(enumInetAddress.hasMoreElements()){
					
					InetAddress inetAddress = enumInetAddress.nextElement();
					
					if(inetAddress.isSiteLocalAddress())
						ip += "SiteLocalAddress: " + inetAddress.getHostAddress() + "\n";
					
				}
				
			}
			
		}catch(SocketException e){
			e.printStackTrace();
			ip += "Something Wrong! " + e.toString() + "\n";
		}

		return ip;
	}
	
	private class SocketServerThread extends Thread {

		  static final int SocketServerPORT = 8080;
		  int count = 0;

		  @Override
		  public void run() {
		   try {
		    serverSocket = new ServerSocket(SocketServerPORT);
		    ServerStart.this.runOnUiThread(new Runnable() {

		     @Override
		     public void run() {
		      info.setText("I'm waiting here: " + serverSocket.getLocalPort());
		     }
		    });

		    while (true) {
		     Socket socket = serverSocket.accept();
		     count++;
		     message += "#" + count + " from " + socket.getInetAddress()
		       + ":" + socket.getPort() + "\n";

		     ServerStart.this.runOnUiThread(new Runnable() {

		      @Override
		      public void run() {
		       msg.setText(message);
		      }
		     });

		     SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
		       socket, count);
		     socketServerReplyThread.run();

		    }
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   }
		  }

	}
	
	private class SocketServerReplyThread extends Thread {

		  private Socket hostThreadSocket;
		  int cnt;

		  SocketServerReplyThread(Socket socket, int c) {
		   hostThreadSocket = socket;
		   cnt = c;
		  }

		  @Override
		  public void run() {
		   OutputStream outputStream;
		   String msgReply = "Hello from Android, you are #" + cnt;

		   try {
		    outputStream = hostThreadSocket.getOutputStream();
		             PrintStream printStream = new PrintStream(outputStream);
		             printStream.print(msgReply);
		             printStream.close();

		    message += "replayed: " + msgReply + "\n";

		    ServerStart.this.runOnUiThread(new Runnable() {

		     @Override
		     public void run() {
		      msg.setText(message);
		     }
		    });

		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    message += "Something wrong! " + e.toString() + "\n";
		   }

		   ServerStart.this.runOnUiThread(new Runnable() {

		    @Override
		    public void run() {
		     msg.setText(message);
		    }
		   });
		  }

		 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.server_start, menu);
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
