package com.example.myloginapp.api;

import androidx.room.Delete;

import com.example.myloginapp.Chanel;
import com.example.myloginapp.ChanelRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


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

    @POST("chanels/top")
    Call<List<Chanel>> GetFavorite();

    @GET("users/history")
    Call<ResponseBody> GetHistory( @Header("Authorization") String token);

    @POST("users/history")
    Call<ResponseBody> AddHistory(@Header("Authorization") String token, @Body ChanelRequest chanelRequest);

/*    @DELETE("users/history")
    Call<ResponseBody> DelHistory( @Header("Authorization") String token, @Body ChanelRequest chanelRequest);*/

    @GET("users/favorites")
    Call<ResponseBody> GetFavorite2( @Header("Authorization") String token);

    @POST("users/favorites")
    Call<ResponseBody> AddFavorite( @Header("Authorization") String token,@Body ChanelRequest chanelRequest);

/*    @DELETE("users/favorites")
    Call<ResponseBody> DelFavorite(@Header("Authorization") String token,@Body ChanelRequest chanelRequest);*/

    @HTTP(method = "DELETE", path = "users/history", hasBody = true)
    Call<ResponseBody> deleteHistory(@Header("Authorization") String token,@Body ChanelRequest chanelRequest);

    @HTTP(method = "DELETE", path = "users/favorites", hasBody = true)
    Call<ResponseBody> deleteFavoriteChanel(@Header("Authorization") String token,@Body ChanelRequest chanelRequest);

}
