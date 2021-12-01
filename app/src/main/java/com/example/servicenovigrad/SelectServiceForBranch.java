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

public class SelectServiceForBranch extends AppCompatActivity {

    ListView serviceListView;
    TextView addService;
    Button addBranchBtn, goBackBtn;

    private List<String> serviceList;
    private String branchName, phoneNumber, address;
    private int startHour, startMinute, endHour, endMinute;
    private boolean mon, tue, wed, thu, fri, sat, sun;
    private ArrayList<String> openDays;
    private ArrayList<String> servicesSelectedList;

    DatabaseReference branchesDataRef, servicesDataRef;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service_for_branch);

        serviceListView = findViewById(R.id.branchServiceListView);
        addBranchBtn = findViewById(R.id.addBranch_button);
        goBackBtn = findViewById(R.id.goBackButton);

        firebaseDatabase = FirebaseDatabase.getInstance();
        branchesDataRef = firebaseDatabase.getReference("Branches");
        servicesDataRef = firebaseDatabase.getReference("Services");

        serviceList = new ArrayList<>();
        servicesSelectedList = new ArrayList<>();

        //Get information from branchAvailability
        branchName = getIntent().getStringExtra("branchName");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        address = getIntent().getStringExtra("address");
        startHour = getIntent().getIntExtra("startHour", 0);
        startMinute = getIntent().getIntExtra("startMinute", 0);
        endHour = getIntent().getIntExtra("endHour", 0);
        endMinute = getIntent().getIntExtra("endMinute", 0);
        mon = getIntent().getBooleanExtra("mon", false);
        tue = getIntent().getBooleanExtra("tue", false);
        wed = getIntent().getBooleanExtra("thu", false);
        thu = getIntent().getBooleanExtra("mon", false);
        fri = getIntent().getBooleanExtra("fri", false);
        sat = getIntent().getBooleanExtra("sat", false);
        sun = getIntent().getBooleanExtra("sun", false);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        openDays = (ArrayList<String>) bundle.getSerializable("openDays");


        //Add the name of services (using a modified version of serviceListAdapter) to a listView
        servicesDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectServiceForBranch.this,
                        android.R.layout.simple_list_item_multiple_choice, serviceArray);

                //attaching adapter to the ListView
                serviceListView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addBranchBtn.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(SelectServiceForBranch.this,
                            "Please select at least one service.", Toast.LENGTH_SHORT).show();
                } else {
                    branchesDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Branch branch = new Branch(branchName, address, phoneNumber, startHour,
                                    startMinute, endHour, endMinute, servicesSelectedList, openDays);
                            // Change services to selected services
                            branchesDataRef.child(branchName).setValue(branch);
                            Toast.makeText(SelectServiceForBranch.this,
                                    "Added branch successfully.", Toast.LENGTH_SHORT).show();

                            // Return to welcomePageEmployee
                            Intent intent = new Intent(SelectServiceForBranch.this, WelcomePageEmployee.class);
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

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}