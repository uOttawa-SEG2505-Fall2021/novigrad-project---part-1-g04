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

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button loginBtn, signupBtn;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.login_button);
        signupBtn = findViewById(R.id.signup_button);
        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);

        //PRE-CREATED ADMIN CREDENTIALS
        userList.add(new Admin("admin", "admin"));


        //GO TO SIGN UP PAGE
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignupPage(v);
            }
        });

        //GO TO WELCOME PAGE AFTER CLICKING "LOGIN"
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clientNumber() == -1) {
                    Toast.makeText(MainActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                }

                //VERIFY CREDENTIALS OF ADMIN AND REDIRECT TO ADMIN WELCOME PAGE
                else if (userList.get(clientNumber()).getRole() == 'a') {
                    onWelcomePageAdmin(v);
                    //DISPLAY "LOGIN SUCCESSFUL" FOR ADMIN
                    Toast.makeText(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                }

                //VERIFY CREDENTIALS OF EMPLOYEE AND REDIRECT TO EMPLOYEE WELCOME PAGE
                else if (userList.get(clientNumber()).getRole() == 'e') {
                    onWelcomePageEmployee(v);
                    Toast.makeText(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                }

                //VERIFY CREDENTIALS OF CLIENT AND REDIRECT TO CLIENT WELCOME PAGE
                else if (userList.get(clientNumber()).getRole() == 'c') {
                    onWelcomePageClient(v);
                    Toast.makeText(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                }

                //DISPLAY "LOGIN FAILED" IF CREDENTIALS DON'T MATCH ANY USER
                else {
                    Toast.makeText(MainActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //RETURN INDEX OF THE POSITION OF USER IN THE USER LIST OR -1 IF USER DOESN'T EXIST
    public int clientNumber() {
        for (int i = 0; i < userList.size(); i++) {
            if (username.getText().toString().equals(userList.get(i).getUsername()) || username.getText().toString().equals(userList.get(i).getEmail())) {
                if (password.getText().toString().equals(userList.get(i).getPassword())) {
                    return i;
                }
            }
        }
        return -1;
    }

    //SWITCH TO SIGN UP ACTIVITY
    public void onSignupPage(View view) {
        Intent intent = new Intent(getApplicationContext(), Sign_up.class);
        startActivityForResult(intent, 0);
    }

    //SWITCH TO WELCOME PAGE FOR ADMIN ACTIVITY
    public void onWelcomePageAdmin(View view) {
        Intent intent = new Intent(getApplicationContext(), welcomePage_admin.class);
        intent.putExtra("USERNAME", userList.get(clientNumber()).getUsername());
        startActivityForResult(intent, 0);
    }

    //SWITCH TO WELCOME PAGE FOR USER ACTIVITY
    public void onWelcomePageClient(View view) {
        Intent intent = new Intent(getApplicationContext(), welcomePage_client.class);
        intent.putExtra("USERNAME", userList.get(clientNumber()).getUsername());
        startActivityForResult(intent, 0);
    }

    //SWITCH TO WELCOME PAGE FOR EMPLOYEE ACTIVITY
    public void onWelcomePageEmployee(View view) {
        Intent intent = new Intent(getApplicationContext(), welcomePage_employee.class);
        intent.putExtra("USERNAME", userList.get(clientNumber()).getUsername());
        startActivityForResult(intent, 0);
    }

}