package com.hackathon.android.sherlocked;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuspectIntroActivity extends AppCompatActivity {

    ImageView suspectImage;
    TextView suspectName, suspectIntro;
    int suspectId;
    Button talkToSuspect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspect_intro);

        suspectImage = findViewById(R.id.suspectImage);
        suspectName = findViewById(R.id.suspectName);
        suspectIntro = findViewById(R.id.suspectIntro);
        talkToSuspect = findViewById(R.id.talkToSuspect);

        Intent intent = getIntent();
        suspectId = intent.getIntExtra("suspectId", 0);

        if (suspectId == getResources().getInteger(R.integer.amit_id)) {
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect_1));
            suspectName.setText("Amit Patnaik");
            suspectIntro.setText("He is victim Raghu's boss.");
        }
        if (suspectId == getResources().getInteger(R.integer.mithila_id)) {
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect_2));
            suspectName.setText("Mithila Shetty");
            suspectIntro.setText("He is victim Raghu's boss.");
        }
        if (suspectId == getResources().getInteger(R.integer.vijay_id)) {
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect_3));
            suspectName.setText("Vijay Johnson Thakur");
            suspectIntro.setText("He is victim Raghu's boss.");
        }

        talkToSuspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuspectIntroActivity.this, ChatActivity.class);
                intent.putExtra("suspectId", suspectId);
                startActivity(intent);
            }
        });
    }
}
