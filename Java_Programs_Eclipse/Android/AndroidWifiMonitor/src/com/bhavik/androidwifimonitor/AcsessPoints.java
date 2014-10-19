package com.bhavik.androidwifimonitor;

import java.util.HashMap;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AcsessPoints extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.access_points);
		final ListView listview = (ListView) findViewById(R.id.apList);
		
		WifiManager myWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		List<ScanResult> apList = myWifiManager.getScanResults();
		
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
		        android.R.layout.simple_list_item_1, apList);
		    listview.setAdapter(adapter);
		
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
	
	
	private class StableArrayAdapter extends ArrayAdapter<ScanResult> {

	    HashMap<ScanResult, Integer> mIdMap = new HashMap<ScanResult, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<ScanResult> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      ScanResult item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }
}
