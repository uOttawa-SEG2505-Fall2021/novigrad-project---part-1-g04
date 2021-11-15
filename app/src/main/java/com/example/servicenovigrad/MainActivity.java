package com.example.servicenovigrad;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText EmailField, passwordField;
    Button logInButton, signUpPageButton;
//    private int id;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInButton = findViewById(R.id.login_button);
        signUpPageButton = findViewById(R.id.signup_button);
        EmailField = findViewById(R.id.EmailField);
        passwordField = findViewById(R.id.passwordField);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //GO TO SIGN UP PAGE
        signUpPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpPage(v);
            }
        });


        //GO TO WELCOME PAGE AFTER CLICKING "LOGIN"
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();

            }
        });
    }

    public void userLogin() {
        String email = EmailField.getText().toString().trim();
        String password = passwordField.getText().toString();

        if (email.isEmpty()) {
            EmailField.setError("Email is required!");
            EmailField.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordField.setError("Password is required");
            passwordField.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser()
                            .getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {

                                    DataSnapshot dataSnapshot = task.getResult();
                                    String username = String.valueOf(dataSnapshot.child("username").getValue());
                                    String role = String.valueOf(dataSnapshot.child("role").getValue());

                                    if (role.equals("Client")) {
                                        Intent intent = new Intent(getApplicationContext(), WelcomePageClient.class);
                                        intent.putExtra("USERNAME", username);
                                        startActivityForResult(intent, 0);
                                    } else if (role.equals("Employee")) {
                                        Intent intent = new Intent(getApplicationContext(), WelcomePageEmployee.class);
                                        intent.putExtra("USERNAME", username);
                                        startActivityForResult(intent, 0);
                                    } else {
                                        Intent intent = new Intent(getApplicationContext(), WelcomePageAdmin.class);
                                        intent.putExtra("USERNAME", username);
                                        startActivityForResult(intent, 0);
                                    }
                                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch(FirebaseAuthInvalidCredentialsException e) {
                        EmailField.setError("Invalid email!");
                        EmailField.requestFocus();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //SWITCH TO SIGN UP ACTIVITY
    public void onSignUpPage(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivityForResult(intent, 0);
    }
}