package com.example.bit603_mitchell_travis_5080526_as3.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bit603_mitchell_travis_5080526_as3.Model.Channel;
import com.example.bit603_mitchell_travis_5080526_as3.Model.Video;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChannelViewModel extends ViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<Channel> channelLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Video>> videosLiveData = new MutableLiveData<>();
    public void writeToFirebase(){

    }

    public void readChannel(String channelId){
        db.collection("youtube_channels")
                .document(channelId)
                .get()
                .addOnSuccessListener(
                        new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot document) {
                                if(document.exists()){
                                    String channelId = document.getString("channelId");
                                    assert channelId != null;
                                    Log.d("FIRESTORE_READ",channelId);
                                }
                            }
                        }
                );
    }

    public void saveOrUpdateChannel(Channel channel) {
        db.collection("channels")
                .document(channel.getChannelId())
                .set(channel, SetOptions.merge())
                .addOnSuccessListener(aVoid ->
                        Log.d("Firestore", "Channel saved/updated successfully"))
                .addOnFailureListener(e ->
                        Log.e("Firestore", "Error saving channel", e));
    }

    //Send http request to YouTube for channel information
    public void fetchChannelData(String token){
        Retrofit retrofitClient = RetrofitClient.getClient();
        YouTubeChannelService service = retrofitClient.create(YouTubeChannelService.class);
        String authHeader = "Bearer " + token;
        String channelId = "UCDPM_n1atn2ijUwHd0NNRQw";
        Call<JsonObject> call = service.getChannelInfo("snippet,statistics", channelId, authHeader);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    //sucessfully recieved response to http request for youtube channel information
                    JsonObject json = response.body();
                    Log.d("YouTubeAPI", "Response JSON: " + json.toString());

                    String title = json.getAsJsonArray("items")
                            .get(0)
                            .getAsJsonObject()
                            .getAsJsonObject("snippet")
                            .get("title")
                            .getAsString();
                    Log.d("YouTubeAPI", "Channel Title: " + title);
                    parseChannelJson(json,channelId);
                } else {
                    try {
                        Log.e("YouTubeAPI", "Error: " + response.code() + " " + response.message()
                                + "\nBody: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("YouTubeAPI", "Failure: " + t.getMessage());
            }
        });
    }

    public void parseChannelJson(JsonObject json, String channelId) {
        JsonObject item = json.getAsJsonArray("items").get(0).getAsJsonObject();
        JsonObject snippet = item.getAsJsonObject("snippet");
        JsonObject statistics = item.getAsJsonObject("statistics");
        //Log channel details to make sure everything ok
        Log.d("JSON_RESPONSE","Title: " + snippet.get("title").getAsString());
        Log.d("JSON_RESPONSE","Description: " + snippet.get("description").getAsString());
        Log.d("JSON_RESPONSE","subscriberCount: " + statistics.get("subscriberCount").getAsString());
        //Create channel object
        Channel channel = new Channel(channelId,snippet.get("title").getAsString(),
                snippet.get("description").getAsString(),
                statistics.get("subscriberCount").getAsString());
                channelLiveData.setValue(channel); //update live data for channel
                saveOrUpdateChannel(channel);

    }
}
