package com.itachi1706.minecrafttools;

import com.itachi1706.minecrafttools.FTPTest.ConAndDLFileFromFtpServer;
import com.itachi1706.minecrafttools.FTPTest.ConAndListFilesFromFtpServer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TestFTPMode extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_ftpmode);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_ftpmode, menu);
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

		private Button connectBtn;
		private Button dcBtn;
		private TextView fileListView;
		private Activity act;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_test_ftpmode,
					container, false);
			this.connectBtn = (Button) rootView.findViewById(R.id.btnFTPTestConnect);
			this.dcBtn = (Button) rootView.findViewById(R.id.btnFTPTestDL);
			act = this.getActivity();
			this.fileListView = (TextView) rootView.findViewById(R.id.tvFTPFilesResult);
			this.connectBtn.setOnClickListener(new ConAndListFilesFromFtpServer(fileListView, act));
			this.dcBtn.setOnClickListener(new ConAndDLFileFromFtpServer(fileListView, act));
			return rootView;
		}
	}

}
