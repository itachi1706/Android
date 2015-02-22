package com.itachi1706.steaminfo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.community.SteamGame;
import com.github.koraktor.steamcondenser.steam.community.SteamId;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
Useful Links
https://developer.valvesoftware.com/wiki/Steam_Condenser - Steam Condenser
https://github.com/koraktor/steam-condenser - GitHub
http://koraktor.de/steam-condenser/ - Main Page
https://github.com/koraktor/steam-condenser/wiki/Usage-Examples-Java - Usage Examples
 */

public class MainScreen extends ActionBarActivity {

    EditText playerName;
    Button submit;
    TextView endResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        playerName = (EditText) findViewById(R.id.tbPlayer);
        submit = (Button) findViewById(R.id.btnSubmit);
        endResult = (TextView) findViewById(R.id.tvResult);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerName.getText().toString().length() == 0){
                    endResult.setText("Please enter a player name");
                    return;
                }
                String name = playerName.getText().toString();
                StringBuilder build = new StringBuilder();
                build.append("Games that ").append(playerName).append(" has\n");
                try {
                    SteamId playerID = SteamId.create(name);
                    HashMap<Integer, SteamGame> games = playerID.getGames();
                    for (Object o : games.entrySet()) {
                        HashMap.Entry pair = (HashMap.Entry) o;
                        SteamGame g = (SteamGame) pair.getValue();
                        build.append(g.getName()).append("\n");
                    }
                } catch (SteamCondenserException e) {
                    endResult.setText(e.toString() + "");
                    return;
                }
                endResult.setText(build.toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
