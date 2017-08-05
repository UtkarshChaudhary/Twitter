package com.example.lenovo.twitterapna2;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 02-08-2017.
 */

public interface ApiInterface {

 @GET("statuses/home_timeline.json")
    Call<ArrayList<Tweet>> getUserTimline();
    //Call<ArrayList<Home>> getUserTimeline(@Header("Authorization") String header);

    @GET("trends/place.json")
    Call<ArrayList<Home>> getUserTrends(@Query("id") String id);

    @GET("users/show.json")
    Call<Profile> getUserProfile(@Query("screen_name") String screen_name);

     @GET("search/tweets.json")
     Call<ArrayList<Tweet>> getSearchTweet(@Query("q") String query);

}
