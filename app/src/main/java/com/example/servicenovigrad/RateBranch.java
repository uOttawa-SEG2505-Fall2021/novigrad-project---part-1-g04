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
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RateBranch extends AppCompatActivity {

    ListView branchListView;
    Button submitBtn, cancelBtn;
    RatingBar ratingBar;

    private List<Branch> branchList;
    private String branchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_branch);

        branchListView = findViewById(R.id.branchListView);
        submitBtn = findViewById(R.id.submit_rating_button);
        cancelBtn = findViewById(R.id.cancel_rating_button);
        ratingBar = findViewById(R.id.ratingBar);

        branchList = new ArrayList<>();

        ratingBar.setNumStars(5);

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
                ListAdapter adapter = new BranchListAdapter(RateBranch.this, branchList);
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
                branchName = branch.getBranchName().trim();
                Toast.makeText(RateBranch.this, "You are rating " + branchName, Toast.LENGTH_SHORT).show();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (branchName != null) {
                    //get rating selected
                    float rating = ratingBar.getRating();
                    //get username
                    String username = getIntent().getStringExtra("username");
                    //add rating to branch in database
                    databaseReference.child(branchName).child("ratings").child(username).setValue(rating);
                    Toast.makeText(RateBranch.this, "Branch rating submitted successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RateBranch.this, "Please select a branch.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}