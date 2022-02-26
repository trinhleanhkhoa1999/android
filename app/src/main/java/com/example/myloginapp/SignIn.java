package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        // khai bao
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        TextView register = (TextView) findViewById(R.id.register);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        //chuc nang xac thuc login
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dang nhap bang data duoc xet truoc tk:admin mk:admin
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    Toast.makeText(SignIn.this, "LOGIN SUCCESSFULL !!!", Toast.LENGTH_SHORT).show();
                            // chuyen den trang Index
                            Intent x = new Intent();
                            x.setClass(SignIn.this,
                                    Index.class);
                            startActivity(x);
                }else
                    Toast.makeText(SignIn.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
            }
        });
        // chuyen sang trang register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();
                // tu trang signin sang trang register
                i.setClass(SignIn.this,
                        Register.class);
                // van hanh chuyen trang
                startActivity(i);
            }
        });

    }
}