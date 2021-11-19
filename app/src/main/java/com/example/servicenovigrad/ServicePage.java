package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class ServicePage extends AppCompatActivity {

    ListView serviceListView;
    Button addServiceButton;
    Button goBackButton;

    private Activity context;
    List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_page);

        serviceListView = findViewById(R.id.serviceListView);
        addServiceButton = findViewById(R.id.addServiceButton);
        goBackButton = findViewById(R.id.goBackButton);

        // Array adapter
        ArrayAdapter adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.activity_list_item,
                (List<String>) serviceListView
        );

        serviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ADD SOME SHIT HERE
            }
        });

        //GO TO ADD SERVICE PAGE AFTER CLICKING "ADD SERVICE"
        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addService.class);
                startActivityForResult(intent, 0);
            }
        });

        //GO BACK TO ADMIN WELCOME PAGE AFTER CLICKING "GO BACK"
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}