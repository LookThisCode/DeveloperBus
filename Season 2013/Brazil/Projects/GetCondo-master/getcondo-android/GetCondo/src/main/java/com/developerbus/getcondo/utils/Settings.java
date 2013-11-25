package com.developerbus.getcondo.utils;

/**
 * Created by bruno on 11/22/13.
 */

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.developerbus.getcondo.models.Session;

public class Settings {

    // Configuration
    public final static String ASSETS_HOSTNAME = "http://192.168.12.56:3000";
    public final static String API_HOSTNAME    = "http://192.168.12.56:3000/api";

    private static Settings mInstance;

    private static Session mSession;


    public static void initInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Settings();
        }
    }

    public static Settings getInstance() {
        return mInstance;
    }

    public static String apiHostname() {

        return API_HOSTNAME;
    }
    public static String assetsHostname() {

        return ASSETS_HOSTNAME;
    }

    public static Session getSession() {
        return mSession;
    }

    public static void setSession(Session mSession) {
        Settings.mSession = mSession;
    }

    private Settings() {
        // Constructor hidden because this is a singleton
    }
}
