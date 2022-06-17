package com.example.myloginapp;

import static android.content.ContentValues.TAG;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myloginapp.api.LoginApi;
import com.example.myloginapp.broadcast.BroadcastInternet;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    private static final int RC_SIGN_IN =1000 ;
    private BroadcastInternet broadcastInternet;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String TOKEN = "token";

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    ImageView iv_google;




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

        //google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

//        iv_google = findViewById(R.id.iv_google);
//        iv_google.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SignIn();
//                Toast.makeText(SignIn.this, "LOGIN SUCCESSFULL !!!", Toast.LENGTH_SHORT).show();
//            }
//        });



//
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);

//
//        GoogleSignIn.silentSignIn()
//                .addOnCompleteListener(
//                        this,
//                        new OnCompleteListener<GoogleSignInAccount>() {
//                            @Override
//                            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
//                                handleSignInResult(task);
//                            }
//                        });


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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(GoogleSignInAccount account) {

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


    private void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(SignIn.this,Index.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();

            // TODO(developer): send ID Token to server and validate

            updateUI(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void startIndex() {
        finish();
        Intent intent = new Intent(SignIn.this, Index.class);
        startActivity(intent);
    }


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
}