package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ViewBranchesFromServiceForClient extends AppCompatActivity {

    private TextView displayServiceName;
    private ListView branchesListView;
    private Button goBackBtn;
    private String serviceName, email, branchServices;
    private List<Branch> branchList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_branches_from_service_for_client);

        branchesListView = findViewById(R.id.DisplayBranchesListView);
        displayServiceName = findViewById(R.id.displayServiceName);
        goBackBtn = findViewById(R.id.goBack_button);
        serviceName = getIntent().getStringExtra("serviceName");
        displayServiceName.setText("Branch name: " + serviceName);
        email = getIntent().getStringExtra("email");

        branchList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Branches");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Branch branch = dataSnapshot.getValue(Branch.class);
                    assert branch != null;
                    //branchList.add(branch.getBranchName());
                    databaseReference.child(Objects.requireNonNull(branch.getBranchName())).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.isSuccessful()) {
                                DataSnapshot datasnap = task.getResult();
                                branchServices = String.valueOf(datasnap.child("services").getValue());
                                branchServices = branchServices.substring(1);
                                //Remove last character "]"
                                branchServices = branchServices.substring(0, branchServices.length() - 1);
                                //Convert string to arrayList
                                String[] serviceArray = branchServices.split(", ");
                                if(Arrays.asList(serviceArray).contains(serviceName)) {
                                    branchList.add(branch);
                                }

                                ListAdapter adapter = new BranchListAdapter(ViewBranchesFromServiceForClient.this, branchList);
                                //attaching adapter to the ListView
                                branchesListView.setAdapter(adapter);

                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        branchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Branch branch = (Branch) branchesListView.getItemAtPosition(position);
                String branchName = branch.getBranchName().trim();

                Intent intent = new Intent(ViewBranchesFromServiceForClient.this, ClientInformation.class);
                intent.putExtra("email", email);
                intent.putExtra("branchName", branchName);
                intent.putExtra("serviceName", serviceName);
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