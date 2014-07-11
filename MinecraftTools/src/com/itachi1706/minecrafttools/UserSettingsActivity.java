package com.itachi1706.minecrafttools;

import com.itachi1706.minecrafttools.random.WebGame2048;

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
        menu.add(Menu.NONE, 1, 0, "2048 Game! (Needs Internet Connection)");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                startActivity(new Intent(this, ShowSettingsActivity.class));
                return true;
            case 1:
            	startActivity(new Intent(this, WebGame2048.class));
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
            
            Preference prefs = (Preference) findPreference("view_sdk_version");
            prefs.setSummary(android.os.Build.VERSION.RELEASE);
            prefs = (Preference) findPreference("view_board_ver");
            prefs.setSummary(android.os.Build.BOARD);
            prefs = (Preference) findPreference("view_bootloader_ver");
            prefs.setSummary(android.os.Build.BOOTLOADER);
            prefs = (Preference) findPreference("view_brand_ver");
            prefs.setSummary(android.os.Build.BRAND);
            prefs = (Preference) findPreference("view_cpu1_ver");
            prefs.setSummary(android.os.Build.CPU_ABI);
            prefs = (Preference) findPreference("view_cpu2_ver");
            prefs.setSummary(android.os.Build.CPU_ABI2);
            prefs = (Preference) findPreference("view_device_ver");
            prefs.setSummary(android.os.Build.DEVICE);
            prefs = (Preference) findPreference("view_display_ver");
            prefs.setSummary(android.os.Build.DISPLAY);
            prefs = (Preference) findPreference("view_fingerprint_ver");
            prefs.setSummary(android.os.Build.FINGERPRINT);
            prefs = (Preference) findPreference("view_hardware_ver");
            prefs.setSummary(android.os.Build.HARDWARE);
            prefs = (Preference) findPreference("view_host_ver");
            prefs.setSummary(android.os.Build.HOST);
            prefs = (Preference) findPreference("view_id_ver");
            prefs.setSummary(android.os.Build.ID);
            prefs = (Preference) findPreference("view_manufacturer_ver");
            prefs.setSummary(android.os.Build.MANUFACTURER);
            prefs = (Preference) findPreference("view_model_ver");
            prefs.setSummary(android.os.Build.MODEL);
            prefs = (Preference) findPreference("view_product_ver");
            prefs.setSummary(android.os.Build.PRODUCT);
            prefs = (Preference) findPreference("view_radio_ver");
            if (android.os.Build.getRadioVersion() != null){
            	prefs.setSummary(android.os.Build.getRadioVersion());
            }
            prefs = (Preference) findPreference("view_serial_ver");
            prefs.setSummary(android.os.Build.SERIAL);
            prefs = (Preference) findPreference("view_tags_ver");
            prefs.setSummary(android.os.Build.TAGS);
            prefs = (Preference) findPreference("view_type_ver");
            prefs.setSummary(android.os.Build.TYPE);
            prefs = (Preference) findPreference("view_user_ver");
            if (android.os.Build.USER != null){
            	prefs.setSummary(android.os.Build.USER);
            }
        } 
         
} 
}
