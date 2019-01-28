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
            suspectIntro.setText(
                    "Raghu's boss, best friend and confidant, Amit is on our radar of suspects because he was the closest to Raghu."
                    + "\n\n"
                    + "He is extremely smart and quick, ask him the right questions to find out if he had anything to do with Raghu's murder."
            );
        }
        if (suspectId == getResources().getInteger(R.integer.mithila_id)) {
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect_2));
            suspectName.setText("Mithila Shetty");
            suspectIntro.setText(
                    "A childhood sweetheart turned wife, Mithila is a suspect because her neighbors have heard her verbally abuse Raghu in the wee hours of the night."
                    + "\n\n"
                    + "She can bail out of the truth by playing the emotional card, be vary and get to the bottom of the truth, especially about her relationship with Raghu."
            );
        }
        if (suspectId == getResources().getInteger(R.integer.vijay_id)) {
            suspectImage.setImageDrawable(getResources().getDrawable(R.drawable.suspect_3));
            suspectName.setText("Vijay Johnson Thakur");
            suspectIntro.setText(
                    "Vijay clearly has a vengeance towards Raghu for his claims against himself. He is irritable, arrogant and spiteful."
                    + "\n\n"
                    +"Beware of this multi-millionaire who can rub you-off the wrong way. Prob him with the same questions more than once."
            );
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
