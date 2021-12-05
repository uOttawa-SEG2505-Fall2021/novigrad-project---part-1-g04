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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAllAddressesForClient extends AppCompatActivity {

    private ListView addressesListView;
    private Button goBackBtn;
    private String email;
    private List<String> addressList, branchList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_addresses_for_client);

        addressesListView = findViewById(R.id.AddressesForClientsListView);
        goBackBtn = findViewById(R.id.goBack_button);
        email = getIntent().getStringExtra("email");
        addressList = new ArrayList<>();
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
                addressList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Branch branch = dataSnapshot.getValue(Branch.class);
                    assert branch != null;
                    addressList.add(branch.getAddress());
                    branchList.add(branch.getBranchName());
                }

                String[] addressArray = new String[addressList.size()];
                for (int i = 0; i < addressList.size(); i++) {
                    addressArray[i] = addressList.get(i);
                }

                ArrayAdapter adapter = new ArrayAdapter(ViewAllAddressesForClient.this, android.R.layout.simple_list_item_1, addressArray);
                addressesListView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        addressesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String branchName = branchList.get(position).trim();
                Intent intent = new Intent(ViewAllAddressesForClient.this, ViewServicesFromBranchForClient.class);
                intent.putExtra("branchName", branchName);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}