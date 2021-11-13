package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    Spinner spinner;
    EditText emailField, usernameField, passwordField,
            confirmPasswordField, firstNameField, lastNameField;
    Button signUpButton;

    List<User> users;
    static DatabaseReference databaseUsers;
    private FirebaseAuth mAuth;
//    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize users List
        users = new ArrayList<User>();

        // Initialize Firebase Reference
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        spinner = findViewById(R.id.spinner);
        signUpButton = findViewById(R.id.signUpButton);
        emailField = findViewById(R.id.emailField);
        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        confirmPasswordField = findViewById(R.id.confirmPasswordField);
        firstNameField = findViewById(R.id.firstNameField);
        lastNameField = findViewById(R.id.lastNameField);

        // Create an adapter to describe how the items are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, new String[] {"Employee", "Client"});
        // Set the spinner's adapter to the previously created one
        spinner.setAdapter(adapter);

        //GO TO WELCOME PAGE AFTER CLICKING "SIGN UP"
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\." +
                "[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //SWITCH TO WELCOME PAGE FOR USER ACTIVITY
    public void onWelcomePageClient(View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomePageClient.class);
        intent.putExtra("USERNAME", firstNameField.getText().toString());
        startActivityForResult(intent, 0);
    }

    //SWITCH TO WELCOME PAGE FOR EMPLOYEE ACTIVITY
    public void onWelcomePageEmployee(View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomePageEmployee.class);
        intent.putExtra("USERNAME", firstNameField.getText().toString());
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    private void reload() {
    }

//    private void addUser() {
//        //getting the values to save
//        String role = spinner.getSelectedItem().toString().trim();
//        String email = emailField.getText().toString().trim();
//        String username = usernameField.getText().toString().trim();
//        String password = passwordField.getText().toString();
//        String confirmPassword = confirmPasswordField.getText().toString();
//        String firstName = firstNameField.getText().toString().trim();
//        String lastName = lastNameField.getText().toString().trim();
//
//        // checking if the values are provided and password and confirmPassword are equal
//        if ((!TextUtils.isEmpty(email)
//                && !TextUtils.isEmpty(username)
//                && !TextUtils.isEmpty(password)
//                && !TextUtils.isEmpty(confirmPassword)
//                && !TextUtils.isEmpty(firstName)
//                && !TextUtils.isEmpty(lastName))
//                && (password.equals(confirmPassword))) {
//
//            //getting a unique id using push().getKey() method
//            //it will create a unique id and we will use it as the Primary key for our User
//            String id = databaseUsers.push().getKey();
//
//            //creating a User Object
//            User user;
//
//            if (role.equals("Client")) {
//                user = new Client(email, username, password, firstName, lastName);
//            } else {
//                user = new Employee(email, username, password, firstName, lastName);
//            }
//
//            // Saving the User
//            assert id != null;
//            databaseUsers.child(id).setValue(user);
//
//            //displaying a success toast
//            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
//        } else {
//            //if the value is not given displaying a toast
//            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
//        }
//    }

    private void registerUser() {
        String role = spinner.getSelectedItem().toString().trim();
        String email = emailField.getText().toString().trim();
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        String firstName = firstNameField.getText().toString().trim();
        String lastName = lastNameField.getText().toString().trim();

        if (email.isEmpty()) {
            emailField.setError("Email is required!");
            emailField.requestFocus();
            return;
        }

        if (!isValidEmail(email)) {
            emailField.setError("Please provide valid email!");
            emailField.requestFocus();
            return;
        }

        if (firstName.isEmpty()) {
            firstNameField.setError("First name is required!");
            firstNameField.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            lastNameField.setError("Last name is required!");
            lastNameField.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            usernameField.setError("Username is required!");
            usernameField.requestFocus();
            return;
        }

        if (password.trim().isEmpty()) {
            passwordField.setError("Password is required!");
            passwordField.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordField.setError("Confirm password does not match password!");
            confirmPasswordField.requestFocus();
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            User user;
                            if (role.equals("Client")) {
                                user = new Client(email, username, password, firstName, lastName);
                            } else {
                                user = new Employee(email, username, password, firstName, lastName);
                            }

                            databaseUsers.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUp.this,"Sign up successful!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(SignUp.this, "Sign up failed!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignUp.this, "Sign up failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}