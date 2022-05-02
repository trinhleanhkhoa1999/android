package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class Detail extends AppCompatActivity {
    StyledPlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        Chanel chanel = (Chanel)getIntent().getSerializableExtra("chanel");
        String chanel = (String)getIntent().getSerializableExtra("chanel");

        playerView = findViewById(R.id.playerView);

        playChannel(chanel);
    }

    public  void  OpenLink(String url){
        Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(open);
    }

    public  void  playChannel(String live_url){
        ExoPlayer exoPlayer= new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(live_url);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();

    }
}