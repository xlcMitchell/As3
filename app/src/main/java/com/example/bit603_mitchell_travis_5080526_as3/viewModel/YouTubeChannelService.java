package com.example.bit603_mitchell_travis_5080526_as3.viewModel;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface YouTubeChannelService {

    @GET("channels")
    Call<JsonObject> getChannelInfo(
            @Query("part") String part,  //which details are required with request
            @Query("id") String userId, //channel username
            @Header("Authorization") String authHeader //user's oauth token
    );
}