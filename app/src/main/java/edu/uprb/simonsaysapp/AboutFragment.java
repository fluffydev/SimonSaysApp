package edu.uprb.simonsaysapp;

/*

 * File: AboutFragment.java

 * Author: Víctor M. Martínez 845-09-4440

 * Course: SICI 4997-KJ1, Prof. Antonio F. Huertas

 * Date: February 17, 2018

 * This class contains the fragment with the "about" section

 * of the app.

 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uprb.edu.simonsaysapp.R;

public class AboutFragment extends Fragment {
    private TextView aboutTextView;

    /*
     * Called when the activity is starting, Initializes
     * the activity.
     * @Param: savedInstanceState The last known state of this application
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /* Initializes layout assets that belong to the given fragment.
     * @param: inflater Inflates the assets
     * @param: container Stores the assets
     * @param: savedInstanceState The app's last recorded state
     * @return: The displayed view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container,
                false);
        return view;
    }
}
