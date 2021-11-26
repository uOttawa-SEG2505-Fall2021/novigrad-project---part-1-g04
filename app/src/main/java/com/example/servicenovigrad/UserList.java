package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {

    private ListView userListView;
    private List<User> userList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Button goBackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userListView = findViewById(R.id.userListView);
        goBackButton = findViewById(R.id.goBackButton);
        userList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                for(DataSnapshot userDatasnap : dataSnapshot.getChildren()) {
                    User users = userDatasnap.getValue(User.class);
                    userList.add(users);
                }

                ListAdapter adapter = new UserListAdapter(UserList.this, userList);
                userListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        userListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView emailField = (TextView) view.findViewById(R.id.textViewEmail);
                TextView passwordField = (TextView) view.findViewById(R.id.textViewPassword);

                String userEmail = emailField.getText().toString().trim();
                String userPassword = passwordField.getText().toString();
                System.out.println(userEmail);
                System.out.println(userPassword);
                mAuth = FirebaseAuth.getInstance();
                System.out.println(userEmail.equals("admin@admin.com"));


                if (!userEmail.equals("admin@admin.com")) {
                    mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().
                                        getCurrentUser().getUid());
                                databaseReference.removeValue();
                                FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(UserList.this, "Delete user successfully", Toast.LENGTH_SHORT).show();

//                                        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                                        DatabaseReference drUser = databaseReference.child(userID);
//                                        drUser.removeValue();


                                        } else {
                                            Toast.makeText(UserList.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(UserList.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(UserList.this, "Cannot delete admin account", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}