package com.example.nutechwallet.core.singleton;

import android.content.Context;

public class SingletonLikeApp {
    private static SingletonLikeApp singleton;

    private PreferencesManager mAppPreferences;

    public SingletonLikeApp() {
        //Empty Constructor
    }

    public PreferencesManager getSharedPreferences(Context context) {
        if (mAppPreferences == null) {
            mAppPreferences = new PreferencesManager(context);
        }

        return mAppPreferences;
    }

    public static SingletonLikeApp getInstance() {
        if (singleton == null) {
            singleton = new SingletonLikeApp();
        }

        return singleton;
    }
}
