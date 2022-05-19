package com.smproject.yourcityradio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.window.SplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override

            public void run() {

                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);

                startActivity(i);

                // close this activity

                finish();

            }

        }, 5000); // wait for 5 seconds


    }
}