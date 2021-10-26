package com.example.servicenovigrad;

import static com.example.servicenovigrad.Sign_up.userList;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    EditText username, password;
    Button loginBtn, signupBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.login_button);
        signupBtn = findViewById(R.id.signup_button);
        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);

        userList.add(new User("admin", "admin", 'a'));


        //GO TO SIGN UP PAGE
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onSignupPage(v);}
        });

        //GO TO WELCOME PAGE AFTER CLICKING "LOGIN"
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VERIFY CREDENTIALS OF ADMIN AND REDIRECT TO ADMIN WELCOME PAGE
                if(authentication() == 'a') {
                    onWelcomePageAdmin(v);
                    //DISPLAY "LOGIN SUCCESSFUL" FOR ADMIN
                    Toast.makeText(MainActivity.this,"Login successful.",Toast.LENGTH_SHORT).show();
                }

                //VERIFY CREDENTIALS OF EMPLOYEE AND REDIRECT TO EMPLOYEE WELCOME PAGE
                else if(authentication() == 'e') {
                    onWelcomePageEmployee(v);
                    Toast.makeText(MainActivity.this,"Login successful.",Toast.LENGTH_SHORT).show();
                }

                //VERIFY CREDENTIALS OF CLIENT AND REDIRECT TO CLIENT WELCOME PAGE
                else if(authentication() == 'c') {
                    onWelcomePageClient(v);
                    Toast.makeText(MainActivity.this,"Login successful.",Toast.LENGTH_SHORT).show();
                }

                //DISPLAY "LOGIN FAILED" IF CREDENTIALS DON'T MATCH ANY USER
                else {
                    Toast.makeText(MainActivity.this,"Login failed.",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    //RETURN ROLE OF USER OR 'Z' IF NON EXISTENT
    public char authentication() {
        char role = 'z';
        for(int i=0; i<userList.size(); i++) {
            if(username.getText().toString().equals(userList.get(i).getUsername().toString())) {
                if(password.getText().toString().equals(userList.get(i).getPassword().toString())) {
                    role = userList.get(i).getRole();

                }
            }
        }
        return role;
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