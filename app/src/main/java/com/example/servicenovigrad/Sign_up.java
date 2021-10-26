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

public class Sign_up extends AppCompatActivity //implements AdapterView.OnItemSelectedListener {
{
    private static final String[] users = {"Employee", "Client"};
    static ArrayList<User> userList = new ArrayList<>();

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
        // Save first letter of role
        char role = spinner_su.getSelectedItem().toString().toLowerCase().charAt(0);

        //GO TO WELCOME PAGE AFTER CLICKING "SIGN UP"
        signupBtn_su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHECK IF CLIENT EXISTS OR NOT (IF CLIENT HAS ROLE, THEN IT EXISTS)
                if(authentication() == 'z') {
                    //VERIFY IF PASSWORD MATCHES CONFIRMED PASSWORD
                    if (password_su.getText().toString().equals(confirmPassword_su.getText().toString())) {
                        userList.add(new User(email_su.getText().toString(), username_su.getText().toString(), password_su.getText().toString(), role)); //VERIFY OPTION SELECTED FROM SPINNER
                        onWelcomePageClient(v);
                        Toast.makeText(Sign_up.this, "Sign up successful.", Toast.LENGTH_SHORT).show();
                    }
                    //DISPLAY MESSAGE THAT SAYS PASSWORDS DON'T MATCH
                    else if (!password_su.getText().toString().equals(confirmPassword_su.getText().toString())) {
                        Toast.makeText(Sign_up.this, "Please make sure you passwords match.", Toast.LENGTH_SHORT).show();
                    }
                }
                //DISPLAY MESSAGE THAT SAYS USERNAME ALREADY EXISTS
                else if(authentication() == 'y') {
                    Toast.makeText(Sign_up.this, "Username already exists.", Toast.LENGTH_SHORT).show();
                }

                //DISPLAYS MESSAGE THAT USER ALREADY EXISTS
                else {
                    Toast.makeText(Sign_up.this,"User already exists. Try logging in.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //RETURN ROLE OF USER OR 'Z' IF NON EXISTENT
    public char authentication() {
        char role = 'z';
        for(int i=0; i<userList.size(); i++) {
            if(username_su.getText().toString().equals(userList.get(i).getUsername().toString())) {
                role = 'y';
                if(password_su.getText().toString().equals(userList.get(i).getPassword().toString())) {
                    role = userList.get(i).getRole();
                }
            }
        }
        return role;
    }

    //SWITCH TO WELCOME PAGE FOR USER ACTIVITY
    public void onWelcomePageClient(View view) {
        Intent intent = new Intent(getApplicationContext(), welcomePage_client.class);
        intent.putExtra("USERNAME",username_su.getText().toString());
        startActivityForResult(intent,0);
    }

    //SWITCH TO WELCOME PAGE FOR EMPLOYEE ACTIVITY
    public void onWelcomePageEmployee(View view) {
        Intent intent = new Intent(getApplicationContext(), welcomePage_employee.class);
        intent.putExtra("USERNAME",username_su.getText().toString());
        startActivityForResult(intent,0);
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
