package com.itachi1706.minecrafttools.PingingUtils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.InputMismatchException;

import com.itachi1706.minecrafttools.Database.ServerList;
import com.itachi1706.minecrafttools.Database.ServerListDB;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddANewServerIntoList implements OnClickListener{
	
	private TextView addAddress;
	private ToggleButton checkServerMode;
	private Activity act;
	
	public AddANewServerIntoList(TextView address, ToggleButton check, Activity activity){
		this.addAddress=address;
		this.checkServerMode=check;
		this.act=activity;
	}

	@Override
	public void onClick(View v) {
		//Parse the ip address
		String addressString = addAddress.getText().toString();
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
				Toast.makeText(act.getApplication(), "An error occured parsing the port! Assuming Default Port (25565)! (" + ex.toString() + ")", Toast.LENGTH_SHORT).show();
				port = 25565;
			} catch (Exception exe){
				//System.out.println("An error occured! (" + exe.toString() + ")");
				Toast.makeText(act.getApplication(), "An error occured! (" + exe.toString() + ")", Toast.LENGTH_SHORT).show();
			}
		}
		StringBuilder builder = new StringBuilder();
		//Check IP or DNS
		InetAddress address = null;
		if (CommonPingTools.isIP(ip)){
			//IP Address Stuff
			Toast.makeText(act.getApplication(), "Pinging IP Address " + ip + ":" + port, Toast.LENGTH_LONG).show();
			try {
				address = InetAddress.getByName(ip);
				if (address.isLoopbackAddress()){	//Localhost
					builder.append("IP Address: " + ip + " -> IP Address: localhost (" + address.getHostAddress() + ")" + "<br />");
				} else {
					builder.append("IP Address: " + ip + " -> IP Address: " + address.getHostAddress() + "<br />");
				}
			} catch (UnknownHostException e) {
				Toast.makeText(act.getApplication(), "Hostname is not recognized! (" + e.toString() + ")", Toast.LENGTH_SHORT).show();
			}
		} else {
			//DNS Address Stuff
			Toast.makeText(act.getApplication(), "Pinging DNS Address " + ip + ":" + port, Toast.LENGTH_SHORT).show();
			try {
				address = InetAddress.getByName(ip);
				builder.append("DNS Address: " + ip + " -> IP Address: " + address.getHostAddress() + "<br />");
			} catch (UnknownHostException e) {
				Toast.makeText(act.getApplication(), "Hostname is not recognized! (" + e.toString() + ")", Toast.LENGTH_SHORT).show();
			}
		}
		InetSocketAddress actualAddr = new InetSocketAddress(address, port);
		
		//Check if 1.6 or 1.7 server
		if(this.checkServerMode.isChecked()){
			//1.7
			ServerList server = new ServerList(actualAddr, "1.7", null);
			ServerListDB db = new ServerListDB(act);
			db.addServer(server);
		} else {
			//1.6
			ServerList server = new ServerList(actualAddr, "1.6", null);
			ServerListDB db = new ServerListDB(act);
			db.addServer(server);
		}
		
		//Update List View
		act.finish();
		act.startActivity(act.getIntent());
	}

}
