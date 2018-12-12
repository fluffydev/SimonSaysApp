package edu.uprb.simonsaysapp;

/*

 * File: Colors.java

 * Author: Víctor M. Martínez 845-09-4440

 * Course: SICI 4997-KJ1, Prof. Antonio F. Huertas

 * Date: Febreuary 17, 2018

 * This is an enumeration that representes a group of colors.

 */


public enum Colors {

    RED, BLUE, YELLOW, CYAN, GREEN, MAGENTA;

    // Returns a numerical representation of this color
    public int toInt(){
        switch(this){
            case RED: return 0;
            case BLUE: return 1;
            case YELLOW: return 2;
            case CYAN: return 3;
            case GREEN: return 4;
            case MAGENTA: return 5;
            default: return -1;

        }
    }

    // Returns the string representations of this color.
    public static String toText(int clr){
        switch(clr){
            case 0: return "R";
            case 1: return "B";
            case 2: return "Y";
            case 3: return "C";
            case 4: return "G";
            case 5: return "M";
            default: return "Unknown";

        }
    }
}


