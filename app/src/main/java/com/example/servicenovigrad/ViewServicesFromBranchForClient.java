package com.example.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewServicesFromBranchForClient extends AppCompatActivity {

    private ListView servicesListView;
    private Button goBackBtn;
    private TextView displayBranchName;
    private String branchName, branchServices, email;
    private List<String> serviceList;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services_from_branch_for_client);

        servicesListView = findViewById(R.id.DisplayServicesListView);
        displayBranchName = findViewById(R.id.displayBranchName);
        goBackBtn = findViewById(R.id.goBack_button);
        branchName = getIntent().getStringExtra("branchName");
        displayBranchName.setText("Branch name: " + branchName);
        email = getIntent().getStringExtra("email");

        serviceList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Branches");

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        databaseReference.child(Objects.requireNonNull(branchName)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    if(Objects.requireNonNull(task.getResult().exists())) {
                        DataSnapshot dataSnapshot = task.getResult();
                        //Get string of selected string
                        branchServices = String.valueOf(dataSnapshot.child("services").getValue());
                        //Remove first character "["
                        branchServices = branchServices.substring(1);
                        //Remove last character "]"
                        branchServices = branchServices.substring(0, branchServices.length() - 1);
                        //Convert string to arrayList
                        String[] serviceArray = branchServices.split(", ");

                        ArrayAdapter adapter = new ArrayAdapter(ViewServicesFromBranchForClient.this, android.R.layout.simple_list_item_1, serviceArray);
                        servicesListView.setAdapter(adapter);

                    }
                }
            }
        });

        servicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String serviceName = (String) servicesListView.getItemAtPosition(position);

                Intent intent = new Intent(ViewServicesFromBranchForClient.this, ClientInformation.class);
                intent.putExtra("email", email);
                intent.putExtra("branchName", branchName);
                intent.putExtra("serviceName", serviceName);
                startActivity(intent);
            }
        });


    }
}