package com.hackathon.android.sherlocked;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button talkToAmit, talkToMithila, talkToVijay, chooseMurderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        talkToAmit = findViewById(R.id.talkToAmit);
        talkToMithila = findViewById(R.id.talkToMithila);
        talkToVijay = findViewById(R.id.talkToVijay);
        chooseMurderer = findViewById(R.id.chooseMurderer);

        talkToAmit.setOnClickListener(this);
        talkToMithila.setOnClickListener(this);
        talkToVijay.setOnClickListener(this);
        chooseMurderer.setOnClickListener(this);

    }
    public void openVijayActivity()
    {
        startActivity(new Intent(this, ChatBotVijayActivity.class));

    }
    public void openAmitActivity()
    {
        startActivity(new Intent(this, ChatBotAmitActivity.class));

    }
    public void openMithilaActivity()
    {
        startActivity(new Intent(this, ChatBotMithilaActivity.class));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.talkToAmit:
                openAmitActivity();
                break;
            case R.id.talkToMithila:
                openMithilaActivity();
                break;
            case R.id.talkToVijay:
                openVijayActivity();
                break;
            case R.id.chooseMurderer:
                Intent intent = new Intent(HomeActivity.this, ChooseMurderer.class);
                startActivity(intent);
                break;

        }

    }
}
