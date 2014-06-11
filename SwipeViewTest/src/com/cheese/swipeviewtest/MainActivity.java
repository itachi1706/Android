package com.cheese.swipeviewtest;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// create the TabHost that will contain the Tabs
		TabHost mTabHost = getTabHost();

	    mTabHost.addTab(mTabHost.newTabSpec("first").setIndicator("First").setContent(new Intent(this  ,Tab1Activity.class )));
	    mTabHost.addTab(mTabHost.newTabSpec("second").setIndicator("Fragment Test").setContent(new Intent(this , Tab2Activity.class )));
	    mTabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
