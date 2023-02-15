package com.example.nutechwallet.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.nutechwallet.R;
import com.example.nutechwallet.core.singleton.SingletonLikeApp;
import com.example.nutechwallet.ui.home.HomeActivity;
import com.example.nutechwallet.ui.login.LoginActivity;

@SuppressLint("CustomSplashScreen")
public class NewtechSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            Intent intent;
            if (SingletonLikeApp.getInstance().getSharedPreferences(getApplicationContext()).getToken().isEmpty()){
                intent = new Intent(NewtechSplashActivity.this, LoginActivity.class);
            }else {
                intent = new Intent(NewtechSplashActivity.this, HomeActivity.class);
            }
            startActivity(intent);
            finish();
        }, 5000);
    }
}