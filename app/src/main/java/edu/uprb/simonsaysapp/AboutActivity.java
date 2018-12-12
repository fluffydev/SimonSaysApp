package edu.uprb.simonsaysapp;

import android.app.Activity;
import android.os.Bundle;

import uprb.edu.simonsaysapp.R;

/*

 * File: AboutActivity.java

 * Author: Víctor M. Martínez 845-09-4440

 * Course: SICI 4997-KJ1, Prof. Antonio F. Huertas

 * Date: February 17, 2018

 * This class creates an about activity and populates it with

 * the indicated content.

 */

public class AboutActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

}

