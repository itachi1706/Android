package com.itachi1706.animestreamer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class KissAnimeMain extends Activity {

    TextView htmlCode;
    //public static String htmlFromKissAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiss_anime_main);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kiss_anime_main, menu);
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
