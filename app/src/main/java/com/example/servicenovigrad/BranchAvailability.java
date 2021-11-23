package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class BranchAvailability extends AppCompatActivity {

    private Button openTime, closeTimeBtn, continueBtn, goBackBtn;
    private ListView daysListView;
    private int startHour, startMinute, endHour, endMinute;
    private String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private boolean mon, tue, wed, thu, fri, sat, sun;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> optionsSelected;
    private String branchName, phoneNumber, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_availability);

        openTime = findViewById(R.id.startTime_button);
        closeTimeBtn = findViewById(R.id.closeTime_button);
        daysListView = findViewById(R.id.daysOfTheWeekListView);
        goBackBtn = findViewById(R.id.goBack_button);
        continueBtn = findViewById(R.id.continue_button);

        //Get info from previous activity
        branchName = getIntent().getStringExtra("branchName");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        address = getIntent().getStringExtra("address");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, daysOfTheWeek);
        daysListView.setAdapter(adapter);
        optionsSelected = new ArrayList<String>();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsSelected.clear();
                // reset boolean values
                mon = tue = wed = thu = fri = sat = sun = false;

                //Add selected elements to arraylist
                for (int i = 0; i < daysListView.getCount(); i++) {
                    if (daysListView.isItemChecked(i)) {
                        optionsSelected.add(daysListView.getItemAtPosition(i).toString());
                    }
                }

                //Make selected element = true
                for (int i = 0; i < optionsSelected.size(); i++) {
                    switch (optionsSelected.get(i)) {
                        case "Monday":
                            mon = true;
                            break;
                        case "Tuesday":
                            tue = true;
                            break;
                        case "Wednesday":
                            wed = true;
                            break;
                        case "Thursday":
                            thu = true;
                            break;
                        case "Friday":
                            fri = true;
                            break;
                        case "Saturday":
                            sat = true;
                            break;
                        case "Sunday":
                            sun = true;
                            break;
                    }
                }

                //Convert text of button to string
                String openingBtnText = openTime.getText().toString();
                String closingBtnText = closeTimeBtn.getText().toString();

                //Check if elements required are goods
                if (!mon && !tue && !wed && !thu && !fri && !sat && !sun) {
                    Toast.makeText(BranchAvailability.this, "Please select at least one day of the week", Toast.LENGTH_SHORT).show();
                } else if (openingBtnText.equalsIgnoreCase("select opening time")) {
                    Toast.makeText(BranchAvailability.this, "Please select the opening time", Toast.LENGTH_SHORT).show();
                } else if (closingBtnText.equalsIgnoreCase("select closing time")) {
                    Toast.makeText(BranchAvailability.this, "Please select the closing time", Toast.LENGTH_SHORT).show();
                } else if (endHour<=startHour && endMinute<=startMinute) {
                    Toast.makeText(BranchAvailability.this, "The opening needs to be before the closing time", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(BranchAvailability.this, SelectServiceForBranch.class);

                    //Save of the information for next activity
                    intent.putExtra("branchName", branchName);
                    intent.putExtra("phoneNumber", phoneNumber);
                    intent.putExtra("address", address);
                    intent.putExtra("startHour", startHour);
                    intent.putExtra("startMinute", startMinute);
                    intent.putExtra("endHour", endHour);
                    intent.putExtra("endMinute", endMinute);
                    intent.putExtra("mon", mon);
                    intent.putExtra("tue", tue);
                    intent.putExtra("wed", wed);
                    intent.putExtra("thu", thu);
                    intent.putExtra("fri", fri);
                    intent.putExtra("sat", sat);
                    intent.putExtra("sun", sun);
                    startActivity(intent);

                }
            }
        });

        //Check for button press for opening time
        openTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener onStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        startHour = selectedHour;
                        startMinute = selectedMinute;
                        openTime.setText(String.format(Locale.getDefault(),"%02d:%02d", startHour, startMinute));
                    }
                };
                TimePickerDialog startTimePickerDialog = new TimePickerDialog(BranchAvailability.this, onStartTimeSetListener, startHour, startMinute, false);
                startTimePickerDialog.setTitle("Select opening time");
                startTimePickerDialog.show();
            }
        });

        //Check for button press for closing time
        closeTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener onCloseTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int startHour, int selectedMinute) {
                        endHour = startHour;
                        endMinute = selectedMinute;
                        closeTimeBtn.setText(String.format(Locale.getDefault(),"%02d:%02d", endHour, endMinute));
                    }
                };
                TimePickerDialog startTimePickerDialog = new TimePickerDialog(BranchAvailability.this, onCloseTimeSetListener, endHour, endMinute, false);
                startTimePickerDialog.setTitle("Select closing time");
                startTimePickerDialog.show();
            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchAvailability.this, AddBranch.class);
                intent.putExtra("getBranchName", branchName);
                intent.putExtra("getPhoneNumber", phoneNumber);
                intent.putExtra("getAddress", address);
                startActivity(intent);
            }
        });
    }

    public void onGoBack(View view) {
        Intent intent = new Intent(getApplicationContext(), AddBranch.class);
        startActivityForResult(intent, 0);
    }



}