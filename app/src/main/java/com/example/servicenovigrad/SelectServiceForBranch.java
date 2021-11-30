package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectServiceForBranch extends AppCompatActivity {

    private ListView branchServiceListView;
    private TextView addService;
    private Button addBranchBtn, goBackBtn;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> optionsSelected;
    private List<Service> serviceList;
    private List<String> servicesForBranch;
    private DatabaseReference databaseReference;
    private String branchName, phoneNumber, address;
    private int startHour, startMinute, endHour, endMinute;
    private boolean mon, tue, wed, thu, fri, sat, sun;
    private ArrayList<String> openDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service_for_branch);

        branchServiceListView = findViewById(R.id.branchServiceListView);
        addBranchBtn = findViewById(R.id.addBranch_button);
        goBackBtn = findViewById(R.id.goBackButton);


        optionsSelected = new ArrayList<String>();
        serviceList = new ArrayList<>();
        servicesForBranch = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Services");

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
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceList.clear();

                //iterating through all the nodes
                for (DataSnapshot serviceDatasnap : snapshot.getChildren()) {
                    //getting service
                    Service service = serviceDatasnap.getValue(Service.class);
                    //adding service to the List
                    serviceList.add(service);
                }

                ListAdapter adapter = new ModifiedServiceListAdapter(SelectServiceForBranch.this, serviceList);
                branchServiceListView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        branchServiceListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView service = (TextView) view.findViewById(R.id.ServiceNameTextBranch);
                String serviceName = service.getText().toString().trim();

                if(servicesForBranch.contains(serviceName)) {
                    Toast.makeText(SelectServiceForBranch.this, "This service has already been selected. If you want to remove it, go to modify branch.", Toast.LENGTH_LONG).show();
                } else {
                    servicesForBranch.add(serviceName);
                    Toast.makeText(SelectServiceForBranch.this, "This service will be added to the branch", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        addBranchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(servicesForBranch.isEmpty()) {
                    Toast.makeText(SelectServiceForBranch.this, "You need to select at least one service for this branch", Toast.LENGTH_SHORT).show();
                } else {
                    Branch branch = new Branch(branchName, address, phoneNumber, startHour, startMinute, endHour, endMinute,
                            (ArrayList<String>) servicesForBranch, openDays);


                    databaseReference.child(branchName).setValue(branch);
                    Toast.makeText(SelectServiceForBranch.this, "Added branch successfully.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SelectServiceForBranch.this, WelcomePageEmployee.class);
                    startActivity(intent);

                }
            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save fields if go back button is pressed
                Intent intent = new Intent(SelectServiceForBranch.this, BranchAvailability.class);
                intent.putExtra("branchName", branchName);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });
    }

}