package com.itachi1706.animestreamer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

public class StreamActivity extends Activity {
	
	ProgressDialog pDialog;
	VideoView vStream;
	String url = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stream);
		
		vStream = (VideoView) findViewById(R.id.vvStream);
		
		//Get the URL from the MainActivity class
		Bundle bundle = getIntent().getExtras();
		if (bundle != null){
			if (bundle.getString("StreamURL").isEmpty()){
				noURL();
			} else {
				url = bundle.getString("StreamURL");
			}
		}
		
		Log.d("DEBUG", url);
		
		//Check if URL is empty
		if (url == null || url==""){
			//Empty
			noURL();
		} else {
		
			//Set the progressbar to notify
			pDialog = new ProgressDialog(StreamActivity.this);
			pDialog.setTitle("Starting Stream");
			pDialog.setMessage("Buffering...\nParsing URL: " + url);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			//Show ProgressBar
			pDialog.show();
			
			//Test out calling intent
			Intent i = new Intent(Intent.ACTION_VIEW);
			//i.setData(Uri.parse(url));
			i.setDataAndType(Uri.parse(url), "video/*");
			startActivity(i);
			pDialog.dismiss();
			this.finish();
			
			/*
			//Try an Async Task for VideoView (Deprecrated)
			try{
				//Set up MediaController
				MediaController mediacontroller = new MediaController(StreamActivity.this);
				mediacontroller.setAnchorView(vStream);
				
				//Get URL from String
				Uri videoUri = Uri.parse(url);
				vStream.setMediaController(mediacontroller);
				vStream.setVideoURI(videoUri);
			} catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	            cannotParseURL();
	        }
			
			vStream.requestFocus();
			vStream.setOnPreparedListener(new OnPreparedListener(){
				
				 // Close the progress bar and play the video
	            public void onPrepared(MediaPlayer mp) {
	                pDialog.dismiss();
	                vStream.start();
	            }
			});*/
		}
	}
	
	private void noURL(){
		Builder errorDiag = new AlertDialog.Builder(this);
		errorDiag.setTitle("URL Not Found!");
		errorDiag.setMessage("Unable to find URL. Please make sure you entered the URL");
		errorDiag.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				StreamActivity.this.finish();
			}
		});
		errorDiag.show();
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private void cannotParseURL(){
		Builder errorDiag = new AlertDialog.Builder(this);
		errorDiag.setTitle("Unable to parse URL!");
		errorDiag.setMessage("Unable to parse URL. Please check your URL entered.");
		errorDiag.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				StreamActivity.this.finish();
			}
		});
		errorDiag.show();
	}

}
