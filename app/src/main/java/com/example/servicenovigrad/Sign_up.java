package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;


import android.os.Bundle;

public class Sign_up extends AppCompatActivity {

    private static final String[] users = {"Admin", "Employee", "Client"};
    Spinner spinner_su;
    EditText username_su, password_su, confirmPassword_su;
    Button signupBtn_su;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        spinner_su = findViewById(R.id.spinner);
        signupBtn_su = findViewById(R.id.login_button);
        username_su = findViewById(R.id.usernameField);
        password_su = findViewById(R.id.passwordField);
        confirmPassword_su = findViewById(R.id.passwordField);

        //ADD SPINNER

    }





}