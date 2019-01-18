package com.javapapers.android.aichatbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



    }

    public void openWebViewActivity(View view)
    {
        startActivity(new Intent(this, TestActivity.class));

    }

    public void openVijayActivity(View view)
    {
        startActivity(new Intent(this, ChatBotVijayActivity.class));

    }

    public void openAmitActivity(View view)
    {
        startActivity(new Intent(this, ChatBotAmitActivity.class));

    }

    public void openMithilaActivity(View view)
    {
        startActivity(new Intent(this, ChatBotMithilaActivity.class));

    }

    public void openRamuActivity(View view)
    {
        startActivity(new Intent(this, ChatBotRamuActivity.class));

    }



}
