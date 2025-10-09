package com.example.bit603_mitchell_travis_5080526_as3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bit603_mitchell_travis_5080526_as3.viewModel.ChannelViewModel;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ChannelViewModel channelInsert = new ChannelViewModel();
        channelInsert.writeToFirebase();
        channelInsert.readChannel("oNATRW73xJS9frychNr7");

        Button playerBtn = findViewById(R.id.watchVideo);
        playerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,YouTubePlayerActivity.class);
                startActivity(intent);
            }
        });

        Button youTubeListBtn = findViewById(R.id.listYouTubeChannel);
        youTubeListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,YouTubeListActivity.class);
               startActivity(intent);
            }
        });

    }
}