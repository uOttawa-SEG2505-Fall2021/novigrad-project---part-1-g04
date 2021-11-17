package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ServiceListAdapter extends ArrayAdapter {

    private final Activity context;
    List<Service> serviceList;

    public ServiceListAdapter(Activity context, List<Service> serviceList) {
        super(context, R.layout.activity_service_list_adapter, serviceList);
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View listItemView = inflater.inflate(R.layout.activity_user_list_adapter, null, true);

        TextView serviceName = listItemView.findViewById(R.id.textViewServiceName);

        Service service = serviceList.get(position);

        serviceName.setText(service.getServiceName());

        return listItemView;
    }
}