package com.hackathon.android.sherlocked;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveNext();
            }
        }, 3500);

    }


    public void moveNext()
    {
        startActivity(new Intent(this, FirstIntroActivity.class));

    }

}
