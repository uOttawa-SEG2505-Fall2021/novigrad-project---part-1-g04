package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    Spinner spinner;
    EditText emailField, usernameField, passwordField,
            confirmPasswordField, firstNameField, lastNameField;
    Button signUpButton;

    List<User> users;
    static DatabaseReference databaseUsers;
    private static final String[] userTypes = {"Employee", "Client"};
    private int id;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        users = new ArrayList<User>();

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
                android.R.layout.simple_spinner_dropdown_item, userTypes);
        // Set the spinner's adapter to the previously created one
        spinner.setAdapter(adapter);

//        //GO TO WELCOME PAGE AFTER CLICKING "SIGN UP"
//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Credentials of new user
//                String role = spinner.getSelectedItem().toString();
//                String email = emailField.getText().toString();
//                String username = usernameField.getText().toString();
//                String password = passwordField.getText().toString();
//                String confirmPassword = confirmPasswordField.getText().toString();
//                String firstName = firstNameField.getText().toString();
//                String lastName = lastNameField.getText().toString();
//
//                //CHECK IF CLIENT EXISTS OR NOT
//                if (authentication() == -1) {
//                    //VERIFY IF PASSWORD MATCHES CONFIRMED PASSWORD
//                    if (password.equals(confirmPassword)) {
//                        //VERIFY OPTION SELECTED FROM SPINNER
//                        if (role.equals("Client")) {
//                            // Add new Client to userList
//                            users.add(new Client(email, username, password, firstName, lastName));
//                            onWelcomePageClient(v);
//                        } else {
//                            // Add new Employee to userList
//                            users.add(new Employee(email, username, password, firstName, lastName));
//                            onWelcomePageEmployee(v);
//                        }
//                        //DISPLAY MESSAGE OF SUCCESSFUL SIGN UP
//                        Toast.makeText(SignUp.this, "Sign up successful.",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                    //DISPLAY MESSAGE THAT SAYS PASSWORDS DON'T MATCH
//                    else {
//                        Toast.makeText(SignUp.this,
//                                "Sign up failed. Please make sure your passwords match.",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
//                //DISPLAY SIGN UP FAILED BASE ON VALUE OF AUTHENTICATION
//                else if (authentication() == -2) {
//                    Toast.makeText(SignUp.this,
//                            "Sign up failed. Username and email already exist.",
//                            Toast.LENGTH_SHORT).show();
//                } else if (authentication() == -3) {
//                    Toast.makeText(SignUp.this,
//                            "Sign up failed. Email already exists.",
//                            Toast.LENGTH_SHORT).show();
//                } else if (authentication() == -4) {
//                    Toast.makeText(SignUp.this,
//                            "Sign up failed. Username already exists.",
//                            Toast.LENGTH_SHORT).show();
//                } else if (authentication() == -5) {
//                    Toast.makeText(SignUp.this,
//                            "Sign up failed. Missing field.",
//                            Toast.LENGTH_SHORT).show();
//                } else if (authentication() == -6) {
//                    Toast.makeText(SignUp.this,
//                            "Sign up failed. Email is not valid.",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

//    //EVALUATES EVERY FIELD IN SIGN UP PAGE
//    public int authentication() {
//        for (int i = 0; i < users.size(); i++) {
//            //RETURN -2 IF BOTH USERNAME AND EMAIL ALREADY EXISTS
//            if (usernameField.getText().toString().equals(users.get(i).getUsername()) &&
//                    emailField.getText().toString().equals(users.get(i).getEmail())) {
//                return -2;
//            }
//            //RETURN -3 IF ONLY EMAIL ALREADY EXISTS
//            else if (emailField.getText().toString().equals(users.get(i).getEmail())) {
//                return -3;
//            }
//            //RETURN -4 IF ONLY USERNAME ALREADY EXISTS
//            else if (usernameField.getText().toString().equals(users.get(i).getUsername())) {
//                return -4;
//            }
//            //RETURN -5 IF AT LEAST ONE FIELD IS MISSING
//            else if (usernameField.getText().toString().trim().equals("") ||
//                    emailField.getText().toString().trim().equals("") ||
//                    passwordField.getText().toString().trim().equals("") ||
//                    confirmPasswordField.getText().toString().trim().equals("") ||
//                    firstNameField.getText().toString().trim().equals("") ||
//                    lastNameField.getText().toString().trim().equals("")) {
//                return -5;
//            //RETURN -6 IF EMAIL IS NOT STANDARD
//            } else if (!verifyEmail(emailField.getText().toString())) {
//                return -6;
//            }
//        }
//        //RETURN -1 IF CLIENT DOESN'T ALREADY EXIST
//        return -1;
//    }

    public boolean verifyEmail(String email) {
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
        if(currentUser != null){
            reload();
        }
    }

    private void reload() {
    }

    private void addUser() {
        //getting the values to save
        String role = spinner.getSelectedItem().toString().trim();
        String email = emailField.getText().toString().trim();
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        String firstName = firstNameField.getText().toString().trim();
        String lastName = lastNameField.getText().toString().trim();

        // checking if the values are provided and password and confirmPassword are equal
        if ((!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(confirmPassword)
                && !TextUtils.isEmpty(firstName)
                && !TextUtils.isEmpty(lastName))
                && (password.equals(confirmPassword))) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary key for our User
            String id = databaseUsers.push().getKey();

            //creating a User Object
            User user;

            if (role.equals("Client")) {
                user = new Client(email, username, password, firstName, lastName);
            } else {
                user = new Employee(email, username, password, firstName, lastName);
            }

            // Saving the User
            assert id != null;
            databaseUsers.child(id).setValue(user);

            //displaying a success toast
            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }
    }
}