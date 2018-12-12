package edu.uprb.simonsaysapp;

/*

 * File: SettingsActivity.java

 * Author: Víctor M. Martínez 845-09-4440

 * Course: SICI 4997-KJ1, Prof. Antonio F. Huertas

 * Date: February 17, 2018

 * This class creates a setting activity and populates it with

 * the indicated content.

 */

import android.app.Activity;
import android.os.Bundle;

import uprb.edu.simonsaysapp.R;

public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //  Display Fragment as main content
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
