package com.example.servicenovigrad;

import static com.example.servicenovigrad.SignUp.databaseUsers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText usernameField, passwordField;
    Button logInButton, signUpPageButton;
//    private int id;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInButton = findViewById(R.id.logInButton);
        signUpPageButton = findViewById(R.id.signUpPageButton);
        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //PRE-CREATED ADMIN CREDENTIALS
        /* ADD HERE */

//        String id = databaseUsers.push().getKey();

        //GO TO SIGN UP PAGE
        signUpPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpPage(v);
            }
        });

//        //GO TO WELCOME PAGE AFTER CLICKING "LOGIN"
//        logInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (clientNumber() == -1) {
//                    Toast.makeText(MainActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
//                }
//
//                //VERIFY CREDENTIALS OF ADMIN AND REDIRECT TO ADMIN WELCOME PAGE
//                else if (userList.get(clientNumber()).getRole().equals("Admin")) {
//                    onWelcomePageAdmin(v);
//                    //DISPLAY "LOGIN SUCCESSFUL" FOR ADMIN
//                    Toast.makeText(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
//                }
//
//                //VERIFY CREDENTIALS OF EMPLOYEE AND REDIRECT TO EMPLOYEE WELCOME PAGE
//                else if (userList.get(clientNumber()).getRole().equals("Employee")) {
//                    onWelcomePageEmployee(v);
//                    Toast.makeText(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
//                }
//
//                //VERIFY CREDENTIALS OF CLIENT AND REDIRECT TO CLIENT WELCOME PAGE
//                else if (userList.get(clientNumber()).getRole().equals("Client")) {
//                    onWelcomePageClient(v);
//                    Toast.makeText(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
//                }
//
//                //DISPLAY "LOGIN FAILED" IF CREDENTIALS DON'T MATCH ANY USER
//                else {
//                    Toast.makeText(MainActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

//    //RETURN INDEX OF THE POSITION OF USER IN THE USER LIST OR -1 IF USER DOESN'T EXIST
//    public int clientNumber() {
//        for (int i = 0; i < userList.size(); i++) {
//            if (usernameField.getText().toString().equals(userList.get(i).getUsername())
//                    || usernameField.getText().toString().equals(userList.get(i).getEmail())) {
//                if (passwordField.getText().toString().equals(userList.get(i).getPassword())) {
//                    return i;
//                }
//            }
//        }
//        return -1;
//    }

    //SWITCH TO SIGN UP ACTIVITY
    public void onSignUpPage(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivityForResult(intent, 0);
    }

//    //SWITCH TO WELCOME PAGE FOR ADMIN ACTIVITY
//    public void onWelcomePageAdmin(View view) {
//        Intent intent = new Intent(getApplicationContext(), WelcomePageAdmin.class);
//        intent.putExtra("USERNAME", userList.get(clientNumber()).getUsername());
//        startActivityForResult(intent, 0);
//    }
//
//    //SWITCH TO WELCOME PAGE FOR USER ACTIVITY
//    public void onWelcomePageClient(View view) {
//        Intent intent = new Intent(getApplicationContext(), WelcomePageClient.class);
//        intent.putExtra("USERNAME", userList.get(clientNumber()).getFirstName());
//        startActivityForResult(intent, 0);
//    }
//
//    //SWITCH TO WELCOME PAGE FOR EMPLOYEE ACTIVITY
//    public void onWelcomePageEmployee(View view) {
//        Intent intent = new Intent(getApplicationContext(), WelcomePageEmployee.class);
//        intent.putExtra("USERNAME", userList.get(clientNumber()).getFirstName());
//        startActivityForResult(intent, 0);
//    }
    
}