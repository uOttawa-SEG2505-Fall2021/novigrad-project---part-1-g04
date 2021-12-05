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

public class ViewBranches extends AppCompatActivity {

    ListView branchListView;
    Button goBackButton;

    private List<Branch> branchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_branches);

        branchListView = findViewById(R.id.branchListView);
        goBackButton = findViewById(R.id.goBackButton);
        branchList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Branches");

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
                ListAdapter adapter = new BranchListAdapter(ViewBranches.this, branchList);
                //attaching adapter to the ListView
                branchListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        branchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Branch branch = (Branch) branchListView.getItemAtPosition(position);
                String branchName = branch.getBranchName().trim();
                Intent intent = new Intent(ViewBranches.this, ConnectToBranch.class);
                intent.putExtra("branchName", branchName);
                startActivity(intent);
            }
        });

        //GO BACK TO ADMIN WELCOME PAGE AFTER CLICKING "GO BACK"
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}