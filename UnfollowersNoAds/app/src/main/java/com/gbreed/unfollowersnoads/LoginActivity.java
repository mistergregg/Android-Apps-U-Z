package com.gbreed.unfollowersnoads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.brunocvcunha.instagram4j.Instagram4j;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view)
    {
        EditText user = findViewById(R.id.editTextUser);
        EditText pass = findViewById(R.id.editTextPass);


        Instagram4j instagram = Instagram4j.builder().username(user.getText().toString()).password(pass.getText().toString()).build();
        instagram.setup();

        try{
            instagram.login();
        }catch (Exception e)
        {
            Toast.makeText(this, "Failed To Log In", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
