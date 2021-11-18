package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ServicePage extends AppCompatActivity {

    Button addServiceButton;
    Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_page);

        addServiceButton = findViewById(R.id.addServiceButton);
        goBackButton = findViewById(R.id.goBackButton);

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