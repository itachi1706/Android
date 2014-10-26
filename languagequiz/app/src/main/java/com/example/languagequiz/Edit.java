package com.example.languagequiz;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Edit extends Activity{
	public ArrayList<Question> quest=	new ArrayList<Question>();
	//quest.add(question1);
	//アクティビティ起動時に呼ばれる

	TextView b;
	EditText d;
	String c;
	Random arrayindex;
	SharedPreferences pref;
	Toast mytoast;
	TextView po;
	int points;
	int indexx = 0 ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.edit);
		arrayindex = new Random();
		//Intent intent =this.getIntent();
		points=getIntent().getIntExtra("data", 3);
		c=getIntent().getStringExtra("c");
		Question question1 =new Question ("what the fuck","hello");
		quest.add(question1);
		b=(TextView)this.findViewById(R.id.textViewJ1);
		b.setText(c);
		pref=this.getSharedPreferences("demo_file", MODE_PRIVATE);	
		Button btn4 = (Button)findViewById(R.id.buttonJ);
		d=(EditText)this.findViewById(R.id.editTextJ);
		po=(TextView)this.findViewById(R.id.textViewJ2) ;
		if(c.equalsIgnoreCase("Japanese"))
		{
			po.setText(quest.get(indexx).getQuestion());

		}
		else{
			po.setText(quest.get(indexx).getAnswer());
		}
		//ArrayAdapter<Question> lawl = new ArrayAdapter<Question>(this,android.R.layout.simple_list_item_1,quest);
		indexx=arrayindex.nextInt(quest.size());
		btn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if(c.equalsIgnoreCase("Japanese")){
					if(d.getText().toString().equalsIgnoreCase(quest.get(indexx).getAnswer()))	
					{
						points+=1;
						Intent Myintent=new Intent();
						int myvalue=points;
						Myintent.putExtra("data", myvalue);
						setResult(Activity.RESULT_OK, Myintent);
						quest.get(indexx).setCorrect(true);
						finish();

					}
					else{

						finish();
					}

				}
				else
				{
					if(d.getText().toString().equalsIgnoreCase(quest.get(indexx).getQuestion()))	
					{
						points+=1;
						Intent Myintent=new Intent();
						int myvalue=points;
						Myintent.putExtra("data", myvalue);
						setResult(Activity.RESULT_OK, Myintent);
						quest.get(indexx).setCorrect(true);
						finish();

					}
					else{
						finish();
					}

				}
			}

		

	});

}


}