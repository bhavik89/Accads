package com.bhavik.androidwifimonitor;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AndroidWifiMonitor extends Activity {

	TextView textConnected, textIp, textSsid, textBssid, textMac, textSpeed,
			textRssi;
	Button getAps;

	private Handler mHandler = new Handler();
	private long mStartRX = 0;
	private long mStartTX = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textConnected = (TextView) findViewById(R.id.Connected);
		textIp = (TextView) findViewById(R.id.Ip);
		textSsid = (TextView) findViewById(R.id.Ssid);
		textBssid = (TextView) findViewById(R.id.Bssid);
		textMac = (TextView) findViewById(R.id.Mac);
		textSpeed = (TextView) findViewById(R.id.Speed);
		textRssi = (TextView) findViewById(R.id.Rssi);
		
		getAps = (Button) findViewById(R.id.getAps);		

		DisplayWifiState();

		mStartRX = TrafficStats.getTotalRxBytes();

		mStartTX = TrafficStats.getTotalTxBytes();

		if (mStartRX == TrafficStats.UNSUPPORTED || mStartTX == TrafficStats.UNSUPPORTED) {

			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Uh Oh!");
			alert.setMessage("Your device does not support traffic stat monitoring.");
			alert.show();

		} else {

			mHandler.postDelayed(mRunnable, 1000);

		}

		this.registerReceiver(this.myWifiReceiver, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));

		this.registerReceiver(this.myRssiChangeReceiver, new IntentFilter(
				WifiManager.RSSI_CHANGED_ACTION));

	}

	private BroadcastReceiver myRssiChangeReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			int newRssi = arg1.getIntExtra(WifiManager.EXTRA_NEW_RSSI, 0);
			textRssi.setText(String.valueOf(newRssi));
		}
	};

	private BroadcastReceiver myWifiReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			@SuppressWarnings("deprecation")
			NetworkInfo networkInfo = (NetworkInfo) arg1
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				DisplayWifiState();
			}
		}
	};

	private void DisplayWifiState() {

		ConnectivityManager myConnManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo myNetworkInfo = myConnManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		WifiManager myWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);		
		WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();

		textMac.setText(myWifiInfo.getMacAddress());

		if (myNetworkInfo.isConnected()) {
			int myIp = myWifiInfo.getIpAddress();

			textConnected.setText("--- CONNECTED ---");

			int intMyIp3 = myIp / 0x1000000;
			int intMyIp3mod = myIp % 0x1000000;

			int intMyIp2 = intMyIp3mod / 0x10000;
			int intMyIp2mod = intMyIp3mod % 0x10000;

			int intMyIp1 = intMyIp2mod / 0x100;
			int intMyIp0 = intMyIp2mod % 0x100;

			textIp.setText(String.valueOf(intMyIp0) + "."
					+ String.valueOf(intMyIp1) + "." + String.valueOf(intMyIp2)
					+ "." + String.valueOf(intMyIp3));

			textSsid.setText(myWifiInfo.getSSID());
			textBssid.setText(myWifiInfo.getBSSID());

			textSpeed.setText(String.valueOf(myWifiInfo.getLinkSpeed()) + " "
					+ WifiInfo.LINK_SPEED_UNITS);
			textRssi.setText(String.valueOf(myWifiInfo.getRssi()));
		} else {
			textConnected.setText("--- DIS-CONNECTED! ---");
			textIp.setText("---");
			textSsid.setText("---");
			textBssid.setText("---");
			textSpeed.setText("---");
			textRssi.setText("---");
		}

	}
	
	

	private final Runnable mRunnable = new Runnable() {

		public void run() {

			TextView RX = (TextView) findViewById(R.id.rxBytes);

			TextView TX = (TextView) findViewById(R.id.txBytes);

			long rxBytes = TrafficStats.getTotalRxBytes() - mStartRX;

			RX.setText(Long.toString(rxBytes));

			long txBytes = TrafficStats.getTotalTxBytes() - mStartTX;

			TX.setText(Long.toString(txBytes));

			mHandler.postDelayed(mRunnable, 1000);

		}

	};
	
	public void getAccessPointsList(View view){
		Intent getAccessPoints = new Intent("com.bhavik.androidwifimonitor.ACCESSPOINTS");
		startActivity(getAccessPoints);
	}
}