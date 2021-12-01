package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomePageEmployee extends AppCompatActivity {

    TextView displayUsername;
    Button signOutButton, viewBranchesButton, addBranchButton, deleteBranchButton, viewRequestsButton;

    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_employee);

        displayUsername = findViewById(R.id.usernameTextView);
        signOutButton = findViewById(R.id.signOutButton);
        viewBranchesButton = findViewById(R.id.viewBranchesButton);
        addBranchButton = findViewById(R.id.addBranchButton);
        deleteBranchButton = findViewById(R.id.deleteBranchButton);
        viewRequestsButton = findViewById(R.id.viewRequestsButton);


        //TODO fix bug where if you got to a new activity, then go back, employee name is NULL
        username = getIntent().getStringExtra("USERNAME");

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 0);
                Toast.makeText(WelcomePageEmployee.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        viewBranchesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePageEmployee.this, ViewBranches.class);
                startActivity(intent);
            }
        });

        addBranchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePageEmployee.this, AddBranch.class);
                startActivity(intent);

            }
        });

        deleteBranchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onDeleteBranch(v); }
        });

        viewRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onViewRequests(v); }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        displayUsername.setText("Welcome " + username);
    }

    public void onAddBranch(View view) {
        Intent intent = new Intent(getApplicationContext(), AddBranch.class);
        startActivityForResult(intent, 0);
    }

    public void onDeleteBranch(View view) {
        Intent intent = new Intent(getApplicationContext(), DeleteBranch.class);
        startActivity(intent);
    }

    public void onViewRequests(View view) {
        Intent intent = new Intent(getApplicationContext(), ConnectToViewRequest.class);
        startActivity(intent);
    }
}