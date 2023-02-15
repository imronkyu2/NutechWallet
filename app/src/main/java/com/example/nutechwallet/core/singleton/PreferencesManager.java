package com.example.nutechwallet.core.singleton;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.nutechwallet.model.login.LoginResponse;
import com.example.nutechwallet.util.Const;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PreferencesManager {
    private static final String TAG = PreferencesManager.class.getName();

    private SharedPreferences sharedPreferences;

    public PreferencesManager(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public void clearAll() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Const.Preferences.TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(Const.Preferences.TOKEN, "");
    }



    public void setUser(LoginResponse loginResponse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (loginResponse.getToken() != null) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                String userString = mapper.writeValueAsString(loginResponse);
                editor.putString(Const.Preferences.USER_LOGIN, userString);
            } catch (JsonProcessingException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        editor.apply();
    }

    public LoginResponse getUser() {
        LoginResponse loginResponse = new LoginResponse();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        String userString = sharedPreferences.getString(Const.Preferences.USER_LOGIN, "");
        try {
            loginResponse = mapper.readValue(userString, LoginResponse.class);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return loginResponse;
    }

}