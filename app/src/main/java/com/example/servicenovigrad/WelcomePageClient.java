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
    Button signOutBtn, selectBranchBtn, selectServiceBtn, selectTimeBtn, selectAddressBtn, rateBranchBtn;
    private String username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_client);

        displayUsername = findViewById(R.id.usernameTextView);
        signOutBtn = findViewById(R.id.signOutButton);
        selectBranchBtn = findViewById(R.id.selectBranchClient);
        selectServiceBtn = findViewById(R.id.viewAllServices_button);
        selectTimeBtn = findViewById(R.id.searchByTime_button);
        selectAddressBtn = findViewById(R.id.searchByAddress);
        rateBranchBtn = findViewById(R.id.rateBranch);

        //DISPLAY USERNAME
        username = getIntent().getStringExtra("USERNAME");
        email = getIntent().getStringExtra("email");
        displayUsername.setText("Welcome " + username);

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(WelcomePageClient.this, "Signed out successfully.", Toast.LENGTH_SHORT).show();

            }
        });

        selectBranchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewAllBranchesForClient.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        selectServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewAllServicesForClient.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

    }
}