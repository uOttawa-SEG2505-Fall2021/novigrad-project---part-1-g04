package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
    private FirebaseDatabase firebaseDatabase;

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
                //clearing the previous List
                serviceList.clear();

                //iterating through all the nodes
                for (DataSnapshot serviceDatasnap : snapshot.getChildren()) {
                    //getting service
                    Service service = serviceDatasnap.getValue(Service.class);
                    //adding service to the List
                    serviceList.add(service);
                }

                //creating adapter
                ListAdapter adapter = new ServiceListAdapter(ServicePage.this, serviceList);
                //attaching adapter to the ListView
                serviceListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        serviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String serviceName = serviceList.get(position).toString();

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Services");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(serviceName)) {
                            databaseReference = FirebaseDatabase.getInstance().getReference("Services").child(serviceName);
                            databaseReference.removeValue();
                            Toast.makeText(ServicePage.this, "Deleted service successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ServicePage.this, "Cannot delete this service. It doesn't exist.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
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
            }
        });
    }

    public void onAddService(View view) {
        Intent intent = new Intent(getApplicationContext(), AddService.class);
        startActivityForResult(intent, 0);
    }

    public void onGoBack(View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomePageAdmin.class);
        startActivityForResult(intent, 0);
    }
}