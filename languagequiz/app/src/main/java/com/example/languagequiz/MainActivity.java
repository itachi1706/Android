package com.example.languagequiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
 public int  points =0;
TextView a;
String c;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		a=(TextView)this.findViewById(R.id.textView3);
		a.setText("points : "+points);
		Intent e=new Intent(MainActivity.this,Edit.class);
		e.putExtra("data",points);
		Button btn1 = (Button)findViewById(R.id.button4);
		Button btn2 = (Button)findViewById(R.id.button2);
		btn1.setOnClickListener(new OnClickListener(){
			@Override
        	public void onClick(View v) {
        		Intent myintent = new Intent(MainActivity.this,Edit.class);
        		myintent.putExtra("c","Japanese");
        		startActivityForResult(myintent,1);
        		a.setText("pointssss : "+points);
			}
		});
		btn2.setOnClickListener(new OnClickListener(){
			@Override
        	public void onClick(View v) {
        		Intent myintent= new Intent(MainActivity.this,Edit.class);
        		myintent.putExtra("c", "English");
        		startActivityForResult(myintent,1);
        		a.setText("pointsss : "+points);
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
	public void onActivityResult(int requestCode, int resultCode, Intent myintent){
		super.onActivityResult(requestCode, resultCode, myintent);
		if (requestCode == 1 && resultCode == Activity.RESULT_OK){
			points = myintent.getIntExtra("data", 0);
			a.setText("pointsss : " + points);
		}
	}

}