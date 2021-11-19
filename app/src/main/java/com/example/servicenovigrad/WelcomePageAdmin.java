package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomePageAdmin extends AppCompatActivity {

    TextView displayUsername;
    Button signOutButton, serviceButton, usersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_admin);

        displayUsername = findViewById(R.id.usernameTextView);
        signOutButton = findViewById(R.id.signOutButton);
        serviceButton = findViewById(R.id.servicesButton);
        usersButton = findViewById(R.id.usersButton);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        displayUsername.setText("Welcome "+ "Admin");

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                onSignOut(v);
            }
        });

        serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                onServicePage(v);
            }
        });

        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                onUserListPage(v);
            }
        });

    }

    public void onSignOut(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void onServicePage(View view) {
        Intent intent = new Intent(getApplicationContext(), ServicePage.class);
        startActivityForResult(intent, 0);
    }

    public void onUserListPage(View view) {
        Intent intent = new Intent(getApplicationContext(), UserList.class);
        startActivityForResult(intent, 0);
    }

}