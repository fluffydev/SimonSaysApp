package edu.uprb.simonsaysapp;

/*

 * File: SimonSaysFragment.java

 * Author: Víctor M. Martínez 845-09-4440

 * Course: SICI 4997-KJ1, Prof. Antonio F. Huertas

 * Modified December 8, 2018. SICI-4037 Data Communications. Prof. Juan Solá

 * Date: Febreuary 17, 2018

 * This application simulates a simon says game between two players

 * using a single android device.

 */

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import uprb.edu.simonsaysapp.R;


public class SimonSaysFragment extends Fragment {

    /* Fragment Fields. */
    private TextView clicksEditText, titleLabel;
    private Button redButton, blueButton, yellowButton, cyanButton,
            greenButton, magentaButton;
    private TextView resultTextView, pOnePattern, serverPattern;
    private int clicks, playerCtr;
    private int[] combination;
    private int[] player;

    /* Determines if the match has ended. */
    private boolean determineEnd = false;


    /* To save values for app calculation. */
    private SharedPreferences preferences;

    /*Timer Amount*/
    private final static int TIMER = 2000;
    private final static int TIMER_REVERT = 1000;

    /*
     * Called when the activity is starting, Initializes
     * the activity.
     * @Param: savedInstanceState The last known state of this application
     */
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

    /* Initializes layout assets that belong to the given fragment.
     * @param: inflater Inflates the assets
     * @param: container Stores the assets
     * @param: savedInstanceState The app's last recorded state
     * @return: The displayed view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();

        this.combination = intent.getIntArrayExtra("combination");

        View view = inflater.inflate(R.layout.fragment_simonsays, container,
                false);

        /* Creating the reference to each widget. */
        clicksEditText = (TextView) view.findViewById(R.id.clicksEditText);
        titleLabel = (TextView) view.findViewById(R.id.titleLabel);
        redButton = (Button) view.findViewById(R.id.redButton);
        blueButton = (Button) view.findViewById(R.id.blueButton);
        yellowButton = (Button) view.findViewById(R.id.yellowButton);
        cyanButton = (Button) view.findViewById(R.id.cyanButton);
        greenButton = (Button) view.findViewById(R.id.greenButton);
        magentaButton = (Button) view.findViewById(R.id.magentaButton);

        resultTextView = (TextView) view.findViewById(R.id.resultTextView);
        pOnePattern = (TextView) view.findViewById(R.id.pOnePattern);
        serverPattern = (TextView) view.findViewById(R.id.server_pattern);

