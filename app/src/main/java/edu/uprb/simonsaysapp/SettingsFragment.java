package edu.uprb.simonsaysapp;

/*

 * File: SettingsFragment.java

 * Author: Víctor M. Martínez 845-09-4440

 * Course: SICI 4997-KJ1, Prof. Antonio F. Huertas

 * Date: February 17, 2018

 * This class contains the fragment that defines the settings of this

 * application.

 */


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import uprb.edu.simonsaysapp.R;

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener{

    private SharedPreferences savedValues;

    /*
     * Creates the preference class.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        savedValues = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    /* Calls the onResume method. */
    @Override
    public void onResume(){
        super.onResume();
        savedValues.registerOnSharedPreferenceChangeListener(this);
    }

    /* Calls the onPause method. */
    @Override
    public void onPause(){
        savedValues.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    /* Starts the SimonSaysfragment if it hasn't been called yet. */
    public void onSharedPreferenceChanged(SharedPreferences saved,
                                          String key){
        SimonSaysFragment invFragment =
                (SimonSaysFragment) getFragmentManager()
                        .findFragmentById(R.id.simonsays_fragment);
        if(invFragment != null){
            invFragment.onResume();
        }
    }
}
