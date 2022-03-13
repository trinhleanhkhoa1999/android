package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;


public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        TextView username_register = (TextView) findViewById(R.id.username_register);
        TextView password_register = (TextView) findViewById(R.id.password_register);
        TextView confirmpassword_register = (TextView) findViewById(R.id.confirmpassword_register);


        MaterialButton registerbtn = (MaterialButton) findViewById(R.id.registerbtn);
        // chuyen ve trang Signin de dang nhap
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String testusername_register = username_register.getText().toString();
                String testpassword_register = password_register.getText().toString();
                String testconfirmpassword_register = confirmpassword_register.getText().toString();

                 System.out.println(testpassword_register + "  " +"testpassword_register");

                if(username_register.getText().toString().equals("") || password_register.getText().toString().equals("") || confirmpassword_register.getText().toString().equals("")){
                    Toast.makeText(Register.this, "REGISTER REQUIRED !!!", Toast.LENGTH_SHORT).show();
                    if(TextUtils.isEmpty(testusername_register)){
                        username_register.setError("Username is required");
                    }
                    if(TextUtils.isEmpty(testpassword_register)){
                        password_register.setError("Password is required");
                    }
                    if(TextUtils.isEmpty(testconfirmpassword_register)){
                        confirmpassword_register.setError("Confirmpassword is required");
                    }
                }
                else {
                    if(testpassword_register.length() <=8 && testconfirmpassword_register.length() <=8){
                        Toast.makeText(Register.this, "password it nhat 8 ky tu", Toast.LENGTH_SHORT).show();
                    }else if(testpassword_register.equals(testconfirmpassword_register)) {
                        Toast.makeText(Register.this, "REGISTER SUCCESSFULL !!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent();
                        i.setClass(Register.this,
                                SignIn.class);
                        startActivity(i);


//                        user result = new user(testusername_register, testpassword_register);
//                        Intent returnIntent = new Intent();
//                        Bundle a = new Bundle();
//                        a.get
//                        returnIntent.putExtra("result",result);
//                        setResult(Activity.RESULT_OK,returnIntent);
//                        finish();

//                        Toast.makeText(Register.this, "ConfirmPassword phai bang voi Password", Toast.LENGTH_SHORT).show();
//                        System.out.println( testpassword_register+ "  " +"testpassword_register");
//                        System.out.println( testconfirmpassword_register+ "  " +"confirmpassword_register");

                    }else{
                        Toast.makeText(Register.this, "ConfirmPassword phai bang voi Password", Toast.LENGTH_SHORT).show();
                    };
                }
            }
        });

    }
}