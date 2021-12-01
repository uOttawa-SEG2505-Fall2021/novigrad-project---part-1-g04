package com.example.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ViewRequests extends AppCompatActivity {

    private Button acceptBtn, refuseBtn, goBackBtn;
    private ListView requestListView;
    private DatabaseReference databaseReference;
    private String branchName;
    private List<String> requestList, optionsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);

        acceptBtn = findViewById(R.id.acceptRequest_button);
        refuseBtn = findViewById(R.id.refuseRequest_button);
        goBackBtn = findViewById(R.id.goBack_button);
        requestListView = findViewById(R.id.RequestListView);

        requestList = new ArrayList<>();
        optionsSelected = new ArrayList<>();
        branchName = getIntent().getStringExtra("branchName");

        databaseReference = FirebaseDatabase.getInstance().getReference("Requests").child(branchName);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestList.clear();

                //Add request info to list view
                for(DataSnapshot userDatasnap : snapshot.getChildren()) {
                    Request request = userDatasnap.getValue(Request.class);
                    if (!request.getStatus()) {
                        requestList.add("Request by " + request.getEmail() + " for service " + request.getServiceName());
                    }
                }

                //Convert arraylist to String array
                String[] requestArray = new String[requestList.size()];
                for (int i = 0; i < requestList.size(); i++) {
                    requestArray[i] = requestList.get(i);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewRequests.this,
                        android.R.layout.simple_list_item_multiple_choice, requestArray);

                //attaching adapter to the ListView
                requestListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsSelected.clear();
                //Check options selected and add to arraylist
                for (int i = 0; i < requestListView.getCount(); i++) {
                    if (requestListView.isItemChecked(i)) {
                        optionsSelected.add(requestListView.getItemAtPosition(i).toString());
                    }
                }

                //Add selected elements to array list
                for(int i=0; i<optionsSelected.size(); i++) {
                    String option = optionsSelected.get(i);
                    String email = StringUtils.substringBetween(option, "Request by ", " for service");
                    String serviceName = StringUtils.substringAfter(option, "for service ");

                    //Get hash from request and update status of request
                    Request request = new Request(email, branchName, serviceName, true);
                    String hash = request.getHash();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Requests").child(branchName).child(hash);
                    databaseReference.setValue(request);
                    Toast.makeText(ViewRequests.this, "Accepted the request(s)", Toast.LENGTH_SHORT).show();
                }

                // Return to welcomePageEmployee
                Intent intent = new Intent(ViewRequests.this, WelcomePageEmployee.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        refuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsSelected.clear();

                //Add selected elements to array list
                for (int i = 0; i < requestListView.getCount(); i++) {
                    if (requestListView.isItemChecked(i)) {
                        optionsSelected.add(requestListView.getItemAtPosition(i).toString());
                    }
                }
                //Get email and service name from list view
                for(int i=0; i<optionsSelected.size(); i++) {
                    String option = optionsSelected.get(i);
                    String email = StringUtils.substringBetween(option, "Request by ", " for service");
                    String serviceName = StringUtils.substringAfter(option, "for service ");
                    //Get hash from request and delete it
                    Request request = new Request(email, branchName, serviceName, true);
                    String hash = request.getHash();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Requests").child(branchName).child(hash);
                    databaseReference.removeValue();
                    Toast.makeText(ViewRequests.this, "Refused the request(s)", Toast.LENGTH_SHORT).show();
                }

                // Return to welcomePageEmployee
                Intent intent = new Intent(ViewRequests.this, WelcomePageEmployee.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}