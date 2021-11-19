package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServicePage extends AppCompatActivity {

    private ListView serviceListView;
    private Button addServiceButton;
    private Button goBackButton;
    private List<Service> serviceList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_page);

        serviceListView = findViewById(R.id.serviceListView);
        addServiceButton = findViewById(R.id.confirm_button);
        goBackButton = findViewById(R.id.cancel_button);
        serviceList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Services");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceList.clear();

                for (DataSnapshot serviceDatasnap : snapshot.getChildren()) {
                    Service service = serviceDatasnap.getValue(Service.class);
                    serviceList.add(service);
                }

                ListAdapter adapter = new ServiceListAdapter(ServicePage.this, serviceList);
                serviceListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        serviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });

        //GO TO ADD SERVICE PAGE AFTER CLICKING "ADD SERVICE"
        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddService(v);
            }
        });

        //GO BACK TO ADMIN WELCOME PAGE AFTER CLICKING "GO BACK"
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoBack(v);
                finish();
            }
        });


    }

    public void onAddService(View view) {
        Intent intent = new Intent(getApplicationContext(), addService.class);
        startActivityForResult(intent, 0);
    }

    public void onGoBack(View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomePageAdmin.class);
        startActivityForResult(intent, 0);
    }
}