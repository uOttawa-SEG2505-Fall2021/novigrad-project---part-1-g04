package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ConnectToBranch extends AppCompatActivity {

    TextView displayBranchName;
    Button connectButton, goBackButton;
    EditText phoneNumberToConnect;

    private String branchName;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_branch);

        connectButton = findViewById(R.id.connectButton);
        goBackButton = findViewById(R.id.goBackButton);
        displayBranchName = findViewById(R.id.displayBranchName);
        phoneNumberToConnect = findViewById(R.id.phoneNumberToConnect);

        branchName = getIntent().getStringExtra("branchName");

        //GO BACK TO VIEW BRANCHES AFTER CLICKING "GO BACK"
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberToConnect.getText().toString().trim();

                if (!phoneNumber.isEmpty()) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Branches");
                    databaseReference.child(Objects.requireNonNull(branchName)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (Objects.requireNonNull(task.getResult()).exists()) {
                                    DataSnapshot dataSnapshot = task.getResult();
                                    String confirmPhoneNumber = String.valueOf(dataSnapshot.child("phoneNumber").getValue());

                                    if (confirmPhoneNumber.equals(phoneNumber)) {
                                        Toast.makeText(ConnectToBranch.this, "Connected to branch successfully.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConnectToBranch.this, ModifyBranch.class);
                                        intent.putExtra("branchName", branchName);
                                        startActivity(intent);
                                    } else {
                                        phoneNumberToConnect.setError("Phone number is incorrect.");
                                    }
                                }
                            }
                        }
                    });
                } else {
                    phoneNumberToConnect.setError("Please enter phone number of branch to connect.");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        displayBranchName.setText(branchName);
    }
}