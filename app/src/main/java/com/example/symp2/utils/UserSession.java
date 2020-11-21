package com.example.symp2.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.HashMap;

public class UserSession {

        SharedPreferences pref;

        // Editor for Shared preferences
        SharedPreferences.Editor editor;

        // Context
        Context context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        private static final String PREF_NAME = "UserSessionPref";


        private static final String IS_LOGIN = "IsLoggedIn";

        public static final String KEY_NAME = "name";

        public static final String KEY_id = "_id";

        public static final String KEY_alerts = "alerts";
        public static final String KEY_Done_alerts = "done_alerts";

        public static final String KEY_DATE = "DATE";



        public UserSession(Context context){
            this.context = context;
            pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
            editor.putString(KEY_DATE, String.valueOf(Calendar.DATE));
        }

        public void createLoginSession(String name, String id){
            // Storing login value as TRUE
            editor.putBoolean(IS_LOGIN, true);

            // Storing name in pref
            editor.putString(KEY_NAME, name);

            // Storing email in pref
            editor.putString(KEY_id, id);

            editor.putInt(KEY_alerts,0);
            editor.putInt(KEY_Done_alerts,0);

            // commit changes
            editor.commit();
        }





        public HashMap<String, String> getUserDetails(){
            HashMap<String, String> user = new HashMap<>();
            // user name
            user.put(KEY_NAME, pref.getString(KEY_NAME, null));

            // user email id
            user.put(KEY_id, pref.getString(KEY_id, null));


            // return user
            return user;
        }


        public void logoutUser(){
            // Clearing all data from Shared Preferences
            editor.putBoolean(IS_LOGIN,false);
            editor.commit();
        }


        public boolean isLoggedIn(){
            return pref.getBoolean(IS_LOGIN, false);
        }

        public void increment(){
            editor.putInt(KEY_alerts,pref.getInt(KEY_alerts,0)+1);
            editor.commit();
        }

        public int getAlerts(){
            return pref.getInt(KEY_alerts,0);
        }

        public void incrementAlerts(){
            editor.putInt(KEY_Done_alerts,pref.getInt(KEY_Done_alerts,0)+1);
            editor.commit();
        }
    public int getDoneAlerts(){
        return pref.getInt(KEY_Done_alerts,0);
    }
    public void turnZero(){
        editor.putInt(KEY_Done_alerts,0);
    }




}
