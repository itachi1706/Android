package com.itachi1706.animestreamer;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
	//Controls Used
	Button btnStream, btnTest, btnParse;
	TextView tvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Define the controls
        btnStream = (Button) findViewById(R.id.btnStream);
        tvUrl = (TextView) findViewById(R.id.tvURL);
        btnParse = (Button) findViewById(R.id.btnParse);
        
        btnParse.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		
        		Intent intenter = new Intent(MainActivity.this, KissAnimeMain.class);
        		startActivity(intenter);
        	}
        	
        });
        
        
        //Set what happens when the button is clicked
        btnStream.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		
        		//Get TextView URL
        		String streamUrl = tvUrl.getText().toString();
        		//Start the Video Stream Class
        		Intent streamIntent = new Intent(MainActivity.this, StreamActivity.class);
        		//Add URL to the intent to pass on
        		streamIntent.putExtra("StreamURL", streamUrl);
        		startActivity(streamIntent);
        	}
        	
        });
        
        //DEBUG
        btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String testURL = "https://redirector.googlevideo.com/videoplayback?requiressl=yes&shardbypass=yes&cmbypass=yes&id=4a8b636375a7e15c&itag=18&source=picasa&cmo=secure_transport=yes&ip=0.0.0.0&ipbits=0&expire=1415864684&sparams=requiressl,shardbypass,cmbypass,id,itag,source,ip,ipbits,expire&signature=D449F2CCFF71F72209956D9516303EA8FE73F84.8709C31354A2AC54172F9507C31E13B4BD20DFC1&key=lh1";
				tvUrl.setText(testURL);
			}
        	
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
