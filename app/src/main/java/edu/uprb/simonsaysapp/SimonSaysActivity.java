package edu.uprb.simonsaysapp;

/*

 * File: SimonSaysActivity.java

 * Author: Víctor M. Martínez 845-09-4440

 * Course: SICI 4997-KJ1, Prof. Antonio F. Huertas

 * Date: Febreuary 17, 2018

 * This activity holds the fragment for the Simon Says activity.

 */


import android.app.Activity;
import android.os.Bundle;

import uprb.edu.simonsaysapp.R;

public class SimonSaysActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_simonsays);

        int[] combination = getIntent().getIntArrayExtra("combination");
        getIntent().putExtra("combination", combination);

    }
}
