package com.itachi1706.minecrafttools;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.InputMismatchException;

import com.itachi1706.minecrafttools.AsyncTasks.GetServerStatusFor16;
import com.itachi1706.minecrafttools.AsyncTasks.GetServerStatusFor17;
import com.itachi1706.minecrafttools.PingingUtils.CommonPingTools;
import com.itachi1706.minecrafttools.PingingUtils.PingServer16;
import com.itachi1706.minecrafttools.PingingUtils.PingServer17;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SingleServerChecker extends ActionBarActivity implements android.app.ActionBar.TabListener {

	private ViewPager viewPager;
	private TabPagerAdapter mAdapter;
	private android.app.ActionBar actionBar;
	
	//Tab Titles
	private String[] tabs = {"1.7 and above", "1.6"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_server_checker);
		
		if (savedInstanceState == null) {
			// Init Pager
			viewPager = (ViewPager) findViewById(R.id.pager);
			actionBar = getActionBar();
			mAdapter = new TabPagerAdapter(getSupportFragmentManager());
			
			viewPager.setAdapter(mAdapter);
			actionBar.setHomeButtonEnabled(false);
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			
			
			// Adding Tabs
	        for (String tab_name : tabs) {
	            actionBar.addTab(actionBar.newTab().setText(tab_name)
	                    .setTabListener(this));
	        }
	        
	        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	        	 
	            @Override
	            public void onPageSelected(int position) {
	                // on changing the page
	                // make respected tab selected
	                actionBar.setSelectedNavigationItem(position);
	            }
	         
	            @Override
	            public void onPageScrolled(int arg0, float arg1, int arg2) {
	            }
	         
	            @Override
	            public void onPageScrollStateChanged(int arg0) {
	            }
	        });
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_server_checker, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(this, UserSettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onTabSelected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		// on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		// Unused
		
		
	}

	@Override
	public void onTabReselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		// Unused
		
	}
	
	/**
	 * A FragmentTabPager adapter
	 */
	public static class TabPagerAdapter extends FragmentPagerAdapter{
		
		public TabPagerAdapter(FragmentManager fm){
			super(fm);
		}
		
		@Override
		public Fragment getItem(int index){
			switch (index){
			case 0: return new ServerChecker17Fragment();
			case 1: return new ServerCheck16Fragment();
			}
			return null;
		}
		
		@Override
		public int getCount(){
			return 2;
		}
	}

	/**
	 * A fragment to check status of 1.7 Servers
	 */
	public static class ServerChecker17Fragment extends Fragment implements OnClickListener {

		public ServerChecker17Fragment() {
		}

		private Button checkServerBtn;
		private TextView resultView;
		private TextView addressView;
		private TextView versionView;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_single_server_checker, container, false);
			this.checkServerBtn = (Button) rootView.findViewById(R.id.btnSubmit);
			this.resultView = (TextView) rootView.findViewById(R.id.tvResult);
			this.addressView = (TextView) rootView.findViewById(R.id.serverAddressAdd);
			this.versionView = (TextView) rootView.findViewById(R.id.tvVersion);
			versionView.setText("For MC 1.7 and above");
			this.checkServerBtn.setOnClickListener(this);
			return rootView;
		}
		
		@Override
		public void onClick(View v){
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(FragmentActivity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(addressView.getWindowToken(), 0);
			getActivity().findViewById(R.id.pbUpdateRes).setVisibility(View.VISIBLE);
			PingServer17 server = new PingServer17();
			String addressString = addressView.getText().toString();
			addressString = addressString + ":25565";
			String[] splitedAddress = addressString.split(":");
			String ip = splitedAddress[0];
			String portString = splitedAddress[1];
			int port = 25565;
			
			if (portString.equals("25565")){
				port = 25565;
			} else {
				try {	
					port = Integer.parseInt(portString);
				} catch (InputMismatchException ex){
					//System.out.println("An error occured parsing the port! Assuming Default Port (25565)! (" + ex.toString() + ")");
					Toast.makeText(getActivity().getApplication(), "An error occured parsing the port! Assuming Default Port (25565)! (" + ex.toString() + ")", Toast.LENGTH_SHORT).show();
					port = 25565;
				} catch (Exception exe){
					//System.out.println("An error occured! (" + exe.toString() + ")");
					Toast.makeText(getActivity().getApplication(), "An error occured! (" + exe.toString() + ")", Toast.LENGTH_SHORT).show();
				}
			}
			StringBuilder builder = new StringBuilder();
			//Check IP or DNS
			InetAddress address = null;
			if (CommonPingTools.isIP(ip)){
				//IP Address Stuff
				Toast.makeText(getActivity().getApplication(), "Pinging IP Address " + ip + ":" + port, Toast.LENGTH_LONG).show();
				try {
					address = InetAddress.getByName(ip);
					if (address.isLoopbackAddress()){	//Localhost
						builder.append("IP Address: " + ip + " -> IP Address: localhost (" + address.getHostAddress() + ")" + "<br />");
					} else {
						builder.append("IP Address: " + ip + " -> IP Address: " + address.getHostAddress() + "<br />");
					}
				} catch (UnknownHostException e) {
					Toast.makeText(getActivity().getApplication(), "Hostname is not recognized! (" + e.toString() + ")", Toast.LENGTH_SHORT).show();
					this.resultView.setText("An error occured! Hostname is not recognized! (" + e.toString() + ")");
				}
			} else {
				//DNS Address Stuff
				Toast.makeText(getActivity().getApplication(), "Pinging DNS Address " + ip + ":" + port, Toast.LENGTH_SHORT).show();
				try {
					address = InetAddress.getByName(ip);
					builder.append("DNS Address: " + ip + " -> IP Address: " + address.getHostAddress() + "<br />");
				} catch (UnknownHostException e) {
					Toast.makeText(getActivity().getApplication(), "Hostname is not recognized! (" + e.toString() + ")", Toast.LENGTH_SHORT).show();
					this.resultView.setText("An error occured! Hostname is not recognized! (" + e.toString() + ")");
				}
			}
			
			//Get the actual info from server (1.7)
			InetSocketAddress actualAddr = new InetSocketAddress(address, port);
			server.setAddress(actualAddr);
			new GetServerStatusFor17(getActivity(), builder, ip, address, resultView).execute(server);
		}
	}
	
	/**
	 * A fragment to check status of 1.6 Servers (1.5 and below not supported)
	 */
	public static class ServerCheck16Fragment extends Fragment implements OnClickListener {

		public ServerCheck16Fragment() {
		}
		
		private Button checkServerBtn;
		private TextView resultView;
		private TextView addressView;
		private TextView versionView;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_single_server_checker, container, false);
			this.checkServerBtn = (Button) rootView.findViewById(R.id.btnSubmit);
			this.resultView = (TextView) rootView.findViewById(R.id.tvResult);
			this.addressView = (TextView) rootView.findViewById(R.id.serverAddressAdd);
			this.versionView = (TextView) rootView.findViewById(R.id.tvVersion);
			versionView.setText("For MC 1.6 only");
			this.checkServerBtn.setOnClickListener(this);
			return rootView;
		}
		
		@Override
		public void onClick(View v){
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(FragmentActivity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(addressView.getWindowToken(), 0);
			getActivity().findViewById(R.id.pbUpdateRes).setVisibility(View.VISIBLE);
			PingServer16 server = new PingServer16();
			String addressString = addressView.getText().toString();
			addressString = addressString + ":25565";
			String[] splitedAddress = addressString.split(":");
			String ip = splitedAddress[0];
			String portString = splitedAddress[1];
			int port = 25565;
			if (portString.equals("25565")){
				port = 25565;
			} else {
				try {	
					port = Integer.parseInt(portString);
				} catch (InputMismatchException ex){
					//System.out.println("An error occured parsing the port! Assuming Default Port (25565)! (" + ex.toString() + ")");
					Toast.makeText(getActivity().getApplication(), "An error occured parsing the port! Assuming Default Port (25565)! (" + ex.toString() + ")", Toast.LENGTH_SHORT).show();
					port = 25565;
				} catch (Exception exe){
					//System.out.println("An error occured! (" + exe.toString() + ")");
					Toast.makeText(getActivity().getApplication(), "An error occured! (" + exe.toString() + ")", Toast.LENGTH_SHORT).show();
				}
			}
			StringBuilder builder = new StringBuilder();
			//Check IP or DNS
			InetAddress address = null;
			if (CommonPingTools.isIP(ip)){
				//IP Address Stuff
				Toast.makeText(getActivity().getApplication(), "Pinging IP Address " + ip + ":" + port, Toast.LENGTH_LONG).show();
				try {
					address = InetAddress.getByName(ip);
					if (address.isLoopbackAddress()){	//Localhost
						builder.append("IP Address: " + ip + " -> IP Address: localhost (" + address.getHostAddress() + ")" + "<br />");
					} else {
						builder.append("IP Address: " + ip + " -> IP Address: " + address.getHostAddress() + "<br />");
					}
				} catch (UnknownHostException e) {
					Toast.makeText(getActivity().getApplication(), "Hostname is not recognized! (" + e.toString() + ")", Toast.LENGTH_SHORT).show();
					this.resultView.setText("An error occured! Hostname is not recognized! (" + e.toString() + ")");
				}
			} else {
				//DNS Address Stuff
				Toast.makeText(getActivity().getApplication(), "Pinging DNS Address " + ip + ":" + port, Toast.LENGTH_SHORT).show();
				try {
					address = InetAddress.getByName(ip);
					builder.append("DNS Address: " + ip + " -> IP Address: " + address.getHostAddress() + "<br />");
				} catch (UnknownHostException e) {
					Toast.makeText(getActivity().getApplication(), "Hostname is not recognized! (" + e.toString() + ")", Toast.LENGTH_SHORT).show();
					this.resultView.setText("An error occured! Hostname is not recognized! (" + e.toString() + ")");
				}
			}
			
			//Get the actual info from server (1.6)
			InetSocketAddress actualAddr = new InetSocketAddress(address, port);
			server.setAddress(actualAddr);
			new GetServerStatusFor16(getActivity(), builder, ip, address, resultView).execute(server);
		}
	}

}
