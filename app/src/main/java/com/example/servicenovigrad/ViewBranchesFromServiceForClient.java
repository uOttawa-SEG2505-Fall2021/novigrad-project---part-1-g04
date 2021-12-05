package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewBranchesFromServiceForClient extends AppCompatActivity {

    private TextView displayServiceName;
    private ListView branchesListView;
    private Button goBackBtn;
    private String serviceName, email;
    private List<String> branchList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_branches_from_service_for_client);

        branchesListView = findViewById(R.id.DisplayServicesListView);
        displayServiceName = findViewById(R.id.displayServiceName);
        goBackBtn = findViewById(R.id.goBack_button);
        serviceName = getIntent().getStringExtra("serviceName");
        displayServiceName.setText("Branch name: " + serviceName);
        email = getIntent().getStringExtra("email");

        branchList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Branches");
        

    }
}