package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {

    private final Activity context;
    List<User> userList;

    public UserListAdapter(Activity context, List<User> userList) {
        super(context, R.layout.activity_user_list_adapter, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View listItemView = inflater.inflate(R.layout.activity_user_list_adapter, null, true);

        TextView userEmail = listItemView.findViewById(R.id.textViewEmail);
        TextView userPassword = listItemView.findViewById(R.id.textViewPassword);

        User user = userList.get(position);

        userEmail.setText(user.getEmail());
        userPassword.setText(user.getPassword());

        return listItemView;
    }
}