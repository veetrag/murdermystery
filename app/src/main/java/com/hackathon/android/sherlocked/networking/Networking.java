package com.hackathon.android.sherlocked.networking;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Networking {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


    public void getScore(Context context, String email, Callback<LUserPojo> callback) {
        Call<LUserPojo> call = apiInterface.getUserScore(email);
        call.enqueue(callback);
    }

    public void storeScore(String email, String name, int score) {
        Call<Void> call = apiInterface.storeUserScore(email, name, score);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
