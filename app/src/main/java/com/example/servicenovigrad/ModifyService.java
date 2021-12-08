package com.example.servicenovigrad;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModifyService extends AppCompatActivity {

    ListView documentsListView;
    Button cancelButton, modifyButton, deleteButton;
    EditText modifyServiceName;

    private boolean proofOfResidence, proofOfStatus, photoID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String serviceRef;

    String[] documents = {"Proof of residence", "Proof of status", "Photo ID"};
    ArrayAdapter<String> adapter;
    ArrayList<String> optionsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_service);

        documentsListView = findViewById(R.id.documentsListView);
        modifyButton = findViewById(R.id.modify_button);
        cancelButton = findViewById(R.id.CancelRequest_button);
        modifyServiceName = findViewById(R.id.serviceNameText);


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, documents);
        documentsListView.setAdapter(adapter);

        optionsSelected = new ArrayList<String>();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear documents in optionsSelected
                optionsSelected.clear();
                // reset boolean values
                proofOfResidence = proofOfStatus = photoID = false;

                serviceRef = modifyServiceName.getText().toString().trim();

                for (int i = 0; i < documentsListView.getCount(); i++) {
                    if (documentsListView.isItemChecked(i)) {
                        optionsSelected.add(documentsListView.getItemAtPosition(i).toString());
                    }
                }

                for (int i = 0; i < optionsSelected.size(); i++) {
                    switch (optionsSelected.get(i)) {
                        case "Proof of residence":
                            proofOfResidence = true;
                            break;
                        case "Proof of status":
                            proofOfStatus = true;
                            break;
                        case "Photo ID":
                            photoID = true;
                            break;
                    }
                }

                if (!proofOfResidence && !proofOfStatus && !photoID) {
                    Toast.makeText(ModifyService.this, "Please select at least one document.", Toast.LENGTH_SHORT).show();
                } else if (modifyServiceName.getText().toString().trim().equals("")) {
                    modifyServiceName.setError("This branch name has already been used.");
                    modifyServiceName.requestFocus();
                } else {
                    Service service = new Service(serviceRef, proofOfResidence, proofOfStatus, photoID);
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("Services");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(serviceRef)) {
                                databaseReference.child(serviceRef).setValue(service);
                                Toast.makeText(ModifyService.this, "Modified service successfully.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ModifyService.this, "This service doesn't exist.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }
}
