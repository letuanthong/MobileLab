package dev.mobile.bai3;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddTaskActivity extends AppCompatActivity {

    EditText edtName, edtPlace, edtDate, edtTime;
    Button btnSubmit;
    int hour = 0;
    int minuteGL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtName = findViewById(R.id.edtName);
        edtPlace = findViewById(R.id.edtPlace);
        edtDate = findViewById(R.id.edtDate);
        edtTime = findViewById(R.id.edtTime);
        btnSubmit = findViewById(R.id.btnSubmit);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int indexSelected = 1;
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String place = intent.getStringExtra("place");
            String date = intent.getStringExtra("date");
            String time = intent.getStringExtra("time");
            edtName.setText(name);
            edtPlace.setText(place);
            edtDate.setText(date);
            edtTime.setText(time);
        }
        edtPlace.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
//                add list of radio
                String[] items = {
                        "C115",
                        "C116",
                        "C117",
                        "C118",
                        "C119",
                        "C120"
                };
                builder.setSingleChoiceItems(items, indexSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtPlace.setText(items[which]);
//                        indexSelected = which;
//                        set position item which is selected
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        edtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePickerDialog.setCanceledOnTouchOutside(false);
                    datePickerDialog.show();
                    datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                        edtDate.setText(dayOfMonth + "/" + month + "/" + year);
                    });
                }
            }
        });

        edtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            edtTime.setText(hourOfDay + ":" + minute);
                            hour = hourOfDay;
                            minuteGL = minute;
                        }
                    }, hour, minuteGL, true);
                    timePickerDialog.setCanceledOnTouchOutside(false);
                    timePickerDialog.show();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (edtName.getText().toString().isEmpty()) {
                    edtName.setError("Please enter name");
                } else if (edtPlace.getText().toString().isEmpty()) {
                    edtPlace.setError("Please enter place");
                } else if (edtDate.getText().toString().isEmpty()) {
                    edtDate.setError("Please enter date");
                } else if (edtTime.getText().toString().isEmpty()) {
                    edtTime.setError("Please enter time");
                } else {
                    intent.putExtra("name", edtName.getText().toString());
                    intent.putExtra("place", edtPlace.getText().toString());
                    intent.putExtra("date", edtDate.getText().toString());
                    intent.putExtra("time", edtTime.getText().toString());
//                    add to taskmodel
//                    TaskModel taskModel = new TaskModel(AddTaskActivity.this, "task.db", 1);
//                    taskModel.add(edtName.getText().toString(), edtPlace.getText().toString(), edtDate.getText().toString() + " / " + edtTime.getText().toString(), false);

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
