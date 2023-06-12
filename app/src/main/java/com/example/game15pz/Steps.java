package com.example.game15pz;

import android.content.Context;
import android.content.SharedPreferences;

public class Steps {
    public static final String SHARED_PREF = "sharedPref";
    public static final String LAST_STEP = "lastStep";
    public static final String LAST_TIME = "lastTime";

    public static final String BEST_TIME = "bestTime";
    public static final String BEST_STEP = "bestStep";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Steps(Context context){
        preferences = context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void saveLastStep(int steps){
        editor.putInt(LAST_STEP,steps).commit();
    }
    public int getLastStep(){
        return preferences.getInt(LAST_STEP,0);
    }
    public void saveLastTime(int seconds){
        editor.putInt(LAST_TIME,seconds).commit();
    }
    public int getLastTime(){
        return preferences.getInt(LAST_TIME,0);
    }
    public void saveBestTime(int seconds){
        editor.putInt(BEST_TIME,seconds).commit();
    }
    public int getBestTime(){
        return preferences.getInt(BEST_TIME,0);
    }
    public void saveBestStep(int steps){
        editor.putInt(BEST_STEP,steps).commit();
    }
    public int getBestStep(){
        return preferences.getInt(BEST_STEP,0);
    }
}
