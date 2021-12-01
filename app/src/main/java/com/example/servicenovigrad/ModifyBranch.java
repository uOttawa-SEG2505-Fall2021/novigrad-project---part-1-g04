package com.example.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModifyBranch extends AppCompatActivity {

    ListView serviceListView;
    Button confirmButton, cancelButton;
    TextView displayBranchName;

    private String branchName; // Reference to current branch
    private List<String> serviceList; // List of services in database
    private List<String> servicesSelectedList; // List of services selected

    DatabaseReference branchesDataRef, servicesDataRef;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_branch);

        serviceListView = findViewById(R.id.serviceListView);
        confirmButton = findViewById(R.id.confirm_button);
        cancelButton = findViewById(R.id.cancel_button);
        displayBranchName = findViewById(R.id.displayBranchName);

        branchName = getIntent().getStringExtra("branchName");
        firebaseDatabase = FirebaseDatabase.getInstance();
        branchesDataRef = firebaseDatabase.getReference("Branches");
        servicesDataRef = firebaseDatabase.getReference("Services");

        serviceList = new ArrayList<>();
        servicesSelectedList = new ArrayList<>();

        servicesDataRef.addValueEventListener(new ValueEventListener() {
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

                branchesDataRef.child(branchName).child("services").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (int i = 0; i < serviceListView.getCount(); i++) {
                            String serviceName = serviceListView.getItemAtPosition(i).toString();

                            // Iterating through all the service nodes of branch
                            for (DataSnapshot serviceDatasnap : snapshot.getChildren()) {
                                if (Objects.requireNonNull(serviceDatasnap.getValue()).toString().equals(serviceName)) {
                                    // Checkmark to checkbox if service is already selected in database
                                    serviceListView.setItemChecked(i, true);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
                // HashMap with service name as key and boolean value as value
                // true if selected and false otherwise
                Map<String, Boolean> servicesSelectedMap = new HashMap<>();

                for (int i = 0; i < serviceListView.getCount(); i++) {
                    // Name of service to be added to servicesSelected
                    String serviceName = serviceListView.getItemAtPosition(i).toString();
                    // Boolean value for if service was selected
                    boolean checked = serviceListView.isItemChecked(i);
                    // Add serviceName and checked to servicesSelectedMap
                    servicesSelectedMap.put(serviceName, checked);

                    if (checked) {
                        // Add serviceName to servicesSelectedList if checked is true
                        servicesSelectedList.add(serviceName);
                    }
                }

                if (!servicesSelectedMap.containsValue(true)) {
                    Toast.makeText(ModifyBranch.this,
                            "Please select at least one service.", Toast.LENGTH_SHORT).show();
                } else {
                    branchesDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Change services to selected services
                            branchesDataRef.child(branchName).child("services").setValue(servicesSelectedList);
                            Toast.makeText(ModifyBranch.this,
                                    "Modified branch successfully.", Toast.LENGTH_SHORT).show();

                            // Return to welcomePageEmployee
                            Intent intent = new Intent(ModifyBranch.this, WelcomePageEmployee.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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