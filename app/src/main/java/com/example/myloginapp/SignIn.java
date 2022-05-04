package com.example.myloginapp;

import static android.content.ContentValues.TAG;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myloginapp.api.LoginApi;
import com.example.myloginapp.broadcast.BroadcastInternet;
import com.google.android.material.button.MaterialButton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    private BroadcastInternet broadcastInternet;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String TOKEN = "token";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        // khai bao
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        TextView register = (TextView) findViewById(R.id.register);
        TextView watch = (TextView) findViewById(R.id.watch);

//        chuc nang xac thuc login
//        loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //dang nhap bang data duoc xet truoc tk:admin mk:admin
//                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
//                    Toast.makeText(SignIn.this, "LOGIN SUCCESSFULL !!!", Toast.LENGTH_SHORT).show();
//                            // chuyen den trang Index
//                            Intent x = new Intent();
//                            x.setClass(SignIn.this,
//                                    Index.class);
//                            startActivity(x);
//                }else
//                    Toast.makeText(SignIn.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
//
//                String testUsername = username.getText().toString();
//                String testPass = password.getText().toString();
//                if(TextUtils.isEmpty(testUsername)){
//                    username.setError("Username is required");
//                }
//                if(TextUtils.isEmpty(testPass)){
//                    password.setError("Password is required");
//                }
//            }
//        });


        broadcastInternet = new BroadcastInternet();

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
//

         loginbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String username1 = username.getText().toString();
                 String password1 = password.getText().toString();
                 LoginRequest loginRequest = new LoginRequest(username1,password1);
                 Call<ReponseLogin> call = LoginApi.loginApi.login(loginRequest);
                 call.enqueue(new Callback<ReponseLogin>() {
                     @Override
                     public void onResponse(Call<ReponseLogin> call, Response<ReponseLogin> response) {
                         if(response.isSuccessful() && response.body() !=null ){
                             ReponseLogin reponseLogin = response.body();
                             String token = reponseLogin.getToken().toString();
                             Intent intent = new Intent(SignIn.this,Index.class);
                             startActivity(intent);
                             editor = sharedPreferences.edit();
                             editor.putString(TOKEN, token);
                             editor.commit();
                         }
                     }

                     @Override
                     public void onFailure(Call<ReponseLogin> call, Throwable t) {

                     }
                 });

             }
         });
        // chuyen sang trang register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Register = 1;
                Intent i = new Intent();
                // tu trang signin sang trang register
                i.setClass(SignIn.this,
                        Register.class);
                // van hanh chuyen trang
                startActivityForResult(i, Register );
            }
        });

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Index.class);
                startActivity(intent);
            }
        });



    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1) {
//            if(resultCode == Activity.RESULT_OK){
//                String result=data.getStringExtra("result");
//            }
//
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastInternet,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastInternet);
    }

}