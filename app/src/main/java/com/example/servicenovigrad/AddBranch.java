package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.StringTokenizer;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBranch extends AppCompatActivity {

    private Button continueBtn, cancelBtn;
    private EditText branchNameText, phoneNumberText, addressText;
    private String branchName, phoneNumber, address, getBranchName, getPhoneNumber, getAddress, username;
    private final String spacesAndHyphenRegex = "^(1-)?\\d{3}-\\d{3}-\\d{4}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

        continueBtn = findViewById(R.id.continue_button);
        cancelBtn = findViewById(R.id.cancel_button);
        branchNameText = findViewById(R.id.branchName_text);
        phoneNumberText = findViewById(R.id.phoneNumber_text);
        addressText = findViewById(R.id.address_text);

        username = getIntent().getStringExtra("USERNAME");

        //Retrieve fields if 'Go back' is pressed on BranchAvailability
        getBranchName = getIntent().getStringExtra("getBranchName");
        getPhoneNumber = getIntent().getStringExtra("getPhoneNumber");
        getAddress = getIntent().getStringExtra("getAddress");

        continueBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO check format of address
                if (branchNameText.getText().toString().trim().equals("")) {
                    Toast.makeText(AddBranch.this, "Please enter a branch name", Toast.LENGTH_SHORT).show();
                } else if (!validatePhoneNumber(phoneNumberText.getText().toString().trim())) {
                    Toast.makeText(AddBranch.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                } else if (!validateAddress(addressText.getText().toString().trim())) {
                    Toast.makeText(AddBranch.this, "Please enter a valid address", Toast.LENGTH_SHORT).show();
                } else {
                    //Get string for all fields
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
                Intent intent = new Intent(AddBranch.this, WelcomePageEmployee.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }

    //If the employee presses 'Go back' in BranchAvailability, restore the editTexts
    @Override
    public void onResume() {
        super.onResume();
        branchNameText.setText(getBranchName);
        phoneNumberText.setText(getPhoneNumber);
        addressText.setText(getAddress);
    }

    //Check if phone number is the following format: XXX-XXX-XXXX
    public boolean validatePhoneNumber(String phoneNumber) {
        Pattern PHONE_REGEX = Pattern.compile(spacesAndHyphenRegex);
        final Matcher matcher = PHONE_REGEX.matcher(phoneNumber);
        return matcher.matches();
    }

    // Check if address is the following format: 1234 Street, City, Province
    public boolean validateAddress(String address) {
        Pattern pattern = Pattern.compile("^(\\d+) ([A-Za-zÀ-ÿ '-]+), ([A-Za-zÀ-ÿ '-]+), ([A-Za-zÀ-ÿ '-]+)$");
        Matcher matcher = pattern.matcher(address);

        if (matcher.find()) {
            String street = Objects.requireNonNull(matcher.group(2)).trim();
            String city = Objects.requireNonNull(matcher.group(3)).trim();
            String province = Objects.requireNonNull(matcher.group(4)).trim();

            // Return false if street, city or province is empty
            if (street.isEmpty() || city.isEmpty() || province.isEmpty()) {
                return false;
            }
        }

        return matcher.matches();
    }
}