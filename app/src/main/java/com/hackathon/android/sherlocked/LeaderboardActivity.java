package com.hackathon.android.sherlocked;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hackathon.android.sherlocked.networking.APIClient;
import com.hackathon.android.sherlocked.networking.APIInterface;
import com.hackathon.android.sherlocked.networking.LUserPojo;
import com.hackathon.android.sherlocked.networking.LeaderboardPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardActivity extends AppCompatActivity {

    APIInterface apiInterface;
    LinearLayout leaderboardView;
    GradientDrawable border, borderHeading, borderHighlight;
    TextView userScore, userRank;

    LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
    );
    LinearLayout.LayoutParams cellParam1 = new LinearLayout.LayoutParams(
            100, ViewGroup.LayoutParams.MATCH_PARENT
    );
    LinearLayout.LayoutParams cellParam2 = new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.MATCH_PARENT, 0.8f
    );
    LinearLayout.LayoutParams cellParam3 = new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.MATCH_PARENT, 0.2f
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        leaderboardView = findViewById(R.id.leaderboardView);
        userScore = findViewById(R.id.userScore);
        userRank = findViewById(R.id.userRank);

        border = new GradientDrawable();
        border.setColor(0xFFFFFFFF); //white background
        border.setStroke(1, 0xFFD4D4D4); //black border with full opacity

        borderHeading = new GradientDrawable();
        borderHeading.setColor(0xFFEEEEEE); //white background
        borderHeading.setStroke(1, 0xFFD4D4D4); //black border with full opacity

        borderHighlight = new GradientDrawable();
        borderHighlight.setColor(getResources().getColor(R.color.background)); //white background
        borderHighlight.setStroke(1, 0xFFD4D4D4); //black border with full opacity


        Call<LeaderboardPojo> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<LeaderboardPojo>() {
            @Override
            public void onResponse(Call<LeaderboardPojo> call, Response<LeaderboardPojo> response) {


                String displayResponse = "";

                LeaderboardPojo resource = response.body();
                List<LUserPojo> leaderList = resource.leaderboard;
                int uRank = resource.rank;
                int uScore = resource.score;

                userRank.setText(uRank+"");
                userScore.setText(uScore+"");

                LinearLayout llHeading = getLeaderboardTableHeading();
                leaderboardView.addView(llHeading);

                int rank=1;
                for (LUserPojo datum : leaderList) {
                    displayResponse += datum.email + " " + datum.name + " " + datum.score + "\n";

                    LinearLayout ll = new LinearLayout(LeaderboardActivity.this);
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    ll.setLayoutParams(rowParams);

                    TextView ctv1 = new TextView(LeaderboardActivity.this);
                    ctv1.setText(rank+"");
                    ctv1.setTypeface(Typeface.create("space_mono", Typeface.NORMAL));
                    ctv1.setLayoutParams(cellParam1);
                    ctv1.setPadding(20, 20, 10, 20);
                    ctv1.setGravity(Gravity.CENTER);
                    if (uRank == rank){
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            ctv1.setBackgroundDrawable(borderHighlight);
                        } else {
                            ctv1.setBackground(borderHighlight);
                        }
                        ctv1.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            ctv1.setBackgroundDrawable(border);
                        } else {
                            ctv1.setBackground(border);
                        }
                    }

                    TextView ctv2 = new TextView(LeaderboardActivity.this);
                    ctv2.setText(datum.name);
                    ctv2.setTypeface(Typeface.create("space_mono", Typeface.NORMAL));
                    ctv2.setLayoutParams(cellParam2);
                    ctv2.setPadding(20, 20, 10, 20);
                    ctv2.setGravity(Gravity.CENTER_VERTICAL);
                    if (uRank == rank){
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            ctv2.setBackgroundDrawable(borderHighlight);
                        } else {
                            ctv2.setBackground(borderHighlight);
                        }
                        ctv2.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            ctv2.setBackgroundDrawable(border);
                        } else {
                            ctv2.setBackground(border);
                        }
                    }

                    TextView ctv3 = new TextView(LeaderboardActivity.this);
                    ctv3.setText(datum.score+"");
                    ctv3.setTypeface(Typeface.create("space_mono", Typeface.NORMAL));
                    ctv3.setLayoutParams(cellParam3);
                    ctv3.setPadding(20, 20, 10, 20);
                    ctv3.setGravity(Gravity.CENTER_VERTICAL);
                    if (uRank == rank){
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            ctv3.setBackgroundDrawable(borderHighlight);
                        } else {
                            ctv3.setBackground(borderHighlight);
                        }
                        ctv3.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            ctv3.setBackgroundDrawable(border);
                        } else {
                            ctv3.setBackground(border);
                        }
                    }

                    ll.addView(ctv1);
                    ll.addView(ctv2);
                    ll.addView(ctv3);

                    leaderboardView.addView(ll);

                    rank++;
                }

            }

            @Override
            public void onFailure(Call<LeaderboardPojo> call, Throwable t) {
                call.cancel();
            }
        });

    }

    LinearLayout getLeaderboardTableHeading() {
        LinearLayout ll = new LinearLayout(LeaderboardActivity.this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setBackgroundColor(getResources().getColor(R.color.white));
        ll.setLayoutParams(rowParams);

        TextView ctv1 = new TextView(LeaderboardActivity.this);
        ctv1.setText("Rank");
        ctv1.setTypeface(Typeface.create("space_mono", Typeface.BOLD));
        ctv1.setLayoutParams(cellParam1);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            ctv1.setBackgroundDrawable(borderHeading);
        } else {
            ctv1.setBackground(borderHeading);
        }
        ctv1.setPadding(20, 20, 10, 20);
        ctv1.setGravity(Gravity.CENTER);

        TextView ctv2 = new TextView(LeaderboardActivity.this);
        ctv2.setText("Name");
        ctv2.setTypeface(Typeface.create("space_mono", Typeface.BOLD));
        ctv2.setLayoutParams(cellParam2);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            ctv2.setBackgroundDrawable(borderHeading);
        } else {
            ctv2.setBackground(borderHeading);
        }
        ctv2.setPadding(20, 20, 10, 20);
        ctv2.setGravity(Gravity.CENTER_VERTICAL);

        TextView ctv3 = new TextView(LeaderboardActivity.this);
        ctv3.setText("Score");
        ctv3.setTypeface(Typeface.create("space_mono", Typeface.BOLD));
        ctv3.setLayoutParams(cellParam3);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            ctv3.setBackgroundDrawable(borderHeading);
        } else {
            ctv3.setBackground(borderHeading);
        }
        ctv3.setPadding(20, 20, 10, 20);
        ctv3.setGravity(Gravity.CENTER_VERTICAL);

        ll.addView(ctv1);
        ll.addView(ctv2);
        ll.addView(ctv3);

        return ll;
    }
}
