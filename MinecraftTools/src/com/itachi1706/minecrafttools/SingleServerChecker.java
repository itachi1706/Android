package com.itachi1706.minecrafttools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itachi1706.minecrafttools.PingServer17.StatusResponse;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SingleServerChecker extends ActionBarActivity implements android.app.ActionBar.TabListener {

	private ViewPager viewPager;
	private TabPagerAdapter mAdapter;
	private android.app.ActionBar actionBar;
	
	//Tab Titles
	private String[] tabs = {"1.7 and above servers", "1.6 Servers (Coming Soon)"};
	
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

		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
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
			return true;
		}
		return super.onOptionsItemSelected(item);
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
			case 0: return new PlaceholderFragment();
			case 1: return new ServerCheck16();
			}
			return null;
		}
		
		@Override
		public int getCount(){
			return 2;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnClickListener {

		public PlaceholderFragment() {
		}

		private Button checkServerBtn;
		private TextView resultView;
		private TextView addressView;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_single_server_checker, container, false);
			this.checkServerBtn = (Button) rootView.findViewById(R.id.btnSubmit);
			this.resultView = (TextView) rootView.findViewById(R.id.tvResult);
			this.addressView = (TextView) rootView.findViewById(R.id.serverAddress);
			this.checkServerBtn.setOnClickListener(this);
			return rootView;
		}
		
		@Override
		public void onClick(View v){
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
			if (isIP(ip)){
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
			try {
				StatusResponse response = server.fetchData();
				builder.append("<br />");
				builder.append("====================================<br />");
				if (isIP(ip)){
					builder.append("Server Info for " + address.getHostAddress() + "<br />");
				} else {
					builder.append("Server Info for " + address.getHostName() + "<br />");
				}
				builder.append("====================================<br />");
				builder.append("<font>MOTD: <b>" + parseFormatting(response.getDescription())  + "</b></font><br />");
				builder.append("Player: " + response.getPlayers().getOnline() + "/" + response.getPlayers().getMax()  + "<br />");
				builder.append("MC Version: " + response.getVersion().getName() + " (Protocol Version " + response.getVersion().getProtocol() + ")"  + "<br />");
				builder.append("Ping: " + response.getTime()  + "<br />");
				//System.out.println("Favicon: " + response.getFavicon());
				this.resultView.setText(Html.fromHtml(builder.toString()));
			} catch (IOException e) {
				Toast.makeText(getActivity().getApplication(), "An error occured! (" + e.toString() + ")", Toast.LENGTH_SHORT).show();
			}
		}
		
		public static boolean isIP(String ip){  
			boolean isValid = false;  
			  
			/*IP: A numeric value will have following format: 
			         ^[-+]?: Starts with an optional "+" or "-" sign. 
			     [0-9]*: May have one or more digits. 
			    \\.? : May contain an optional "." (decimal point) character. 
			    [0-9]+$ : ends with numeric digit. 
			*/  
			  
			//Initialize reg ex for numeric data.   
			String expression = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b";  
			CharSequence inputStr = ip;  
			Pattern pattern = Pattern.compile(expression);  
			Matcher matcher = pattern.matcher(inputStr);  
			if(matcher.matches()){  
			isValid = true;  
			}  
			return isValid;  
		}
		
		private static String parseFormatting(String in){
			String out = in;
			//out = out.replace("§", "</font><font color=#55FFFF>");
			out = out.replace("§0", "</font><font color=#000000>");
			out = out.replace("§1", "</font><font color=#0000AA>");
			out = out.replace("§2", "</font><font color=#00AA00>");
			out = out.replace("§3", "</font><font color=#00AAAA>");
			out = out.replace("§4", "</font><font color=#AA0000>");
			out = out.replace("§5", "</font><font color=#AA00AA>");
			out = out.replace("§6", "</font><font color=#FFAA00>");
			out = out.replace("§7", "</font><font color=#AAAAAA>");
			out = out.replace("§8", "</font><font color=#555555>");
			out = out.replace("§9", "</font><font color=#5555FF>");
			out = out.replace("§a", "</font><font color=#55FF55>");
			out = out.replace("§b", "</font><font color=#55FFFF>");
			out = out.replace("§c", "</font><font color=#FF5555>");
			out = out.replace("§d", "</font><font color=#FF55FF>");
			out = out.replace("§e", "</font><font color=#FFFF55>");
			out = out.replace("§f", "</font><font color=#FFFFFF>");
			//End of Colors
			// Formatting
			out = out.replace("§k", "");
			out = out.replace("§l", "");
			out = out.replace("§m", "");
			out = out.replace("§n", "");
			out = out.replace("§o", "");
			out = out.replace("§r", "");
			return out;
		}
	}
	
	@Override
	public void onTabSelected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		// on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onTabReselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	//Temp Soon Page
	public static class ServerCheck16 extends Fragment implements OnClickListener {

		public ServerCheck16() {
		}

		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_single_server_checker_1_6, container, false);
			return rootView;
		}
		
		@Override
		public void onClick(View v){

		}
	}

}
