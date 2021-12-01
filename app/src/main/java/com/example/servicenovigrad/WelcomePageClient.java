package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomePageClient extends AppCompatActivity {

    TextView displayUsername;
    Button signOutButton, selectBranch;
    private String username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_client);

        displayUsername = findViewById(R.id.usernameTextView);
        signOutButton = findViewById(R.id.signOutButton);
        selectBranch = findViewById(R.id.selectBranchClient);

        //DISPLAY USERNAME
        username = getIntent().getStringExtra("USERNAME");
        email = getIntent().getStringExtra("email");
        displayUsername.setText("Welcome " + username);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(WelcomePageClient.this, "Signed out successfully", Toast.LENGTH_SHORT).show();

            }
        });

        selectBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectBranchForClient.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

    }
}