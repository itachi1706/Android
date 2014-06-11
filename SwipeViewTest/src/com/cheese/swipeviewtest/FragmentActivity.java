package com.cheese.swipeviewtest;

import android.app.Activity;
import android.os.Bundle;

public class FragmentActivity extends Activity implements ListFragment.OnItemSelectedListener{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
    }

	//if the wizard generated an onCreateOptionsMenu you can delete
	// it, not needed for this tutorial
	
	@Override
	public void onItemSelected(String link) {
	DetailFragment fragment = (DetailFragment) getFragmentManager()
	        .findFragmentById(R.id.detailFragment);
	    if (fragment != null && fragment.isInLayout()) {
	      fragment.setText(link);
	    } 
}

	@Override
	public void onRssItemSelected(String link) {
		// TODO Auto-generated method stub
		
	}

}
