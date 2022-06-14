package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myloginapp.api.ApiService;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail extends AppCompatActivity {
    StyledPlayerView playerView;
    TextView tvContent;
    Toolbar toolbar;
    ExoPlayer exoPlayer;
    ToggleButton toggle;
    Boolean checktoogle ;
    String token;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        Chanel chanel = (Chanel)getIntent().getSerializableExtra("chanel");
        String chanel = (String)getIntent().getSerializableExtra("chanel");
        String chanel_id = (String)getIntent().getSerializableExtra("chanel_id");
        String chaneltitle = (String)getIntent().getSerializableExtra("chaneltitle");
        String chanelcontent = (String)getIntent().getSerializableExtra("chanelcontent");
        Boolean check = (Boolean)getIntent().getSerializableExtra("check");
        checktoogle = false;
        checktoogle = check;




        toggle = findViewById(R.id.btnToggle);

        toggle.setChecked(check);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    addFavorite(chanel_id);
                }else {
                    deleteFavorite(chanel_id);
                }
            }
        });

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


    private void addFavorite(String s){
        sharedPreferences = this.getSharedPreferences("token", this.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        if(token!=null){
            ChanelRequest chanelRequest = new ChanelRequest(s);
            ApiService.apiService.AddFavorite(token,chanelRequest).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(), "Add to Favorite",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    private void deleteFavorite(String s){
        sharedPreferences = this.getSharedPreferences("token", this.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        if(token!=null){
            ChanelRequest chanelRequest = new ChanelRequest(s);
            ApiService.apiService.deleteFavoriteChanel(token,chanelRequest).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(), "Deleted from Favorite",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }
}