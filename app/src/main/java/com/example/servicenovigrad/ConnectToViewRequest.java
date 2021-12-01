package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConnectToViewRequest extends AppCompatActivity {

    private Button connectBtn, goBackBtn;
    private EditText phoneNumberToConnect;
    private DatabaseReference databaseReference;
    private String branchName;
    private boolean found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_view_request);

        connectBtn = findViewById(R.id.connectForRequestButton);
        goBackBtn = findViewById(R.id.goBackButton);
        phoneNumberToConnect = findViewById(R.id.phoneNumberToConnectForRequest);

        //GO BACK TO VIEW BRANCHES AFTER CLICKING "GO BACK"
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberToConnect.getText().toString().trim();
                if (!phoneNumber.isEmpty()) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Branches");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            found = false;
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Branch branch = dataSnapshot.getValue(Branch.class);
                                //Get phone number of current dataSnapshot
                                String confirmPhoneNumber = branch.getPhoneNumber();
                                if(confirmPhoneNumber.equals(phoneNumber)) {
                                    branchName = branch.getBranchName();
                                    found = true;

                                    Intent intent = new Intent(getApplicationContext(), ViewRequests.class);
                                    intent.putExtra("branchName", branchName);
                                    startActivity(intent);
                                }
                            }

                            if(!found) {
                                phoneNumberToConnect.setError("This phone number is not associated with a branch.");
                                phoneNumberToConnect.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    phoneNumberToConnect.setError("Please enter a phone number.");
                    phoneNumberToConnect.requestFocus();
                }
            }
        });
    }
}