package com.itachi1706.minecrafttools.random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.itachi1706.minecrafttools.Database.ServerList;
import com.itachi1706.minecrafttools.Database.ServerListDB;
import com.itachi1706.minecrafttools.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditServerNameDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditServerNameDialog#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class EditServerNameDialog extends DialogFragment {

    private static ServerList server;
    private static View v;

    private OnFragmentInteractionListener mListener;

    public static EditServerNameDialog newInstance(ServerList item, View view) {
        EditServerNameDialog f = new EditServerNameDialog();
        server=item;
        v=view;
        return f;
    }
    public EditServerNameDialog() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_server_name_dialog, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
