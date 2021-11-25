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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBranches extends AppCompatActivity {

    private ListView branchListView;
    private Button goBackButton;
    private List<Branch> branchList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_branches);

        branchListView = findViewById(R.id.branchListView);
        goBackButton = findViewById(R.id.goBack_button);
        branchList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Branches");

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

//        branchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String branchName = branchListView.getItemAtPosition(position).toString().trim();
//
//                databaseReference = FirebaseDatabase.getInstance().getReference("Branches").child(branchName);
//            }
//        });

        //GO BACK TO ADMIN WELCOME PAGE AFTER CLICKING "GO BACK"
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}