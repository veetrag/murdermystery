package com.hackathon.android.sherlocked;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstIntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_intro);
    }

    public void moveNext(View view)
    {
        startActivity(new Intent(this, SecondIntroActivity.class));
    }
}
