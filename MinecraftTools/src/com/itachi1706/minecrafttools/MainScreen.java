package com.itachi1706.minecrafttools;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainScreen extends ActionBarActivity {	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		//Hacked disable strict mode for network processes (Might fix this instead of doing this in the future)
		if (android.os.Build.VERSION.SDK_INT > 9) {
		      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		      StrictMode.setThreadPolicy(policy);
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

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_screen,
					container, false);
			this.singleServerStatBtn = (Button) rootView.findViewById(R.id.btnStatusSingle);
			this.ServerStatListBtn = (Button) rootView.findViewById(R.id.btnStatusList);
			this.singleServerStatBtn.setOnClickListener(new OnClickListener() {
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
			this.ServerStatListBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//View Server List (List View)
					//Intent intent = new Intent();
					FragmentManager fm = getActivity().getSupportFragmentManager();
					DialogFragment dialog = new FeatureComingSoonDialog();
					Toast.makeText(getActivity().getApplication(), "Feature Coming Soon!", Toast.LENGTH_SHORT).show();
					dialog.show(fm, "comingsoon");
				}
			});
			return rootView;
		}

	}

}
