package com.cheese.swipeviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Tab2Activity  extends Activity
{
	
	private Button fragBtn;
	
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView( R.layout.second_layout );
            this.fragBtn = (Button) findViewById(R.id.button1);
            this.fragBtn.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				Log.d("Manual: ", "Launching fragment...");
    				setContentView(R.layout.second_layout);
    			}
    		});
        }
}
