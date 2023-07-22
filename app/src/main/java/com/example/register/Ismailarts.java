package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class Ismailarts extends AppCompatActivity {
    int count=0 ;
    Timer timer;
    Handler handle= new Handler();
    public static int SPLASH_TIME_OUT=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ismailarts);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                timer = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {


                        count++;


                        if (count == 30) {
                            startActivity(new Intent(Ismailarts.this, Home.class));
                            finish();
                        }

                    }
                };
                timer.schedule(tt, 0, 50);


            }
        },SPLASH_TIME_OUT);
    }
}

