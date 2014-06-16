package com.itachi1706.minecrafttools;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itachi1706.minecrafttools.Database.GetSQLData;
import com.itachi1706.minecrafttools.Database.McItem;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PopulateDatabase extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_populate_database);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.populate_database, menu);
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
		
		private TableLayout tl;
		String data = "";
		TableRow tr;
		TextView label;
		View v;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_populate_database, container, false);
			this.tl = (TableLayout) rootView.findViewById(R.id.tl_items);
			this.v=rootView;
			final GetSQLData getData = new GetSQLData();
			new Thread(new Runnable() {
				public void run(){
					data = getData.getDataFromDB();
					
					getActivity().runOnUiThread(new Runnable(){
						
						@Override
						public void run(){
							ArrayList<McItem> items = parseJSON(data);	//Parse the JSON Data
							//Todo: Put data into database
	                        addData(items);
						}
					});
				}
			});
			return rootView;
		}
		
		public ArrayList<McItem> parseJSON(String result){
			ArrayList<McItem> items = new ArrayList<McItem>();
			try {
	            JSONArray jArray = new JSONArray(result);
	            for (int i = 0; i < jArray.length(); i++) {
	                JSONObject json_data = jArray.getJSONObject(i);
	                McItem item = new McItem(json_data.getInt("mainID"),json_data.getInt("block_id"),json_data.getInt("block_subid"),json_data.getString("name"),
	                		json_data.getBoolean("obtainable_normally"),json_data.getBoolean("isCraftable"),json_data.getString("craftSlot1"),
	                		json_data.getString("craftSlot2"),json_data.getString("craftSlot3"),
	                		json_data.getString("craftSlot4"),json_data.getString("craftSlot5"),json_data.getString("craftSlot6"),
	                		json_data.getString("craftSlot7"),json_data.getString("craftSlot8"),json_data.getString("craftSlot9"),
	                		json_data.getBoolean("isSmeltable"),json_data.getString("smeltWith"),json_data.getString("imgUrl"),json_data.getString("descUrl"));
	                items.add(item);
	            }
	        } catch (JSONException e) {
	            Log.e("log_tag", "Error parsing data " + e.toString());  
	        }
			return items;
		}
		
		@SuppressWarnings({ "deprecation", "rawtypes" })
		public void addData(ArrayList<McItem> items) {
			for (Iterator i = items.iterator(); i.hasNext();) {
				 
				McItem p = (McItem) i.next();
	 
	            /** Create a TableRow dynamically **/
	            this.tr = new TableRow(v.getContext());
	 
	            /** Creating a TextView to add to the row **/
	            label = new TextView(v.getContext());
	            label.setText(p.getId() + ":" + p.getSubId());
	            label.setId(p.getMainId());
	            label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
	                    LayoutParams.WRAP_CONTENT));
	            label.setPadding(5, 5, 5, 5);
	            label.setBackgroundColor(Color.GRAY);
	            LinearLayout Ll = new LinearLayout(v.getContext());
	            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
	                    LayoutParams.WRAP_CONTENT);
	            params.setMargins(5, 2, 2, 2);
	            //Ll.setPadding(10, 5, 5, 5);
	            Ll.addView(label,params);
	            tr.addView((View)Ll); // Adding textView to tablerow.
	 
	            /** Creating Qty Button **/
	            TextView place = new TextView(v.getContext());
	            place.setText(p.getName());
	            place.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
	                    LayoutParams.WRAP_CONTENT));
	            place.setPadding(5, 5, 5, 5);
	            place.setBackgroundColor(Color.GRAY);
	            Ll = new LinearLayout(v.getContext());
	            params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
	                    LayoutParams.WRAP_CONTENT);
	            params.setMargins(0, 2, 2, 2);
	            //Ll.setPadding(10, 5, 5, 5);
	            Ll.addView(place,params);
	            tr.addView((View)Ll); // Adding textview to tablerow.
	 
	             // Add the TableRow to the TableLayout
	            tl.addView(tr, new TableLayout.LayoutParams(
	                    LayoutParams.FILL_PARENT,
	                    LayoutParams.WRAP_CONTENT));
	        }
	    }
	}
	
	

}
