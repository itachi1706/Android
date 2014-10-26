package com.itachi1706.animestreamer;

import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Kenneth on 26/10/2014, 8:26 PM
 * for Anime Streamer in package com.itachi1706.animestreamer
 */
public class KissAnimeListAsync extends AsyncTask<String, Void, String> {

    TextView htmlCoder;

    public KissAnimeListAsync(TextView htmlCode) {
        // TODO Auto-generated constructor stub
        htmlCoder = htmlCode;
    }

    @Override
    protected String doInBackground(String... urls) {
        URI url;
        String htmltmp = "";
        try {
            url = new URI(urls[0]);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null)
            {
                str.append(line);
            }
            in.close();
            htmltmp = str.toString();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htmltmp;
    }

    protected void onPostExecute(String html) {
        //KissAnimeMain.htmlFromKissAnime = html;
        htmlCoder.setText(html);
    }

}

