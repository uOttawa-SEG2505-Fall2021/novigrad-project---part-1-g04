package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class welcomePage_admin extends AppCompatActivity {

    TextView displayUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_admin);

        displayUsername = findViewById(R.id.usernameTextView);

        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        displayUsername.setText("Welcome " + username);

    }
}