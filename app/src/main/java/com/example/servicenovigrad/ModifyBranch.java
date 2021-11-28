package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ModifyBranch extends AppCompatActivity {

    ListView serviceListView;
    Button confirmButton, cancelButton;

//    private boolean proofOfResidence, proofOfStatus, photoID;
    private String branchRef; // Reference to current branch
    private List<String> serviceList;
    private List<String> optionsSelected; // List of services selected

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_branch);

        serviceListView = findViewById(R.id.serviceListView);
        confirmButton = findViewById(R.id.confirm_button);
        cancelButton = findViewById(R.id.cancel_button);

        serviceList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Services");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clearing the previous List
                serviceList.clear();

                //iterating through all the nodes
                for (DataSnapshot serviceDatasnap : snapshot.getChildren()) {
                    //getting service
                    Service service = serviceDatasnap.getValue(Service.class);
                    //adding service to the List
                    assert service != null;
                    serviceList.add(service.getServiceName());
                }

                // Convert serviceList to array
                String[] serviceArray = new String[serviceList.size()];
                for (int i = 0; i < serviceList.size(); i++) {
                    serviceArray[i] = serviceList.get(i);
                }

                //creating adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ModifyBranch.this,
                        android.R.layout.simple_list_item_multiple_choice, serviceArray);

                //attaching adapter to the ListView
                serviceListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}