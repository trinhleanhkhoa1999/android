package com.example.myloginapp.api;

import com.example.myloginapp.Chanel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    // URL = https://serene-savannah-52454.herokuapp.com/
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://serene-savannah-52454.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("chanels")
    Call<List<Chanel>> GetChanels();

    @GET("chanels/{id}")
    Call<ResponseBody> getChanelswithId(@Path("id") String id, @Header("Authorization") String token);

}
