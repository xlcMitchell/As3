package com.example.bit603_mitchell_travis_5080526_as3.viewModel;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface YouTubeUploadListService {

      @GET("playlistItems")
        Call<JsonObject> getPlaylistVideos(
                @Query("part") String part,
                @Query("playlistId") String playlistId,
                @Query("maxResults") int maxResults,
                @Header("Authorization") String authHeader
        );

}
