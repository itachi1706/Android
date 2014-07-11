package com.itachi1706.minecrafttools.random;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import com.itachi1706.minecrafttools.Database.ServerList;
import com.itachi1706.minecrafttools.Database.ServerListDB;

public class EditServerNameDialog extends DialogFragment {
	
	private static ServerList server;
	private static View v;

	public static EditServerNameDialog newInstance(ServerList item, View view) {
		EditServerNameDialog f = new EditServerNameDialog();
		server=item;
		v=view;
	    return f;
	    }
	
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final EditText input = new EditText(getActivity());
		builder.setTitle("Rename server");
		builder.setMessage("Rename server to ");
		input.setText(server.getName());
		builder.setView(input);
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Close :D
				EditServerNameDialog.this.getDialog().cancel();
			}
		});
		
		builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Close :D
				String name = input.getText().toString();
				server.setName(name);
				new ServerListDB(v.getContext()).editServerName(server);
			}
		});
		
		
		//Return created object
		return builder.create();
	}

}
