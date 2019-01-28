package com.hackathon.android.sherlocked.networking;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("sherlocked/data/get/leaderboardgetter.php?email=b@1.com")
    Call<LeaderboardPojo> doGetListResources();

    @GET("sherlocked/data/get/userscoregetter.php?")
    Call<LUserPojo> getUserScore(@Query("email") String email);

    @GET("sherlocked/data/set/userscorestore.php?")
    Call<Void> storeUserScore(@Query("email") String email, @Query("name") String name, @Query("score") int score);

}