package com.hackathon.android.sherlocked;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hackathon.android.sherlocked.util.Preferences;

public class MurdererRevealActivity extends AppCompatActivity {


    int suspectId = 0;
    TextView heading, message;
    ImageView suspectImage, suspectStamp;
    LinearLayout murderRevealLayout;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String email, name, profileImage;
    Button playAgain, showLeaderboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_murderer_reveal);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        email = pref.getString(Preferences.EMAIL, "");
        name = pref.getString(Preferences.NAME, "");
        profileImage = pref.getString(Preferences.PROFILE_IMAGE, "");


        showLeaderboard = findViewById(R.id.showLeaderboard);
        playAgain = findViewById(R.id.playAgain);

        Intent intent = getIntent();
        suspectId = intent.getIntExtra("suspectId", 0);

        murderRevealLayout = findViewById(R.id.murderRevealLayout);
        heading = findViewById(R.id.heading);
        message = findViewById(R.id.message);
        suspectImage = findViewById(R.id.suspectImage);
        suspectStamp = findViewById(R.id.suspectStamp);

        if (suspectId == 1) {
            heading.setText("YOU\nGOT HIM!");
            // TODO: get user name from shared preferences and show.
            message.setText("Well done, "+name+"!\nYou have cracked this!");
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect1_jail));
            suspectStamp.setImageDrawable(getDrawable(R.drawable.murderer));
        }
        if (suspectId == 2) {
            murderRevealLayout.setBackgroundColor(getResources().getColor(R.color.red));
            heading.setText("You are\nWrong!");
            heading.setTextColor(getResources().getColor(R.color.white));
            // TODO: get user name from shared preferences and show.
            message.setText("You have wrongly accused an innocent suspect!");
            message.setTextColor(getResources().getColor(R.color.white));
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect2_jail));
            suspectStamp.setImageDrawable(getDrawable(R.drawable.innocent));
            playAgain.setText("Try Again");
        }
        if (suspectId == 3) {
            murderRevealLayout.setBackgroundColor(getResources().getColor(R.color.red));
            heading.setText("You are\nWrong!");
            heading.setTextColor(getResources().getColor(R.color.white));
            // TODO: get user name from shared preferences and show.
            message.setText("You are in trouble.\nYou have accused a tycoon who had nothing to do with this murder!");
            message.setTextColor(getResources().getColor(R.color.white));
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect3_jail));
            suspectStamp.setImageDrawable(getDrawable(R.drawable.innocent));
            playAgain.setText("Try Again");
        }


        showLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MurdererRevealActivity.this, LeaderboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
