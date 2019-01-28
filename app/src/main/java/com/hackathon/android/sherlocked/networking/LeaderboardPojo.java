package com.hackathon.android.sherlocked.networking;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaderboardPojo {
    @SerializedName("code")
    public int code;
    @SerializedName("Message")
    public String message;
    @SerializedName("leaderboard")
    public List<LUserPojo> leaderboard;
    @SerializedName("rank")
    public int rank;
    @SerializedName("score")
    public int score;
}
