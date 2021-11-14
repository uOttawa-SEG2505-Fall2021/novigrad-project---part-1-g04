package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

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