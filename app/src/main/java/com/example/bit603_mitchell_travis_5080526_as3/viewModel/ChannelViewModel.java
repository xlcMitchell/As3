package com.example.bit603_mitchell_travis_5080526_as3.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChannelViewModel extends ViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void writeToFirebase(){
        // Connecting with the Firebase Realtime Database

        /*
        Map<String, Object> channel = new HashMap<>();
        channel.put("channelId", "UC_xxx");
        channel.put("channelName", "My Channel");

        db.collection("youtube_channels").add(channel)
                .addOnSuccessListener(
                        new OnSuccessListener<DocumentReference>() {

                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("FireStoreSuccess", "Document added with ID: " + documentReference.getId());
                            }
                        }
                )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FireStoreError", "cannot add data in FireStore");
                    }
                });


         */
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

    public void fetchChannelData(String token){
        Retrofit retrofitClient = RetrofitClient.getClient();
        YouTubeChannelService service = retrofitClient.create(YouTubeChannelService.class);
        String authHeader = "Bearer " + token;
        String channelId = "UCn9ZHYVdOBqAkBQ7H7rjnQA";
        Call<JsonObject> call = service.getChannelInfo("snippet,statistics", channelId, authHeader);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject json = response.body();
                    Log.d("YouTubeAPI", "Response JSON: " + json.toString());

                    String title = json.getAsJsonArray("items")
                            .get(0)
                            .getAsJsonObject()
                            .getAsJsonObject("snippet")
                            .get("title")
                            .getAsString();
                    Log.d("YouTubeAPI", "Channel Title: " + title);
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
}
