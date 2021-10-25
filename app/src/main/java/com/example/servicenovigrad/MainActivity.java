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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String[] users = {"Admin", "Employee", "Client"};

    EditText username, password;
    Button loginBtn, signupBtn;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        loginBtn = findViewById(R.id.login_button);
        signupBtn = findViewById(R.id.signup_button);
        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, users);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //GO TO SIGN UP PAGE
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onSignupPage(v);}
        });

        //GO TO WELCOME PAGE AFTER CLICKING "LOGIN"
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHECK CREDENTIALS OF ADMIN
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) { //NEED TO CHECK IS SPINNER == ADMIN
                    //DISPLAY "LOGIN SUCCESSFUL FOR ADMIN
                    Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_SHORT).show();

                    //GO TO WELCOME PAGE OF ADMIN
                    onWelcomePageAdmin(v);
                }
                if(username.getText().toString().equals("bob") && password.getText().toString().equals("bob")) { //NEED TO CHECK IS SPINNER == ADMIN
                    //DISPLAY "LOGIN SUCCESSFUL FOR ADMIN
                    Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_SHORT).show();

                    //GO TO WELCOME PAGE OF ADMIN
                    onWelcomePageClient(v);
                }

                else {
                    //DISPLAY "LOGIN SUCCESSFUL FOR ADMIN
                    Toast.makeText(MainActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                }
            }

        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the third item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    //SWITCH TO SIGN UP ACTIVITY
    public void onSignupPage(View view) {
        Intent intent = new Intent(getApplicationContext(), Sign_up.class);
        startActivityForResult(intent,0);
    }

    //SWITCH TO WELCOME PAGE FOR ADMIN ACTIVITY
    public void onWelcomePageAdmin(View view) {
        Intent intent = new Intent(getApplicationContext(), welcomePage_admin.class);
        intent.putExtra("USERNAME",username.getText().toString());
        startActivityForResult(intent,0);
    }

    //SWITCH TO WELCOME PAGE FOR USER ACTIVITY
    public void onWelcomePageClient(View view) {
        Intent intent = new Intent(getApplicationContext(), welcomePage_client.class);
        intent.putExtra("USERNAME",username.getText().toString());
        startActivityForResult(intent,0);
    }

    //SWITCH TO WELCOME PAGE FOR EMPLOYEE ACTIVITY
    public void onWelcomePageEmployee(View view) {
        Intent intent = new Intent(getApplicationContext(), welcomePage_employee.class);
        intent.putExtra("USERNAME",username.getText().toString());
        startActivityForResult(intent,0);
    }

}