package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomePageAdmin extends AppCompatActivity {

    TextView displayUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_admin);

        displayUsername = findViewById(R.id.usernameTextView);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        displayUsername.setText("Welcome "+ username);


    }
}