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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class modifyService extends AppCompatActivity {

    private ListView documentsListView;
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
        cancelButton = findViewById(R.id.cancel_button);
        modifyServiceName = findViewById(R.id.serviceNameText);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, documents);
        documentsListView.setAdapter(adapter);

        optionsSelected = new ArrayList<String >();

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceRef = modifyServiceName.getText().toString().trim();

                for(int i=0; i<documentsListView.getCount(); i++) {
                    if (documentsListView.isItemChecked(i)) {
                        optionsSelected.add(documentsListView.getItemAtPosition(i).toString());
                        System.out.println(documentsListView.getItemAtPosition(i).toString());
                    } else {
                        optionsSelected.add("");
                    }
                }

                for(int i=0; i<optionsSelected.size(); i++) {
                    if(optionsSelected.get(i) == "Proof of residence") {
                        proofOfResidence = true;
                    } else if(optionsSelected.get(i) == "Proof of status") {
                        proofOfStatus = true;
                    } else if (optionsSelected.get(i) == "Photo ID"){
                        photoID = true;
                    } else {
                        proofOfResidence = proofOfStatus = photoID = false;
                    }
                }

                if(proofOfResidence == false && proofOfStatus == false && photoID == false) {
                    Toast.makeText(modifyService.this, "Please select at least one document", Toast.LENGTH_SHORT).show();
                    return;
                } else if(modifyServiceName.getText().toString().trim().equals("")){
                    Toast.makeText(modifyService.this, "Please the service name you would like to modify.", Toast.LENGTH_SHORT).show();
                    return;
                } else {


                    Service service = new Service(serviceRef, proofOfResidence, proofOfStatus, photoID);
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("Services");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(serviceRef)) {
                                databaseReference.child(serviceRef).setValue(service);
                                Toast.makeText(modifyService.this, "Added service successfully.", Toast.LENGTH_SHORT).show();
                                onDone(v);
                            } else {
                                Toast.makeText(modifyService.this, "This service doesn't exist.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceRef = modifyServiceName.getText().toString().trim();


                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Services");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(serviceRef)) {
                            databaseReference = FirebaseDatabase.getInstance().getReference("Services").child(serviceRef);
                            databaseReference.removeValue();
                            Toast.makeText(modifyService.this, "Deleted service successfully.", Toast.LENGTH_SHORT).show();
                            onDone(v);
                        } else {
                            Toast.makeText(modifyService.this, "Cannot delete this service. It doesn't exist.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }

    public void onDone(View view) {
        Intent intent = new Intent(getApplicationContext(), ServicePage.class);
        startActivityForResult(intent, 0);
    }
}
