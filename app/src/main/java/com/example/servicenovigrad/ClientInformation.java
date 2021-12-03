package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class ClientInformation extends AppCompatActivity {

    private String serviceName, branchName, email;
    private ListView documentsForServiceListView;
    private Button confirmBtn, cancelBtn;
    private EditText dateOfBirthText, addressText;
    private List<String> documentList, optionsSelected;
    private DatabaseReference databaseReference;
    private boolean photoId, proofOfResidence, proofOfStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_information);

        documentsForServiceListView = findViewById(R.id.documentForServiceListView);
        confirmBtn = findViewById(R.id.confirmRequest_button);
        cancelBtn = findViewById(R.id.cancelRequest_button);
        dateOfBirthText = findViewById(R.id.dateOfBirth_text);
        addressText = findViewById(R.id.clientAddress_text);

        //Get email of client, branchName selected and serviceName selected
        email = getIntent().getStringExtra("email");
        branchName = getIntent().getStringExtra("branchName");
        serviceName = getIntent().getStringExtra("serviceName");
        documentList = new ArrayList<>();
        optionsSelected = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Services");
        databaseReference.child(Objects.requireNonNull(serviceName)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    //Check if documents for service are TRUE
                    photoId = Boolean.valueOf((Boolean) dataSnapshot.child("photoID").getValue());
                    proofOfResidence = Boolean.valueOf((Boolean) dataSnapshot.child("proofOfResidence").getValue());
                    proofOfStatus = Boolean.valueOf((Boolean) dataSnapshot.child("proofOfStatus").getValue());
                    //If document is required (true), add it to array to then add it to listview
                    if(photoId) {
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

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

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