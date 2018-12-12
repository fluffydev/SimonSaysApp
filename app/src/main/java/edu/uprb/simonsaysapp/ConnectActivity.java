package edu.uprb.simonsaysapp;

/*

 * File: ConnectActivity.java

 * Author: Víctor M. Martínez 845-09-4440

 * Course: SICI 4037-LJ1, Dr. Juan M. Solá

 * Date: December 7, 2018

 * This main activity for the Simon Says Client app.

 */

import android.app.Activity;
import android.os.Bundle;

import uprb.edu.simonsaysapp.R;

/**
 * Created by Fluffy on 12/8/2018.
 */

public class ConnectActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_connect);
    }
}