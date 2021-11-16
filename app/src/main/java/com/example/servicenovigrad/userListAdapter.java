package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class userListAdapter extends ArrayAdapter {

    private Activity context;
    List<User> userList;

    public userListAdapter(Activity context, List<User> userList) {
        super(context, R.layout.activity_user_list_adapter, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.activity_user_list_adapter, null, true);

        TextView userEmail = listItemView.findViewById(R.id.textViewEmail);
        TextView userPassword = listItemView.findViewById(R.id.textViewPassword);

        User users = userList.get(position);

        userEmail.setText(users.getEmail());
        userPassword.setText(users.getPassword());

        return listItemView;

    }
}