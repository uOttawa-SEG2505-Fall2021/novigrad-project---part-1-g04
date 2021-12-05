package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;

public class SearchBranchByTime extends AppCompatActivity {

    Button selectTimeBtn, confirmBtn, goBackBtn;
    Spinner daysSpinner;

    private String email;
    private int selectedHourBranch, selectedMinuteBranch;
    private String selectedDayBranch;
    private final String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_branches_by_time);

        daysSpinner = findViewById(R.id.spinner);
        selectTimeBtn = findViewById(R.id.time_button);
        confirmBtn = findViewById(R.id.confirm_search_button);
        goBackBtn = findViewById(R.id.cancel_search_button);
        email = getIntent().getStringExtra("email");

        // Create an adapter to describe how the items are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, daysOfTheWeek);
        // Set the spinner's adapter to the previously created one
        daysSpinner.setAdapter(adapter);

        //Change the color of selected item on the spinner
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0xFF03DAC5);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        daysSpinner.setOnItemSelectedListener(listener);

        //Check for button press for opening time
        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener onStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        selectedHourBranch = selectedHour;
                        selectedMinuteBranch = selectedMinute;
                        selectTimeBtn.setText(String.format(Locale.getDefault(),
                                "%02d:%02d",selectedHour, selectedMinute));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        SearchBranchByTime.this, onStartTimeSetListener, selectedHourBranch,
                        selectedMinuteBranch, false);
                timePickerDialog.setTitle("Select time");
                timePickerDialog.show();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDayBranch = daysSpinner.getSelectedItem().toString().trim();

                if (selectTimeBtn.getText().toString().equalsIgnoreCase("select time")) {
                    Toast.makeText(SearchBranchByTime.this, "Please select the time.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SearchBranchByTime.this, ViewBranchesFromTimeForClient.class);
                    intent.putExtra("email", email);
                    intent.putExtra("selectedHourBranch", selectedHourBranch);
                    intent.putExtra("selectedMinuteBranch", selectedMinuteBranch);
                    intent.putExtra("selectedDayBranch", selectedDayBranch);
                    startActivity(intent);
                }
            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}