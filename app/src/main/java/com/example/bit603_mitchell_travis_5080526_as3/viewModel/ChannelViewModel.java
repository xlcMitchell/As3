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

import java.util.HashMap;
import java.util.Map;

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
}
