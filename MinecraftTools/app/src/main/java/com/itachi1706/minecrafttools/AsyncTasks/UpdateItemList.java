package com.itachi1706.minecrafttools.AsyncTasks;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.itachi1706.minecrafttools.R;
import com.itachi1706.minecrafttools.UpdateDatabase;
import com.itachi1706.minecrafttools.Database.GetSQLData;
import com.itachi1706.minecrafttools.Database.McItem;
import com.itachi1706.minecrafttools.Database.McItemDB;

public class UpdateItemList extends AsyncTask <String, Void, Boolean>{
	
	Activity mActivity;
	String data;
	int progressr = 0;
	String namre;
	boolean manual;
	ProgressDialog dialog;
	
	public UpdateItemList(Activity activity, boolean manual, ProgressDialog dia){
		this.mActivity=activity;
		this.manual=manual;
		this.dialog=dia;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		final GetSQLData getData = new GetSQLData();
		progressr = 1;
		publishProgress();
		data = getData.getDataFromDB();
		progressr = 2;
		publishProgress();
		ArrayList<McItem> items = parseJSON(data);	//Parse the JSON Data
		progressr = 3;
		addData(items);
		return true;
	}
	
	@Override
	protected void onProgressUpdate(Void... progress) {
		if (manual){
			switch (progressr){
			case 0: UpdateDatabase.PlaceholderFragment.progressUpdate("");
			dialog.setMessage("Connecting to Online MySQL Database...");
			break;
			case 1: UpdateDatabase.PlaceholderFragment.progressUpdate("");
			dialog.setMessage("Downloading SQL Table...");
			break;
			case 2: UpdateDatabase.PlaceholderFragment.progressUpdate("");
			dialog.setMessage("Parsing JSON Array...");
			break;
			case 3: UpdateDatabase.PlaceholderFragment.progressUpdate("");
			dialog.setMessage("Inserting " + namre + " into database...");
			break;
			}
		} else {
			switch (progressr){
			case 0: dialog.setMessage("Connecting to Online MySQL Database... \nPlease do not exit out of this screen!");
			break;
			case 1: dialog.setMessage("Downloading SQL Table \nPlease do not exit out of this screen!");
			break;
			case 2: dialog.setMessage("Parsing JSON Array \nPlease do not exit out of this screen!");
			break;
			case 3: dialog.setMessage("Inserting " + namre + " into database... \nPlease do not exit out of this screen!");
			break;
			}
		}
		
    }
	
	public ArrayList<McItem> parseJSON(String result){
		ArrayList<McItem> items = new ArrayList<McItem>();
		try {
            JSONArray jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                McItem item = new McItem(json_data.getInt("mainID"),json_data.getInt("block_id"),json_data.getInt("block_subid"),json_data.getString("name"),
                		json_data.getInt("obtainable_normally"),json_data.getInt("isCraftable"),json_data.getString("craftSlot1"),
                		json_data.getString("craftSlot2"),json_data.getString("craftSlot3"),
                		json_data.getString("craftSlot4"),json_data.getString("craftSlot5"),json_data.getString("craftSlot6"),
                		json_data.getString("craftSlot7"),json_data.getString("craftSlot8"),json_data.getString("craftSlot9"),
                		json_data.getInt("isSmeltable"),json_data.getString("smeltWith"),json_data.getString("imgUrl"),json_data.getString("descUrl"), json_data.getString("blockIdName"));
                items.add(item);
            }
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());  
        }
		return items;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void addData(ArrayList<McItem> items) {
		//Drop database table and recreate it
		McItemDB db = new McItemDB(mActivity);
		db.dropTableAndRenew();
		for (Iterator i = items.iterator(); i.hasNext();) {
			 
			McItem p = (McItem) i.next();
			namre = p.getName();
			publishProgress();
			db.addItem(p);
		}
	}
	
	public void onPostExecute(Boolean response) {
		if (manual){
			mActivity.findViewById(R.id.pbNewDb).setVisibility(View.GONE);
			UpdateDatabase.PlaceholderFragment.progressUpdate("Completed! You can now exit out of this page with the back button!");
			dialog.dismiss();
		} else {
			dialog.dismiss();
			Toast.makeText(mActivity.getApplication(), "First Boot Completed!", Toast.LENGTH_LONG).show();
		}
	}

}
