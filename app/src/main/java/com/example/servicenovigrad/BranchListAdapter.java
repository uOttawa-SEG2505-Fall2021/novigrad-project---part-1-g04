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

public class BranchListAdapter extends ArrayAdapter {

    private final Activity context;
    List<Branch> branchList;

    public BranchListAdapter(Activity context, List<Branch> branchList) {
        super(context, R.layout.activity_branch_list_adapter, branchList);
        this.context = context;
        this.branchList = branchList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View listItemView = inflater.inflate(R.layout.activity_branch_list_adapter, null, true);

        TextView branchName = listItemView.findViewById(R.id.branchName_text);

        Branch branch = branchList.get(position);

        branchName.setText(branch.getBranchName());

        return listItemView;
    }
}