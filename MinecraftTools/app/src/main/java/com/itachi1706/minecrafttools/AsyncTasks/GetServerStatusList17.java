package com.itachi1706.minecrafttools.AsyncTasks;

import java.io.IOException;
import java.net.InetSocketAddress;

import android.os.AsyncTask;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itachi1706.minecrafttools.PingingUtils.CommonPingTools;
import com.itachi1706.minecrafttools.PingingUtils.PingServer17;
import com.itachi1706.minecrafttools.PingingUtils.PingServer17.StatusResponse;

public class GetServerStatusList17 extends AsyncTask <String, Void, StatusResponse>{
	
	View mContex;
	InetSocketAddress maddress;
	TextView motdView;
	TextView playerView;
	private Exception e=null;
	
	public  GetServerStatusList17(View context, InetSocketAddress address, TextView motd, TextView player)
    {
     this.mContex=context;
     this.maddress=address;
     this.motdView=motd;
     this.playerView=player;
    }
	
	@Override
	protected StatusResponse doInBackground(String... server) {
			//1.7
			try {
				PingServer17 servers = new PingServer17();
				servers.setAddress(maddress);
				return servers.fetchData();
			} catch (IOException e) {
				this.e=e;
				return null;
			}
    }
	
	public void onPostExecute(StatusResponse response) {
		if (e==null){
			motdView.setText(Html.fromHtml(CommonPingTools.parseFormatting(response.getDescription())));
			playerView.setText(Html.fromHtml("<font color=#55FF55>" + response.getPlayers().getOnline() + "</font><font color='#AAAAAA'>/</font><font color=#FF5555>" + response.getPlayers().getMax() + "</font>"));
		} else {
			Toast.makeText(mContex.getContext(), "An error occured! (" + e.toString() + ")", Toast.LENGTH_SHORT).show();
			motdView.setText(Html.fromHtml("<font color='#FF5555'>Unable to get server info!</font>"));
			playerView.setText(Html.fromHtml("<font color=#FF5555>?????</font><font color='#AAAAAA'>/</font><font color=#FF5555>?????</font>"));
			//mContex.findViewById(R.id.pBServerList).setVisibility(View.GONE);
		}
    }


}
