package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBranch extends AppCompatActivity {

    private Button continueBtn, cancelBtn;
    private EditText branchNameText, phoneNumberText, addressText;
    private String branchName, phoneNumber, address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

        continueBtn = findViewById(R.id.continue_button);
        cancelBtn = findViewById(R.id.cancel_button);
        branchNameText = findViewById(R.id.branchName_text);
        phoneNumberText = findViewById(R.id.phoneNumber_text);
        addressText = findViewById(R.id.address_text);



        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(branchNameText.getText().toString().trim().equals("")) {
                    Toast.makeText(AddBranch.this, "Please enter a branch name", Toast.LENGTH_SHORT).show();
                }
                //TODO check format of phone number and format of address
                else {
                    branchName = branchNameText.getText().toString().trim();
                    phoneNumber = phoneNumberText.getText().toString().trim();
                    address = addressText.getText().toString().trim();
                    Intent intent = new Intent(AddBranch.this, BranchAvailability.class);
                    //Save of the information for time and date
                    intent.putExtra("branchName", branchName);
                    intent.putExtra("phoneNumber", phoneNumber);
                    intent.putExtra("address", address);
                    startActivity(intent);
                }
            }
        });


        //If cancel button is pressed, go back to welcomePageEmployee
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoBack(v);
            }
        });

    }

    public void onGoBack(View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomePageEmployee.class);
        startActivityForResult(intent, 0);
    }

    public void onAvailability(View view) {
        Intent intent = new Intent(getApplicationContext(), BranchAvailability.class);
        startActivityForResult(intent, 0);
    }


}