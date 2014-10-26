package com.itachi1706.minecrafttools;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import com.itachi1706.minecrafttools.AsyncTasks.GetServerStatusList16;
import com.itachi1706.minecrafttools.AsyncTasks.GetServerStatusList17;
import com.itachi1706.minecrafttools.Database.ServerList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ServerListAdapter extends ArrayAdapter<ServerList>{
	
	// declaring our ArrayList of items
		private ArrayList<ServerList> objects;
		
		/* here we must override the constructor for ArrayAdapter
		* the only variable we care about now is ArrayList<Item> objects,
		* because it is the list of objects we want to display.
		*/
		public ServerListAdapter(Context context, int textViewResourceId, ArrayList<ServerList> objects) {
			super(context, textViewResourceId, objects);
			this.objects = objects;
		}
		
		/*
		 * we are overriding the getView method here - this is what defines how each
		 * list item will look.
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			// assign the view we are converting to a local variable
			View v = convertView;

			// first check to see if the view is null. if so, we have to inflate it.
			// to inflate it basically means to render, or show, the view.
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.listview_server_item_row, parent, false);
			}

			/*
			 * Recall that the variable position is sent in as an argument to this method.
			 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
			 * iterates through the list we sent it)
			 * 
			 * Therefore, i refers to the current Item object.
			 */
			ServerList i = objects.get(position);

			if (i != null) {

				// This is how you obtain a reference to the TextViews.
				// These TextViews are created in the XML files we defined.

				TextView serverName = (TextView) v.findViewById(R.id.toptext);	//Server Name
				TextView motd = (TextView) v.findViewById(R.id.middletextdata);	//Server MOTD
				TextView playerCount = (TextView) v.findViewById(R.id.bottomtext);	//Server Player List Count
				TextView serverIP = (TextView) v.findViewById(R.id.desctext);	//Server IP Address

				// check to see if each individual textview is null.
				// if not, assign some text!
				//Server Name
				if (serverName != null){
					if (i.getName() == null || i.getName().equals("")){
						//Server IP and Port
						serverName.setText(i.getAddress().getHostName() + ":" + i.getAddress().getPort());
					} else {
						serverName.setText(i.getName());
					}
				}
				
				//Server MOTD
				if (motd != null && playerCount != null){
					if (i.getVersion().toString().equals("1.7")){
						//1.7 Server
						InetSocketAddress inet = new InetSocketAddress(i.getAddress().getHostName(), i.getAddress().getPort());
						new GetServerStatusList17(v, inet, motd, playerCount).execute("");
					} else {
						//1.6 Server
						InetSocketAddress inet = new InetSocketAddress(i.getAddress().getHostName(), i.getAddress().getPort());
						new GetServerStatusList16(v, inet, motd, playerCount).execute("");
					}
				}
				if (serverIP != null){
					if (i.getAddress().getPort() == 25565){
						serverIP.setText(i.getAddress().getHostName());
					} else {
						serverIP.setText(i.getAddress().getHostName() + ":" + i.getAddress().getPort());
					}
				}
			}
			return v;

		}

}
