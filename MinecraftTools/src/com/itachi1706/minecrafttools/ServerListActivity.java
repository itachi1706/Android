package com.itachi1706.minecrafttools;

import java.util.ArrayList;

import com.itachi1706.minecrafttools.Database.ServerList;
import com.itachi1706.minecrafttools.Database.ServerListDB;
import com.itachi1706.minecrafttools.PingingUtils.AddANewServerIntoList;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ServerListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_list);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.server_list, menu);
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
		} else if (id == R.id.action_refresh){
			finish();
			startActivity(getIntent());
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends ListFragment{
		
		private TextView addAddress;
		private ToggleButton checkServerMode;
		private Button addServer;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_server_list,
					container, false);
			
			ArrayList<ServerList> tmp = new ServerListDB(rootView.getContext()).getAllServers();
			//ServerList[] valuesr = (ServerList[]) tmp.toArray();
			
			this.addAddress=(TextView) rootView.findViewById(R.id.serverAddressAdd);
			this.checkServerMode=(ToggleButton) rootView.findViewById(R.id.toggleIs17);
			this.addServer=(Button) rootView.findViewById(R.id.btnServerListAdd);
			this.addServer.setOnClickListener(new AddANewServerIntoList(addAddress,checkServerMode,this.getActivity()));
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(FragmentActivity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(addAddress.getWindowToken(), 0);
			ServerListAdapter adapter = new ServerListAdapter(getActivity(), R.layout.listview_server_item_row , tmp);
			this.setListAdapter(adapter);
			
			return rootView;
		}
	}

}
