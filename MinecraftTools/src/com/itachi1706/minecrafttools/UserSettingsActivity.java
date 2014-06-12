package com.itachi1706.minecrafttools;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class UserSettingsActivity extends PreferenceActivity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Display the fragment as the main content. 
        Log.v("SETTINGS","Building SettingsFragment"); 
        getFragmentManager().beginTransaction() 
        .replace(android.R.id.content, new SettingsFragment()) 
        .commit(); 
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, 0, "Show current inserted");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                startActivity(new Intent(this, ShowSettingsActivity.class));
                return true;
        }
        return false;
    }
	
	public static class SettingsFragment extends PreferenceFragment {         

        public static final String TAG = "SettingsFragment"; 

        @Override 
        public void onCreate(Bundle savedInstanceState) { 
            Log.v(TAG,"onCreate"); 
            super.onCreate(savedInstanceState); 
            
            String version = "NULL", packName = "NULL";
            try {
				PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
				version = pInfo.versionName;
				packName = pInfo.packageName;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
            // Load the preferences from an XML resource 
            addPreferencesFromResource(R.xml.preference_headers); 
            getPreferenceManager().setSharedPreferencesMode(MODE_MULTI_PROCESS);
            
            Preference verPref = (Preference) findPreference("view_app_version");
            verPref.setSummary(version);
            Preference pNamePref = (Preference) findPreference("view_app_name");
            pNamePref.setSummary(packName);
        } 
         
} 
}
