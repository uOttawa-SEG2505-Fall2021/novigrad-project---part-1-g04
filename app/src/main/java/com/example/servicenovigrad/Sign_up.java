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
import android.widget.Spinner;


import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign_up extends AppCompatActivity //implements AdapterView.OnItemSelectedListener {
{
    private static final String[] users = {"Employee", "Client"};
    static ArrayList<User> userList = new ArrayList<>();
    private int id;

    Spinner spinner_su;
    EditText email_su, username_su, password_su, confirmPassword_su;
    Button signupBtn_su;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        spinner_su = findViewById(R.id.spinner);
        signupBtn_su = findViewById(R.id.signup_page_button);
        email_su = findViewById(R.id.emailField);
        username_su = findViewById(R.id.usernameField);
        password_su = findViewById(R.id.passwordField);
        confirmPassword_su = findViewById(R.id.confirmPasswordField);

        // Create an adapter to describe how the items are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, users);
        // Set the spinner's adapter to the previously created one
        spinner_su.setAdapter(adapter);

        //GO TO WELCOME PAGE AFTER CLICKING "SIGN UP"
        signupBtn_su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Credentials of new user
                String email = email_su.getText().toString();
                String username = username_su.getText().toString();
                String password = password_su.getText().toString();
                String confirmPassword = confirmPassword_su.getText().toString();
                String role = spinner_su.getSelectedItem().toString();

                //CHECK IF CLIENT EXISTS OR NOT
                if (authentication() == -1) {
                    //VERIFY IF PASSWORD MATCHES CONFIRMED PASSWORD
                    if (password.equals(confirmPassword)) {
                        //VERIFY OPTION SELECTED FROM SPINNER
                        if (role.equals("Client")) {
                            // Add new Client to userList
                            userList.add(new Client(email, username, password));
                            onWelcomePageClient(v);
                        } else {
                            // Add new Client to userList
                            userList.add(new Employee(email, username, password));
                            onWelcomePageEmployee(v);
                        }
                        Toast.makeText(Sign_up.this, "Sign up successful.", Toast.LENGTH_SHORT).show();
                    }
                    //DISPLAY MESSAGE THAT SAYS PASSWORDS DON'T MATCH
                    else {
                        Toast.makeText(Sign_up.this, "Please make sure you passwords match.", Toast.LENGTH_SHORT).show();
                    }
                }
                //DISPLAY MESSAGE THAT SAYS USERNAME ALREADY EXISTS
                else if (authentication() == -2) {
                    Toast.makeText(Sign_up.this, "Sign up failed. Username and email already exist.", Toast.LENGTH_SHORT).show();
                }

                //DISPLAYS MESSAGE THAT USER ALREADY EXISTS
                else if (authentication() == -3) {
                    Toast.makeText(Sign_up.this, "Sign up failed. Email already exists.", Toast.LENGTH_SHORT).show();
                } else if (authentication() == -4) {
                    Toast.makeText(Sign_up.this, "Sign up failed. Username already exists.", Toast.LENGTH_SHORT).show();
                } else if (authentication() == -5) {
                    Toast.makeText(Sign_up.this, "Sign up failed. Missing field.", Toast.LENGTH_SHORT).show();
                } else if (authentication() == -6) {
                    Toast.makeText(Sign_up.this, "Sign up failed. Email is not valid.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //RETURN ROLE OF USER OR 'Z' IF NON EXISTENT
    public int authentication() {
        for (int i = 0; i < userList.size(); i++) {
            //RETURN -2 IF BOTH USERNAME AND EMAIL ALREADY EXISTS
            if (username_su.getText().toString().equals(userList.get(i).getUsername()) && email_su.getText().toString().equals(userList.get(i).getEmail())) {
                return -2;
            }
            //RETURN -3 IF ONLY EMAIL ALREADY EXISTS
            else if (email_su.getText().toString().equals(userList.get(i).getEmail())) {
                return -3;
            }
            //RETURN -4 IF ONLY USERNAME ALREADY EXISTS
            else if (username_su.getText().toString().equals(userList.get(i).getUsername())) {
                return -4;
            }
            //VERIFY IF ANY FIELDS ARE EMPTY
            else if (username_su.getText().toString().equals("") || email_su.getText().toString().equals("") || password_su.getText().toString().equals("") || confirmPassword_su.getText().toString().equals("")) {
                return -5;
            } else if (!verifyEmail(email_su.getText().toString())) {
                return -6;
            }
        }
        //RETURN -1 IF CLIENT DOESN'T ALREADY EXIST
        return -1;
    }

    public boolean verifyEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    //SWITCH TO WELCOME PAGE FOR USER ACTIVITY
    public void onWelcomePageClient(View view) {
        Intent intent = new Intent(getApplicationContext(), welcomePage_client.class);
        intent.putExtra("USERNAME", username_su.getText().toString());
        startActivityForResult(intent, 0);
    }

    //SWITCH TO WELCOME PAGE FOR EMPLOYEE ACTIVITY
    public void onWelcomePageEmployee(View view) {
        Intent intent = new Intent(getApplicationContext(), welcomePage_employee.class);
        intent.putExtra("USERNAME", username_su.getText().toString());
        startActivityForResult(intent, 0);
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//        Toast.makeText(parent.getContext(), parent.getItemAtPosition(pos).toString(),
//                Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//    }
}
