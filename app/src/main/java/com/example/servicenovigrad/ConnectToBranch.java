package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConnectToBranch extends AppCompatActivity {

    TextView displayBranchName;
    Button modifyButton, goBackButton;

    private String branchName;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_branch);

        modifyButton = findViewById(R.id.modifyButton);
        goBackButton = findViewById(R.id.goBackButton);
        displayBranchName = findViewById(R.id.displayBranchName);

        branchName = getIntent().getStringExtra("branchName");
        databaseReference = FirebaseDatabase.getInstance().getReference("Branches").child(branchName);



        //GO BACK TO VIEW BRANCHES AFTER CLICKING "GO BACK"
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onModify(v);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        displayBranchName.setText(branchName);
    }

    public void onModify(View view) {
        Intent intent = new Intent(getApplicationContext(), ModifyBranch.class);
        startActivityForResult(intent, 0);
    }
}