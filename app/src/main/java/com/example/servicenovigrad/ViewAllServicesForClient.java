package com.example.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAllServicesForClient extends AppCompatActivity {

    private ListView servicesListView;
    private Button goBackBtn;
    private String email;
    private List<Service> serviceList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_services_for_client);

        servicesListView = findViewById(R.id.ServicesForClientsListView);
        goBackBtn = findViewById(R.id.goBack_button);
        email = getIntent().getStringExtra("email");
        serviceList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Services");

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Service service = dataSnapshot.getValue(Service.class);
                    serviceList.add(service);
                }

                ListAdapter adapter = new ServiceListAdapter(ViewAllServicesForClient.this, serviceList);
                //attaching adapter to the ListView
                servicesListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        servicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service service = (Service) servicesListView.getItemAtPosition(position);
                String serviceName = service.getServiceName().trim();
                Intent intent = new Intent(ViewAllServicesForClient.this, ViewBranchesFromServiceForClient.class);
                intent.putExtra("email", email);
                intent.putExtra("serviceName", serviceName);
                startActivity(intent);
            }
        });
    }
}