package com.example.servicenovigrad;

import android.content.Intent;
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

public class AddService extends AppCompatActivity {

    private ListView documentsListView;
    private Button confirmButton, cancelButton;
    private EditText serviceName;
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
        setContentView(R.layout.activity_add_service);

        documentsListView = findViewById(R.id.serviceListView);
        confirmButton = findViewById(R.id.confirm_button);
        cancelButton = findViewById(R.id.cancel_button);
        serviceName = findViewById(R.id.serviceNameText);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, documents);
        documentsListView.setAdapter(adapter);

        optionsSelected = new ArrayList<String>();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear documents in optionsSelected
                optionsSelected.clear();
                // reset boolean values
                proofOfResidence = proofOfStatus = photoID = false;

                serviceRef = serviceName.getText().toString().trim();

                for (int i = 0; i < documentsListView.getCount(); i++) {
                    if (documentsListView.isItemChecked(i)) {
                        optionsSelected.add(documentsListView.getItemAtPosition(i).toString());
                        System.out.println(documentsListView.getItemAtPosition(i).toString());
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
                    Toast.makeText(AddService.this, "Please select at least one document", Toast.LENGTH_SHORT).show();
                } else if (serviceName.getText().toString().trim().equals("")) {
                    Toast.makeText(AddService.this, "Please enter a service name", Toast.LENGTH_SHORT).show();
                } else {
                    Service service = new Service(serviceRef, proofOfResidence, proofOfStatus, photoID);

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("Services");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(serviceRef)) {
                                Toast.makeText(AddService.this, "This service already exists. Try to modify or delete it.", Toast.LENGTH_SHORT).show();
                            } else {
                                databaseReference.child(serviceRef).setValue(service);
                                Toast.makeText(AddService.this, "Added service successfully.", Toast.LENGTH_SHORT).show();
                                onConfirmCancel(v);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmCancel(v);
            }
        });
    }


    public void onConfirmCancel(View view) {
        Intent intent = new Intent(getApplicationContext(), ServicePage.class);
        startActivityForResult(intent, 0);
    }

}
