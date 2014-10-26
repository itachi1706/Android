package com.itachi1706.minecrafttools;

import android.app.Activity;
import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.itachi1706.minecrafttools.Database.ServerList;
import com.itachi1706.minecrafttools.Database.ServerListDB;
import com.itachi1706.minecrafttools.PingingUtils.AddANewServerIntoList;
import com.itachi1706.minecrafttools.random.EditServerNameDialog;

import java.util.ArrayList;


public class ServerListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_list);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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
    public static class PlaceholderFragment extends ListFragment {

        private TextView addAddress;
        private ToggleButton checkServerMode;
        private Button addServer;
        public int selectedServerListItem = -1;
        protected Object mActionMode;
        ArrayList<ServerList> serverList;
        private View v;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_server_list, container, false);

            this.serverList = new ServerListDB(rootView.getContext()).getAllServers();
            //ServerList[] valuesr = (ServerList[]) tmp.toArray();
            this.v=rootView;

            this.addAddress=(TextView) rootView.findViewById(R.id.serverAddressAdd);
            this.checkServerMode=(ToggleButton) rootView.findViewById(R.id.toggleIs17);
            this.addServer=(Button) rootView.findViewById(R.id.btnServerListAdd);
            this.addServer.setOnClickListener(new AddANewServerIntoList(addAddress,checkServerMode,this.getActivity()));
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(FragmentActivity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(addAddress.getWindowToken(), 0);
            ServerListAdapter adapter = new ServerListAdapter(getActivity(), R.layout.listview_server_item_row , serverList);
            this.setListAdapter(adapter);
            ListView list = (ListView) rootView.findViewById(android.R.id.list);
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id){
                    if (mActionMode != null) {
                        return false;
                    }

                    selectedServerListItem = position;

                    // start the CAB using the ActionMode.Callback defined above
                    mActionMode = getActivity().startActionMode(mActionModeCallback);
                    view.setSelected(true);
                    return true;
                }
            });
            return rootView;
        }

        private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

            // the following method is called each time
            // the action mode is shown. Always called after
            // onCreateActionMode, but
            // may be called multiple times if the mode is invalidated.
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false; // Return false if nothing is done
            }

            // called when the user exits the action mode
            @Override
            public void onDestroyActionMode(ActionMode arg0) {
                mActionMode = null;
                selectedServerListItem = -1;
            }

            // called when the action mode is created; startActionMode() was called
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate a menu resource providing context menu items
                MenuInflater inflater = mode.getMenuInflater();
                // assumes that you have "contexual.xml" menu resources
                inflater.inflate(R.menu.serverrowselection, menu);
                return true;
            }

            // called when the user selects a contextual menu item
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.servermenuitem1_show:
                        show();
                        // the Action was executed, close the CAB
                        mode.finish();
                        return true;
                    case R.id.servermenuitem1_delete:
                        delete();
                        mode.finish();
                        return true;
                    case R.id.servermenuitem1_rename:
                        rename();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }
        };

        private void show() {
            Toast.makeText(this.getActivity().getApplication(),
                    String.valueOf(selectedServerListItem), Toast.LENGTH_LONG).show();
        }

        private void delete(){
            ServerList server = this.serverList.get(selectedServerListItem);
            new ServerListDB(v.getContext()).deleteServer(server);
            getActivity().finish();
            getActivity().startActivity(getActivity().getIntent());
        }

        private void rename(){
            ServerList server = this.serverList.get(selectedServerListItem);
            android.app.FragmentManager fm = this.getActivity().getFragmentManager();
            DialogFragment dialog = EditServerNameDialog.newInstance(server, v);
            dialog.show(fm, "Rename");
        }
    }
}
