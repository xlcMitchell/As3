package com.example.bit603_mitchell_travis_5080526_as3;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bit603_mitchell_travis_5080526_as3.viewModel.ChannelViewModel;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class YouTubeListActivity extends AppCompatActivity {

    private static final int REQUEST_ACCOUNT_PICKER = 1000;
    private static final int REQUEST_AUTHORIZATION = 1001;
    private GoogleAccountCredential credential;
    private String token;
    ChannelViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_list); // make sure you have this layout
        RecyclerView recyclerView = findViewById(R.id.channelRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        VideoAdapter adapter = new VideoAdapter(video -> {
            String videoId = video.getVideoId();
            Toast.makeText(this, "Id: " + videoId, Toast.LENGTH_SHORT).show();
            //TODO: start intent to watch video and pass youtube video ID with the intent
            Intent intent = new Intent(YouTubeListActivity.this,YouTubeVideoPlayerActivity.class);
            intent.putExtra("videoId",videoId);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ChannelViewModel.class);

        //Observe the LiveData
        viewModel.getVideosLiveData().observe(this, videos -> {
            if (videos != null) {
                Log.d("UpdateAdapter","Updating recyclerview");
                adapter.setVideos(videos);
            }
        });

        // Initialize the Google OAuth credential
        credential = GoogleAccountCredential.usingOAuth2(
                this,
                Collections.singletonList(YouTubeScopes.YOUTUBE_READONLY)
        );

        // Start account picker so user selects a Google account
        startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ACCOUNT_PICKER && resultCode == RESULT_OK && data != null) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            if (accountName != null) {
                credential.setSelectedAccountName(accountName);

                // Retrieve OAuth token in a background thread
                new Thread(() -> {
                    try {
                        token = credential.getToken();
                        Log.d("YouTubeListActivity", "OAuth Token: " + token);
                        viewModel.fetchChannelData(token); //send request
                    } catch (UserRecoverableAuthException e) {
                        //executes if the user needs to provide consent.. UserRecoverableAuthException e provides an intent
                        //to achieve this
                        Log.d("YouTubeListActivity", "Requesting user consent...");
                        startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
                    }
                    catch (Exception e) {
                        Log.e("YouTubeListActivity", "Error getting token: " + e.getMessage());
                    }
                }).start();
            }
        }
    }
}