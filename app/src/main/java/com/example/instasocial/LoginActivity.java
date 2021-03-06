package com.example.instasocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText userName;
    private EditText password;
    private TextView notificationText;
    private Button loginButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser()!=null){
            goMainActivity();
        }

        userName = findViewById(R.id.instaUserName);
        password = findViewById(R.id.instaPassword);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        notificationText = findViewById(R.id.notificationText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=userName.getText().toString();
                String pass=password.getText().toString();
                loginUser(username,pass);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=userName.getText().toString();
                String pass=password.getText().toString();

                if(username==null|| pass==null|| pass.length()<3){
                    String message="";
                    if(username==null){
                        message="User Name can not be empty!";
                    }
                    if(pass==null){
                        message="Password can not be empty!";
                    }
                    if(pass!=null && pass.length()<3){
                        message="Password too short!";
                    }
                    notificationText.setText(message);
                }else{
                    createUserAccount(username,pass);
                }

            }
        });

    }

    private void createUserAccount(String username, String pass) {
        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(pass);
        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    userName.setText("");
                    password.setText("");
                    notificationText.setText("You have successfully created your account!");
                }else{
                    Log.d(TAG,e.toString());
                }
            }
        });

    }

    private void loginUser(String username, String password){

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e!=null){
                    notificationText.setText("Wrong User name or password! Please use the correct credentials.");

                } else {
                    goMainActivity();
                }
            }
        });
    }

    private void goMainActivity(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}