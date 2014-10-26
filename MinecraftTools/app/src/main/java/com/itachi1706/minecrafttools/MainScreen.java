package com.itachi1706.minecrafttools;


import android.app.Activity;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.itachi1706.minecrafttools.AsyncTasks.GetNewAppResources;

import org.apache.commons.net.ftp.FTPClient;

public class MainScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, UserSettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        private Button singleServerStatBtn;
        private Button ServerStatListBtn;
        private Button McListTableBtn;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
            this.singleServerStatBtn = (Button) rootView.findViewById(R.id.btnStatusSingle);
            this.ServerStatListBtn = (Button) rootView.findViewById(R.id.btnStatusList);
            this.McListTableBtn = (Button) rootView.findViewById(R.id.btnMcItemTable);
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            if (sharedPrefs.getBoolean("first_boot", true)){
                Toast.makeText(getActivity().getApplication(), "Initializing First Boot", Toast.LENGTH_LONG).show();
                NotificationManager notifyManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getActivity()).setContentTitle("Refresh Resources")
                        .setContentText("Preparing to refresh resource...").setSmallIcon(R.drawable.ic_launcher);
                ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //Update Resources
                if (sharedPrefs.getBoolean("dl_wifi", true)){
                    if (!mWifi.isConnected()) {
                        // Do whatever
                        notifyBuilder.setContentText("Please connect to a Wifi Network before continuing");
                        notifyBuilder.setContentTitle("Not Connected To Wifi!");
                        notifyManager.notify(100, notifyBuilder.build());
                        Toast.makeText(getActivity().getApplication(), "Not Connected to Wifi! Please connect to Wifi or uncheck the Download On Wifi setting", Toast.LENGTH_LONG).show();
                        return rootView;
                    }
                }
                final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Updating Resources", "Downloading Resources Now... Please do not exit out of this screen!", true);
                ringProgressDialog.setCancelable(false);
                notifyManager.notify(100, notifyBuilder.build());
                String serverAddr = "itachi1706.cloudapp.net";
                int ftpPort = 21;
                FTPClient ftp = new FTPClient();
                new GetNewAppResources(getActivity(), serverAddr, ftpPort, notifyManager, notifyBuilder, false, ringProgressDialog).execute(ftp);

                //End off
                sharedPrefs.edit().putBoolean("first_boot", false).apply();
            }
            this.singleServerStatBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Ability to check for a single server
                    Intent intent = new Intent(getActivity(), SingleServerChecker.class);
                    startActivity(intent);
					/*FragmentManager fm = getActivity().getSupportFragmentManager();
					DialogFragment dialog = new FeatureComingSoonDialog();
					dialog.show(fm, "comingsoon");*/
                }
            });
            this.ServerStatListBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //View Server List (List View)
                    //Intent intent = new Intent();
                    startActivity(new Intent(getActivity(), ServerListActivity.class));
					/*FragmentManager fm = getActivity().getSupportFragmentManager();
					DialogFragment dialog = new FeatureComingSoonDialog();
					Toast.makeText(getActivity().getApplication(), "Feature Coming Soon!", Toast.LENGTH_SHORT).show();
					dialog.show(fm, "comingsoon");*/
                }
            });
            this.McListTableBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //View Server List (List View)
                    //Intent intent = new Intent();
                    startActivity(new Intent(getActivity(), GetMcItemList.class));
					/*FragmentManager fm = getActivity().getSupportFragmentManager();
					DialogFragment dialog = new FeatureComingSoonDialog();
					Toast.makeText(getActivity().getApplication(), "Feature Coming Soon!", Toast.LENGTH_SHORT).show();
					dialog.show(fm, "comingsoon");*/
                }
            });
            return rootView;
        }
    }
}
