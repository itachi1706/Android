package com.itachi1706.minecrafttools;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import com.itachi1706.minecrafttools.AsyncTasks.GetNewAppResources;

import org.apache.commons.net.ftp.FTPClient;


public class UpdateResources extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_resources);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_resources, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        private Activity act;
        private static TextView tv;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_update_resources, container, false);
            act = this.getActivity();
            ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            PlaceholderFragment.tv = (TextView) rootView.findViewById(R.id.tvProgress);
            NotificationManager notifyManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getActivity()).setContentTitle("Refresh Resources")
                    .setContentText("Preparing to refresh resource...").setSmallIcon(R.drawable.ic_launcher);
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            if (sharedPrefs.getBoolean("dl_wifi", true)){
                if (!mWifi.isConnected()) {
                    // Do whatever
                    notifyBuilder.setContentText("Please connect to a Wifi Network before continuing");
                    notifyBuilder.setContentTitle("Not Connected To Wifi!");
                    notifyManager.notify(100, notifyBuilder.build());
                    Toast.makeText(getActivity().getApplication(), "Not Connected to Wifi! Please connect to Wifi or uncheck the Download On Wifi setting", Toast.LENGTH_LONG).show();
                    tv.setText("Not Connected to Wifi! Please connect to Wifi or uncheck the Download On Wifi setting");
                    return rootView;
                }
            }
            notifyManager.notify(100, notifyBuilder.build());
            String serverAddr = "itachi1706.cloudapp.net";
            int ftpPort = 21;
            FTPClient ftp = new FTPClient();
            final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Updating Resources", "Downloading Resources Now...", true);
            ringProgressDialog.setCancelable(false);
            new GetNewAppResources(act, serverAddr, ftpPort, notifyManager, notifyBuilder, true, ringProgressDialog).execute(ftp);
            return rootView;
        }

        public static void progressUpdate(String text){
            tv.setText(text);
        }
    }
}