        final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.beep);

        /* Setting the appropriate listeners. */
        redButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                mp.start();
                resetColors();
                redButton.setBackgroundColor(Color.WHITE);
                calculateArray(Colors.RED);
            }
        });

        blueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                mp.start();
                resetColors();
                blueButton.setBackgroundColor(Color.WHITE);
                calculateArray(Colors.BLUE);
            }
        });

        yellowButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                mp.start();
                resetColors();
                yellowButton.setBackgroundColor(Color.WHITE);
                calculateArray(Colors.YELLOW);
            }
        });

        cyanButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                mp.start();
                resetColors();
                cyanButton.setBackgroundColor(Color.WHITE);
                calculateArray(Colors.CYAN);
            }
        });

        greenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                mp.start();
                resetColors();
                greenButton.setBackgroundColor(Color.WHITE);
                calculateArray(Colors.GREEN);
            }
        });

        magentaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                mp.start();
                resetColors();
                magentaButton.setBackgroundColor(Color.WHITE);
                calculateArray(Colors.MAGENTA);
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
                resetValues();
                resetColors();
                return true;
            case R.id.menu_repeat:
                showCombination();
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

    /*
     * Creates the array given by the player.
     * @param clr The color of the pressed button
     */
    public void calculateArray(Colors clr) {
        if(playerCtr < player.length) {
            player[playerCtr] = clr.toInt();
            playerCtr++;
        }


        if(playerCtr == clicks){
            calculateResult();
            determineEnd = true;
        }
    }

    /*
     * Calculates the result of the game.
     */
    public void calculateResult(){
        for(int idx = 0; idx < player.length; idx++){
            // Second Player loss condition.
            if(player[idx] != combination[idx]) {
                resultTextView.setText(R.string.fail_label);

                break;
            }

            if(idx == player.length - 1)
                resultTextView.setText(R.string.win_label);
        }
        printResults();
    }

    /*
     * Prints outs both array combinations.
     */
    public void printResults(){
        String str1 = arrToString(player);
        String str2 = arrToString(combination);

        pOnePattern.setText(str1);
        serverPattern.setText(str2);
    }

    /*
     * Produces a string of the given array.
     */
    public String arrToString(int[] arr){
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < clicks; i++){
            str.append(Colors.toText(arr[i]));

            if( i < clicks - 1)
                str.append(", ");
        }

        return str.toString();
    }


    /*
    * Saves the current values being processed by the application
    * when it is paused.
    */
    @Override
    public void onPause(){
        Editor editor = preferences.edit();
        editor.putBoolean("determineEnd", determineEnd);

        editor.putInt("p1_counter", playerCtr);

        for(int idx = 0; idx < clicks; idx++){
            editor.putString("p1_array_" + idx, player[idx] + "");
        }

        editor.commit();
        super.onPause();
    }

    /*
     * Sets each field in the activity to the values that had been
     * saved before a pause.
     */
    @Override
    public void onResume() {
        super.onResume();

        /* Getting preferences. */
        clicks = Integer.parseInt(preferences.getString("pref_clicks", "5"));
        clicksEditText.setText(preferences.getString("pref_clicks", "5"));

         /* Each player's options. */
        player = new int[clicks];

        playerCtr = preferences.getInt("p1_counter", 0);

        /* Acquiring game state, */
        determineEnd = preferences.getBoolean("determineEnd", false);

        for (int idx = 0; idx < clicks; idx++){
                player[idx] = Integer.parseInt(preferences.getString(
                        "p1_array_" + idx, "0"));
        }

        if(playerCtr == clicks && determineEnd) {
            calculateResult();
        }
        resetValues();
        resetColors();

        try{
            TimeUnit.SECONDS.sleep(2);
        }catch(InterruptedException e){
            ;
        }
        showCombination();
    }

    /*
     * Displays the combination or pattern given by the server.
     */
    public void showCombination(){

        final Handler h = new Handler();
        Runnable task = new Runnable() {
            int count = 0;
            @Override
            public void run() {

                if(count < combination.length){
                    FlashColor(combination[count]);
                    count++;
                    h.postDelayed(this, TIMER);
                }
            }
        };
        h.post(task);
    }

    /*
     * Flashes the appropriate color based on the given input.
     */
    public void FlashColor(int color){

        Handler h = new Handler();

        final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.beep);
            switch (color){
                case 0:
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            redButton.setBackgroundColor(Color.RED);

                        }
                    }, TIMER_REVERT);
                    mp.start();
                    redButton.setBackgroundColor(Color.WHITE);

                    break;

                case 1:
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            blueButton.setBackgroundColor(Color.BLUE);
                        }
                    }, TIMER_REVERT);
                    mp.start();
                    blueButton.setBackgroundColor(Color.WHITE);

                    break;

                case 2:
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            yellowButton.setBackgroundColor(Color.YELLOW);

                        }
                    }, TIMER_REVERT);
                    mp.start();
                    yellowButton.setBackgroundColor(Color.WHITE);

                    break;

                case 3:
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cyanButton.setBackgroundColor(Color.CYAN);

                        }
                    }, TIMER_REVERT);
                    mp.start();
                    cyanButton.setBackgroundColor(Color.WHITE);

                    break;

                case 4:
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            greenButton.setBackgroundColor(Color.GREEN);

                        }
                    }, TIMER);
                    mp.start();
                    greenButton.setBackgroundColor(Color.WHITE);
                    break;

                case 5:
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            magentaButton.setBackgroundColor(Color.MAGENTA);

                        }
                    }, TIMER_REVERT);
                    mp.start();
                    magentaButton.setBackgroundColor(Color.WHITE);
                    break;

            }
    }


    /*
     * Returns each Button to their default color.
     */
    public void resetColors(){
        redButton.setBackgroundColor(Color.RED);
        blueButton.setBackgroundColor(Color.BLUE);
        yellowButton.setBackgroundColor(Color.YELLOW);
        cyanButton.setBackgroundColor(Color.CYAN);
        greenButton.setBackgroundColor(Color.GREEN);
        magentaButton.setBackgroundColor(Color.MAGENTA);
    }

    /*
     * This method both initializes the values for the variables in this application
     * and resets them when necessary.
     */
    public void resetValues (){
        /* Initializing the counters to keep track of each array. */
        playerCtr = 0;

        determineEnd = false;

        /* Player's options. */
        player = new int[clicks];

        resultTextView.setText(" ");
        pOnePattern.setText(" ");
        serverPattern.setText(" ");
    }
}



