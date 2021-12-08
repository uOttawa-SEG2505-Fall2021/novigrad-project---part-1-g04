package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientInformation extends AppCompatActivity {

    private String serviceName, branchName, email, dateOfBirth;
    private ListView documentsForServiceListView;
    private Button confirmBtn, cancelBtn, dateBtn;
    private EditText addressText;
    private DatePickerDialog datePickerDialog;
    private List<String> documentList, optionsSelected;
    private DatabaseReference databaseReference;
    private boolean photoID, proofOfResidence, proofOfStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_information);

        documentsForServiceListView = findViewById(R.id.documentForServiceListView);
        confirmBtn = findViewById(R.id.confirmRequest_button);
        cancelBtn = findViewById(R.id.cancelRequest_button);
        addressText = findViewById(R.id.clientAddress_text);
        dateBtn = findViewById(R.id.datePickerButton);

        //Get email of client, branchName selected, serviceName selected and datePicker
        email = getIntent().getStringExtra("email");
        branchName = getIntent().getStringExtra("branchName");
        serviceName = getIntent().getStringExtra("serviceName");
        documentList = new ArrayList<>();
        optionsSelected = new ArrayList<>();
        initDatePicker();

        databaseReference = FirebaseDatabase.getInstance().getReference("Services");
        databaseReference.child(Objects.requireNonNull(serviceName)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    //Check if documents for service are TRUE
                    photoID = Boolean.valueOf((Boolean) dataSnapshot.child("photoID").getValue());
                    proofOfResidence = Boolean.valueOf((Boolean) dataSnapshot.child("proofOfResidence").getValue());
                    proofOfStatus = Boolean.valueOf((Boolean) dataSnapshot.child("proofOfStatus").getValue());
                    //If document is required (true), add it to array to then add it to listview
                    if(photoID) {
                        documentList.add("Photo ID");
                    }
                    if(proofOfResidence) {
                        documentList.add("Proof of Residence");
                    }
                    if(proofOfStatus) {
                        documentList.add("Proof of Status");
                    }

                //Convert arraylist to String array
                String[] serviceArray = new String[documentList.size()];
                for (int i = 0; i < documentList.size(); i++) {
                    serviceArray[i] = documentList.get(i);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ClientInformation.this,
                        android.R.layout.simple_list_item_multiple_choice, serviceArray);

                //attaching adapter to the ListView
                    documentsForServiceListView.setAdapter(adapter);
                }
            }
        });


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request;
                String address = addressText.getText().toString().trim();
                optionsSelected.clear();
                // reset boolean values
                proofOfResidence = proofOfStatus = photoID = false;

                //Add selected options to arraylist
                for (int i = 0; i < documentsForServiceListView.getCount(); i++) {
                    if (documentsForServiceListView.isItemChecked(i)) {
                        optionsSelected.add(documentsForServiceListView.getItemAtPosition(i).toString());
                    }
                }

                //Get string from date button
                String dateOfBirthText = dateBtn.getText().toString();
                if(address.isEmpty()) {
                    addressText.setError("Please enter your address");
                    addressText.requestFocus();
                    return;
                } else if(dateOfBirthText.equalsIgnoreCase("Select date of birth")) {
                    Toast.makeText(ClientInformation.this, "Please select your date of birth.", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!validateAddress(address)) {
                    addressText.setError("Please enter a valid address");
                    addressText.requestFocus();
                    return;
                }

                //Check if all options have been selected
                if(optionsSelected.size() == documentList.size()) {
                    //Set boolean allDocuments to true if all documents have been selected
                    request = new Request(email, branchName, serviceName, dateOfBirth, address, false, true);
                } else {
                    //Set boolean allDocuments to false if not all documents have been selected
                    request = new Request(email, branchName, serviceName, dateOfBirth, address, false, false);
                }

                databaseReference = FirebaseDatabase.getInstance().getReference("Requests");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(branchName).hasChild(request.getHash())) {
                            Toast.makeText(ClientInformation.this, "You already submitted a request for this service.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            //Add request to database
                            databaseReference.child(Objects.requireNonNull(branchName)).child(request.getHash()).
                                    setValue(request);
                            Toast.makeText(ClientInformation.this, "Submitted request successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ClientInformation.this, WelcomePageClient.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Validate address using regex
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

    //Code bellow is for date picker
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                dateOfBirth = makeDateString(day, month, year);
                dateBtn.setText(dateOfBirth);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //Make sure user can't pick a date in the future
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //Default, should never happen
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}