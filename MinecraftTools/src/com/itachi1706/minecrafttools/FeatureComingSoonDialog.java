package com.itachi1706.minecrafttools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class FeatureComingSoonDialog extends DialogFragment {
	
	public static FeatureComingSoonDialog newInstance() {
		FeatureComingSoonDialog f = new FeatureComingSoonDialog();
	    return f;
	    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.feature_coming_soon).setNegativeButton(R.string.feature_coming_soon_confirm, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Close :D
				FeatureComingSoonDialog.this.getDialog().cancel();
			}
		});
		
		//Return created object
		return builder.create();
	}

}
