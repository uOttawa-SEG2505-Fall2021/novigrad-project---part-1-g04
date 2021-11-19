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
import java.util.Objects;

public class addService extends AppCompatActivity {

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

        documentsListView = findViewById(R.id.documentsListView);
        confirmButton = findViewById(R.id.confirm_button);
        cancelButton = findViewById(R.id.cancel_button);
        serviceName = findViewById(R.id.serviceNameText);

        proofOfResidence = false;
        proofOfStatus = false;
        photoID = false;




        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, documents);
        documentsListView.setAdapter(adapter);

        optionsSelected = new ArrayList<String >();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0; i<documentsListView.getCount(); i++) {
                    if (documentsListView.isItemChecked(i)) {
                        optionsSelected.add(documentsListView.getItemAtPosition(i).toString());
                        System.out.println(documentsListView.getItemAtPosition(i).toString());
                    }
                }


                if(optionsSelected.isEmpty()) {
                    Toast.makeText(addService.this, "Please select at least one document", Toast.LENGTH_SHORT).show();
                    return;
                } else if(serviceName.getText().toString().trim().equals("")){
                    Toast.makeText(addService.this, "Please enter a service name", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    serviceRef = serviceName.getText().toString().trim();




                    for(int i=0; i<optionsSelected.size(); i++) {
                        if(optionsSelected.get(i) == "Proof of residence") {
                            proofOfResidence = true;
                        } else if(optionsSelected.get(i) == "Proof of status") {
                            proofOfStatus = true;
                        } else {
                            photoID = true;
                        }
                    }
                    Service service = new Service(serviceRef, proofOfResidence, proofOfStatus, photoID);
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("Services");
                    databaseReference.child(serviceRef).setValue(service);

                    onConfirmCancel(v);

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
