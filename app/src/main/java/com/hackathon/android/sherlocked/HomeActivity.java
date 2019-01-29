package com.hackathon.android.sherlocked;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hackathon.android.sherlocked.networking.LUserPojo;
import com.hackathon.android.sherlocked.networking.Networking;
import com.hackathon.android.sherlocked.util.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button talkToAmit, talkToMithila, talkToVijay, chooseMurderer, chooseMurdererDisabled;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String email, name, profileImage;
    TextView scoreText;
    @Override
    protected void onStart() {
        super.onStart();
        setButtonVisibility();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        email = pref.getString(Preferences.EMAIL, "");
        name = pref.getString(Preferences.NAME, "");
        profileImage = pref.getString(Preferences.PROFILE_IMAGE, "");

        talkToAmit = findViewById(R.id.talkToAmit);
        talkToMithila = findViewById(R.id.talkToMithila);
        talkToVijay = findViewById(R.id.talkToVijay);
        chooseMurderer = findViewById(R.id.chooseMurderer);
        chooseMurdererDisabled = findViewById(R.id.chooseMurdererDisabled);
        scoreText = findViewById(R.id.scoreText);

        talkToAmit.setOnClickListener(this);
        talkToMithila.setOnClickListener(this);
        talkToVijay.setOnClickListener(this);
        chooseMurderer.setOnClickListener(this);

        Callback<LUserPojo> responseCallback = new Callback<LUserPojo>() {
            @Override
            public void onResponse(Call<LUserPojo> call, Response<LUserPojo> response) {
                LUserPojo resource = response.body();
                String email = resource.email;
                String name = resource.name;
                final int score = resource.score;

                editor.putInt(Preferences.USER_SCORE, score);
                editor.commit();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setButtonVisibility();
                    }
                });
            }

            @Override
            public void onFailure(Call<LUserPojo> call, Throwable t) {
                call.cancel();
            }
        };

        new Networking().getScore(this, email, responseCallback);

    }
    public void openVijayActivity()
    {
        Intent intent = new Intent(this, SuspectIntroActivity.class);
        intent.putExtra("suspectId", getResources().getInteger(R.integer.vijay_id));
        startActivity(intent);
    }
    public void openAmitActivity()
    {
        Intent intent = new Intent(this, SuspectIntroActivity.class);
        intent.putExtra("suspectId", getResources().getInteger(R.integer.amit_id));
        startActivity(intent);

    }
    public void openMithilaActivity()
    {
        Intent intent = new Intent(this, SuspectIntroActivity.class);
        intent.putExtra("suspectId", getResources().getInteger(R.integer.mithila_id));
        startActivity(intent);
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

    void setButtonVisibility() {
        int score = pref.getInt(Preferences.USER_SCORE, 0);
        scoreText.setText(score+"");
        if (score >= 100) {
            chooseMurderer.setVisibility(View.VISIBLE);
            chooseMurdererDisabled.setVisibility(View.GONE);
        } else {
            chooseMurderer.setVisibility(View.GONE);
            chooseMurdererDisabled.setVisibility(View.VISIBLE);
        }
    }
}
