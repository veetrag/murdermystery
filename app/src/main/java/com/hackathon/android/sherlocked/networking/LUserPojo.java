package com.hackathon.android.sherlocked.networking;

import com.google.gson.annotations.SerializedName;

public class LUserPojo {
    @SerializedName("email")
    public String email;
    @SerializedName("name")
    public String name;
    @SerializedName("score")
    public int score;
    @SerializedName("code")
    public int code;
    @SerializedName("Message")
    public String message;
}
