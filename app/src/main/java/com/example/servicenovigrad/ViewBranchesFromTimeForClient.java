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

public class ViewBranchesFromTimeForClient extends AppCompatActivity {

    ListView branchListView;
    Button goBackBtn;

    private String email;
    private int selectedHourBranch, selectedMinuteBranch;
    private String selectedDayBranch;
    private List<Branch> branchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_branches_with_time_for_client);

        branchListView = findViewById(R.id.branchListView);
        goBackBtn = findViewById(R.id.goBackButton);

        selectedHourBranch = getIntent().getIntExtra("selectedHourBranch", selectedHourBranch);
        selectedMinuteBranch = getIntent().getIntExtra("selectedMinuteBranch", selectedMinuteBranch);
        selectedDayBranch = getIntent().getStringExtra("selectedDayBranch");
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
                    assert branch != null;
                    //getting openDays
                    ArrayList<String> openDays = branch.getOpenDays();
                    //getting open hours
                    int startHour = branch.getStartHour();
                    int startMinute = branch.getStartMinute();
                    int endHour = branch.getEndHour();
                    int endMinute = branch.getEndMinute();

                    if (openDays.contains(selectedDayBranch)) {
                        if (selectedHourBranch >= startHour && selectedHourBranch <= endHour) {
                            if (selectedMinuteBranch >= startMinute && selectedMinuteBranch <= endMinute) {
                                //adding branch to the List
                                branchList.add(branch);
                            }
                        }
                    }
                }

                //creating adapter
                ListAdapter adapter = new BranchListAdapter(ViewBranchesFromTimeForClient.this, branchList);
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
                Intent intent = new Intent(ViewBranchesFromTimeForClient.this,
                        ViewServicesFromBranchForClient.class);
                intent.putExtra("branchName", branchName);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        //GO BACK TO ADMIN WELCOME PAGE AFTER CLICKING "GO BACK"
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}