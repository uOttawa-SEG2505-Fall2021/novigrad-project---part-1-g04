package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBranch extends AppCompatActivity {

    private Button continueBtn, cancelBtn;
    private EditText branchNameText, phoneNumberText, addressText;
    private String branchName, phoneNumber, address, getBranchName, getPhoneNumber, getAddress, username;
    private final String spacesAndHyphenRegex = "^(1-)?\\d{3}-\\d{3}-\\d{4}$";
    private DatabaseReference databaseReference;
    private List<String> branchList;
    private boolean match, confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

        continueBtn = findViewById(R.id.cancelRequest_button);
        cancelBtn = findViewById(R.id.CancelRequest_button);
        branchNameText = findViewById(R.id.branchName_text);
        phoneNumberText = findViewById(R.id.phoneNumber_text);
        addressText = findViewById(R.id.address_text);

        username = getIntent().getStringExtra("USERNAME");
        branchList = new ArrayList<>();

        //Retrieve fields if 'Go back' is pressed on BranchAvailability
        getBranchName = getIntent().getStringExtra("getBranchName");
        getPhoneNumber = getIntent().getStringExtra("getPhoneNumber");
        getAddress = getIntent().getStringExtra("getAddress");

        continueBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO check format of address
                if (branchNameText.getText().toString().trim().equals("")) {
                    branchNameText.setError("Please enter a branch name.");
                    branchNameText.requestFocus();
                } else if (!validatePhoneNumber(phoneNumberText.getText().toString().trim())) {
                    phoneNumberText.setError("Please enter a valid phone number.");
                    phoneNumberText.requestFocus();
                } else if (!validateAddress(addressText.getText().toString().trim())) {
                    addressText.setError("Please enter a valid address.");
                    addressText.requestFocus();
                } else {

                    //Get string for all fields
                    branchName = branchNameText.getText().toString().trim();
                    phoneNumber = phoneNumberText.getText().toString().trim();
                    address = addressText.getText().toString().trim();

                    databaseReference = FirebaseDatabase.getInstance().getReference("Branches");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            branchList.clear();
                            confirm = true;
                            match = false;
                            if(snapshot.hasChild(branchName)) {
                                branchNameText.setError("This branch name has already been used.");
                                branchNameText.requestFocus();
                            } else {
                                //iterating through all the nodes
                                for (DataSnapshot branchDatasnap : snapshot.getChildren()) {
                                    //getting branch
                                    Branch branch = branchDatasnap.getValue(Branch.class);
                                    //adding branch to the List
                                    assert branch != null;
                                    branchList.add(branch.getBranchName());

                                    String confirmPhoneNumber = String.valueOf(branchDatasnap.child("phoneNumber").getValue());
                                    String confirmAddress = String.valueOf(branchDatasnap.child("address").getValue());
                                    //Check if phone number is unique
                                    if (confirmPhoneNumber.equals(phoneNumber)) {
                                        phoneNumberText.setError("Phone number is already associated with a branch.");
                                        phoneNumberText.requestFocus();
                                        match = true;
                                        break;
                                        //Check if address is unique
                                    } else if(confirmAddress.equals(address)) {
                                        addressText.setError("Address is already associated with a branch.");
                                        addressText.requestFocus();
                                        match = true;
                                        break;
                                    }

                                }

                                //Check if all fields have been verified
                                if(!match && confirm) {
                                    Intent intent = new Intent(AddBranch.this, BranchAvailability.class);
                                    //Save of the information for time and date
                                    intent.putExtra("branchName", branchName);
                                    intent.putExtra("phoneNumber", phoneNumber);
                                    intent.putExtra("address", address);
                                    startActivity(intent);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            }
        });

        //If cancel button is pressed, go back to welcomePageEmployee
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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