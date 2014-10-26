package com.itachi1706.minecrafttools.AsyncTasks;

import java.io.IOException;
import java.net.InetAddress;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itachi1706.minecrafttools.R;
import com.itachi1706.minecrafttools.PingingUtils.CommonPingTools;
import com.itachi1706.minecrafttools.PingingUtils.PingServer17;
import com.itachi1706.minecrafttools.PingingUtils.PingServer17.StatusResponse;

/**
 * Async Task for 1.7 (Experimental)
 */

public class GetServerStatusFor17 extends AsyncTask <PingServer17, Void, StatusResponse>{
	
	Activity mContex;
	StringBuilder mbuilder;
	String mIP;
	InetAddress mAddress;
	TextView resultView;
	private Exception e=null;
	
   public  GetServerStatusFor17(Activity contex, StringBuilder builder, String ip, InetAddress address, TextView result)
    {
     this.mContex=contex;
     this.mAddress=address;
     this.mIP=ip;
     this.resultView=result;
     this.mbuilder=builder;
    }

	@Override
	protected StatusResponse doInBackground(PingServer17... server) {
        try {
			return server[0].fetchData();
		} catch (IOException e) {
			this.e=e;
			return null;
		}
    }
	
	public void onPostExecute(StatusResponse response) {
		if (e==null){
			mbuilder.append("<br />");
			mbuilder.append("====================================<br />");
			if (CommonPingTools.isIP(mIP)){
				mbuilder.append("Server Info for " + mAddress.getHostAddress() + "<br />");
			} else {
				mbuilder.append("Server Info for " + mAddress.getHostName() + "<br />");
			}
			mbuilder.append("====================================<br />");
			mbuilder.append("<font>MOTD: <b>" + CommonPingTools.parseFormatting(response.getDescription())  + "</b></font><br />");
			mbuilder.append("Player: " + response.getPlayers().getOnline() + "/" + response.getPlayers().getMax()  + "<br />");
			mbuilder.append("MC Version: " + response.getVersion().getName() + " (Protocol Version " + response.getVersion().getProtocol() + ")"  + "<br />");
			mbuilder.append("Ping: " + response.getTime()  + "<br />");
			//System.out.println("Favicon: " + response.getFavicon());
			mContex.findViewById(R.id.pbUpdateRes).setVisibility(View.GONE);
			this.resultView.setText(Html.fromHtml(mbuilder.toString()));
		} else {
			Toast.makeText(mContex.getApplication(), "An error occured! (" + e.toString() + ")", Toast.LENGTH_SHORT).show();
			mContex.findViewById(R.id.pbUpdateRes).setVisibility(View.GONE);
		}
    }
	
}
