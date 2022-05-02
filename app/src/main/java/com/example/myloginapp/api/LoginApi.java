package com.example.myloginapp.api;

import com.example.myloginapp.LoginRequest;
import com.example.myloginapp.RegisterResponse;
import com.example.myloginapp.ReponseLogin;
import com.example.myloginapp.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginApi {

    // URL = https://serene-savannah-52454.herokuapp.com/
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    LoginApi loginApi = new Retrofit.Builder()
            .baseUrl("https://serene-savannah-52454.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(LoginApi.class);

    @POST("users/login")
    Call<ReponseLogin> login(@Body LoginRequest loginRequest);

    @GET()
    Call<ResponseBody> get(@Header("Authorization") String authToken);

//    @FormUrlEncoded
//    @POST("users/register")
//    Call<RegisterResponse> register(
//            @Field("username") String username,
//            @Field("password") String password
//    );

    @POST("users/register")
    Call<RegisterResponse> register(@Body LoginRequest loginRequest);

}
