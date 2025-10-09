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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class YouTubeListActivity extends AppCompatActivity {

    private static final int REQUEST_ACCOUNT_PICKER = 1000;
    private static final int REQUEST_AUTHORIZATION = 1001;
    private GoogleAccountCredential credential;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_list); // make sure you have this layout

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
                        String token = credential.getToken();
                        Log.d("YouTubeListActivity", "OAuth Token: " + token);
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