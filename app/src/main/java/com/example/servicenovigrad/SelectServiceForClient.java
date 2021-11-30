package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.drm.DrmStore;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SelectServiceForClient extends AppCompatActivity {

    private ListView servicesListView;
    private TextView displayBranchName;
    private String branchName, test, email;
    private List<String> serviceList;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service_for_client);

        servicesListView = findViewById(R.id.DisplayServicesListView);
        displayBranchName = findViewById(R.id.displayBranchName);
        branchName = getIntent().getStringExtra("branchName");
        displayBranchName.setText("Branch name: " + branchName);
        email = getIntent().getStringExtra("email");

        serviceList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Branches");
        databaseReference.child(Objects.requireNonNull(branchName)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    if(Objects.requireNonNull(task.getResult().exists())) {
                        DataSnapshot dataSnapshot = task.getResult();
                        //Get string of selected string
                        test = String.valueOf(dataSnapshot.child("services").getValue());
                        //Remove first character "["
                        test = test.substring(1);
                        //Remove last character "]"
                        test = test.substring(0, test.length() - 1);
                        //Convert string to arrayList
                        String[] serviceArray = test.split(", ");

                        ArrayAdapter adapter = new ArrayAdapter(SelectServiceForClient.this, android.R.layout.simple_list_item_1, serviceArray);
                        servicesListView.setAdapter(adapter);

                    }
                }
            }
        });

        servicesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Get service name for listView
                String serviceName = (String) parent.getItemAtPosition(position);
                //Create new request
                Request request = new Request(email, branchName, serviceName);

                databaseReference = FirebaseDatabase.getInstance().getReference("Requests");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Check if request has already been submitted by a client
                        if(snapshot.child(branchName).hasChild(stringHash(email, branchName, serviceName))) {

                            Toast.makeText(SelectServiceForClient.this, "You already submitted a request for this service.", Toast.LENGTH_SHORT).show();
                        } else {
                            //Add request to database
                            databaseReference.child(Objects.requireNonNull(branchName)).child(stringHash(email, branchName, serviceName)).
                                    setValue(request);
                            Toast.makeText(SelectServiceForClient.this, "Submitted request successfully.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                return true;
            }
        });


    }

    //Generate a unique hash for a request (use email, branchName and serviceName)
    public String stringHash(String email, String branchName, String serviceName) {
        long h = 0;
        String id = email + branchName + serviceName;
        for(int i=0; i<id.length(); i++) {
            //140627 is a prime number
            h = h * 140627 + id.charAt(i);
        }
        String hash = Long.toString(h);

        return hash;
    }
}