package com.javapapers.android.aichatbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MurdererRevealActivity extends AppCompatActivity {


    int suspectId = 0;
    TextView heading, message;
    ImageView suspectImage;
    LinearLayout murderRevealLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_murderer_reveal);

        Intent intent = getIntent();
        suspectId = intent.getIntExtra("suspectId", 0);

        murderRevealLayout = findViewById(R.id.murderRevealLayout);
        heading = findViewById(R.id.heading);
        message = findViewById(R.id.message);
        suspectImage = findViewById(R.id.suspectImage);

        if (suspectId == 1) {
            heading.setText("Right\nyou are!");
            // TODO: get user name from shared preferences and show.
            message.setText("Well done, Anusha!\nYou have cracked this!");
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect_1));
        }
        if (suspectId == 2) {
            murderRevealLayout.setBackgroundColor(getResources().getColor(R.color.red));
            heading.setText("You are\nWrong!");
            heading.setTextColor(getResources().getColor(R.color.white));
            // TODO: get user name from shared preferences and show.
            message.setText("You have wrongly accused an innocent suspect! Worst ye");
            message.setTextColor(getResources().getColor(R.color.white));
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect_2));
        }
        if (suspectId == 3) {
            murderRevealLayout.setBackgroundColor(getResources().getColor(R.color.red));
            heading.setText("You are\nWrong!");
            heading.setTextColor(getResources().getColor(R.color.white));
            // TODO: get user name from shared preferences and show.
            message.setText("You are in trouble.\nYou have accused a tycoon who had nothing to do with this murder!");
            message.setTextColor(getResources().getColor(R.color.white));
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect_3));
        }

    }
}
