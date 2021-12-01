package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectBranchForClient extends AppCompatActivity {

    private ListView branchesListView;
    private Button goBackBtn;
    private List<Branch> branchList;
    private DatabaseReference databaseReference;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_branch_for_client);

        branchesListView = findViewById(R.id.BranchesForClientsListView);
        goBackBtn = findViewById(R.id.goBack_button);
        email = getIntent().getStringExtra("email");
        branchList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Branches");

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clearing the previous List
                branchList.clear();

                //iterating through all the nodes
                for (DataSnapshot branchDatasnap : snapshot.getChildren()) {
                    //getting branch
                    Branch branch = branchDatasnap.getValue(Branch.class);
                    //adding branch to the List
                    branchList.add(branch);
                }

                //creating adapter
                ListAdapter adapter = new BranchListAdapter(SelectBranchForClient.this, branchList);
                //attaching adapter to the ListView
                branchesListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        branchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Branch branch = (Branch) branchesListView.getItemAtPosition(position);
                String branchName = branch.getBranchName().trim();
                Intent intent = new Intent(SelectBranchForClient.this, SelectServiceForClient.class);
                intent.putExtra("branchName", branchName);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

    }
}