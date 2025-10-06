package com.example.bit603_mitchell_travis_5080526_as3.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChannelViewModel extends ViewModel {
    public void writeToFirebase(){
        // Connecting with the Firebase Realtime Database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //get the datbase channel collection
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
    }
}
