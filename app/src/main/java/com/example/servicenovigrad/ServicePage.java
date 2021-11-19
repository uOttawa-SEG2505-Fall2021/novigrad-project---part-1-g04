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

        addServiceButton = findViewById(R.id.confirm_button);
        goBackButton = findViewById(R.id.cancel_button);

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