package com.itachi1706.animestreamer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class KissAnimeMain extends Activity{
	
	TextView htmlCode;
	//public static String htmlFromKissAnime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kissanime);
		
		htmlCode = (TextView) findViewById(R.id.tvHTMLResults);
		
		//Get HTML from KissAnime AnimeList
		new KissAnimeListAsync(htmlCode).execute("http://kissanime.com/AnimeList");
		/*htmlFromKissAnime
		if (htmlFromKissAnime != null){
			htmlCode.setText(htmlFromKissAnime);
		} else {
			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle("ERROR").setMessage("Unable to parse HTML").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				KissAnimeMain.this.finish();
			}
		});
		}*/
		
		
	}

}
