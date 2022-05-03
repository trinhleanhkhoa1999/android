package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class Detail extends AppCompatActivity {
    StyledPlayerView playerView;
    TextView tvContent;
    Toolbar toolbar;
    ExoPlayer exoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        Chanel chanel = (Chanel)getIntent().getSerializableExtra("chanel");
        String chanel = (String)getIntent().getSerializableExtra("chanel");
        String chaneltitle = (String)getIntent().getSerializableExtra("chaneltitle");
        String chanelcontent = (String)getIntent().getSerializableExtra("chanelcontent");

        toolbar = findViewById(R.id.toolbar);
        tvContent = findViewById(R.id.tv_content);
        tvContent.setText(chanelcontent);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(chaneltitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playerView = findViewById(R.id.playerView);
        playChannel(chanel);
    }

    public  void  OpenLink(String url){
        Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(open);
    }

    public  void  playChannel(String live_url){
        exoPlayer= new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(live_url);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        exoPlayer.seekToDefaultPosition();
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onPause() {
        exoPlayer.setPlayWhenReady(false);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        exoPlayer.release();
        super.onDestroy();
    }
}