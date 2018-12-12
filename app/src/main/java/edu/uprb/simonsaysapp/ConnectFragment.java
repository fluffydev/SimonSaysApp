package edu.uprb.simonsaysapp;

/*

 * File: ConnectFragment.java

 * Author: Víctor M. Martínez 845-09-4440

 * Course: SICI 4037-LJ1, Dr. Juan M. Solá

 * Date: December 7, 2018

 * This Fragment defines the operations for the ConnectActivity and

 * handles the logic for the operations of the Simon Says Game.

 */

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.uprb.sockets.Client;
import uprb.edu.simonsaysapp.R;

/**
 * Created by Víctor M. Martínez on 12/6/2018.
 */

public class ConnectFragment extends Fragment {

    private TextView response;
    private EditText editAddress, editPort;
    private Button btnConnect, btnPlay, btnClear;


    private int playerCtr;
    public ConnectFragment parent = this;
    public int[] combination;

    /* To save values for app calculation. */
    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Set the default value for the preferences.
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);

        /* Creates a shared preferences object. */
        // Links shared preferences logical name with the physical name of the file.
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // Turns on the options menu:
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.connect_fragment, container, false);

        editAddress = (EditText) view.findViewById(R.id.editAddress);
        editPort = (EditText) view.findViewById(R.id.editPort);
        btnConnect = (Button) view.findViewById(R.id.btnConnect);
        btnPlay = (Button) view.findViewById(R.id.btnPlay);
        btnClear = (Button) view.findViewById(R.id.btnClear);
        response = (TextView) view.findViewById(R.id.response);

        btnConnect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Client myClient = new Client(parent, editAddress.getText()
                        .toString(), Integer.parseInt(editPort
                        .getText().toString()), response, playerCtr);
                myClient.execute();
            }
        });

        btnPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SimonSaysActivity.class);
                intent.putExtra("combination", combination);
                startActivity(intent);
            }
        });

        btnClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                response.setText("");
            }
        });

        return view;

    }

    /*
    * Handles the event for menu item selection. Returns
    * true if one of the options is selected. If no option is selected,
    * superclass handles event.
    * @param item The clicked item on the menu
    * @return True after processing given selection
    */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Attempt to get an instance of the SettingsFragment Object
        SettingsFragment settingsFragment = (SettingsFragment) getFragmentManager()
                .findFragmentById(R.id.settings_fragment);

        // If the fragment is null, displays the appropriate menu
        if(settingsFragment == null)
            inflater.inflate(R.menu.fragment_simonsays, menu);
        else
            ;
    }

    /*
    * Handles the event for menu item selection. Returns
    * true if one of the options is selected. If no option is selected,
    * superclass handles event.
    * @param item The clicked item on the menu
    * @return True after processing given selection
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reset:
                //resetValues();
                //resetColors();
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(getActivity(),
                        SettingsActivity.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(getActivity(),
                        AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        this.playerCtr = Integer.parseInt(preferences.getString("pref_clicks", "5"));

    }
}
