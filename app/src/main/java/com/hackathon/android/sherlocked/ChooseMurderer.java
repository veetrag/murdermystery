package com.hackathon.android.sherlocked;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseMurderer extends AppCompatActivity implements View.OnClickListener {

    Button frameAmit, frameMithila, frameVijay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_murderer);

        frameAmit = findViewById(R.id.frameAmit);
        frameMithila = findViewById(R.id.frameMithila);
        frameVijay = findViewById(R.id.frameVijay);

        frameAmit.setOnClickListener(this);
        frameMithila.setOnClickListener(this);
        frameVijay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frameAmit:
                frameASuspect(1);
                break;
            case R.id.frameMithila:
                frameASuspect(2);
                break;
            case R.id.frameVijay:
                frameASuspect(3);
                break;
        }
    }

    void frameASuspect(int suspectId) {
        Intent i=new Intent(this,MurdererRevealActivity.class);
        i.putExtra("suspectId", suspectId);
        this.startActivity(i);

    }
}
