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

    private TextView displayUsername;
    private Button signOutButton;
    private Button viewBranchesButton;
    private Button addBranchButton;
    private Button deleteBranchButton;
    private Button viewRequestsButton;

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

        //DISPLAY USERNAME
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        displayUsername.setText("Welcome " + username);

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
            public void onClick(View v) { onViewBranches(v); }
        });

        addBranchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onAddBranch(v); }
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

    public void onViewBranches(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewBranches.class);
        startActivityForResult(intent, 0);
    }

    public void onAddBranch(View view) {
        Intent intent = new Intent(getApplicationContext(), AddBranch.class);
        startActivityForResult(intent, 0);
    }

    public void onDeleteBranch(View view) {
        Intent intent = new Intent(getApplicationContext(), DeleteBranch.class);
        startActivityForResult(intent, 0);
    }

    public void onViewRequests(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewRequests.class);
        startActivityForResult(intent, 0);
    }
}