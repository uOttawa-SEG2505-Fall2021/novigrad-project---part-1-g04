package com.example.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class DeleteBranch extends AppCompatActivity {

    private Button confirmBtn, cancelBtn;
    private EditText branchNameText, phoneNumberText;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_branch);

        confirmBtn = findViewById(R.id.delete_confirm_button);
        cancelBtn = findViewById(R.id.delete_cancel_button);
        branchNameText = findViewById(R.id.deleteBranchName_text);
        phoneNumberText = findViewById(R.id.deletePhoneNumber_text);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String branchName = branchNameText.getText().toString().trim();
                String phoneNumber = phoneNumberText.getText().toString().trim();

                if(!branchName.isEmpty() && !phoneNumber.isEmpty()) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Branches");
                    databaseReference.child(Objects.requireNonNull(branchName)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.isSuccessful()) {
                                if(Objects.requireNonNull(task.getResult().exists())) {
                                    DataSnapshot dataSnapshot = task.getResult();
                                    String confirmPhoneNumber = String.valueOf(dataSnapshot.child("phoneNumber").getValue());

                                    if(confirmPhoneNumber.equals(phoneNumber)) {
                                        Toast.makeText(DeleteBranch.this, "Deleted service successfully.", Toast.LENGTH_SHORT).show();
                                        databaseReference.child(branchName).removeValue();

                                        // Return to welcomePageEmployee
                                        Intent intent = new Intent(DeleteBranch.this, WelcomePageEmployee.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(DeleteBranch.this, "Phone number doesn't exist.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(DeleteBranch.this, "Branch doesn't exist.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(DeleteBranch.this, "Failed to delete branch.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else if(branchName.isEmpty()){
                    Toast.makeText(DeleteBranch.this, "Branch name is empty.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DeleteBranch.this, "Phone number is empty.", Toast.LENGTH_SHORT).show();
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