package dev.mobile.bai2.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dev.mobile.bai2.R;

public class EventMonitorActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextPlace, editTextDate, editTextTime;
    private Calendar dateCalendar = Calendar.getInstance();
    private Calendar timeCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_monitor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeUIComponents();
        populateFieldsFromIntent();
        setFieldListeners();
    }

    private void initializeUIComponents() {
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextPlace = findViewById(R.id.editTextPlace);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
    }

    private void populateFieldsFromIntent() {
        Intent intent = getIntent();
        editTextTitle.setText(intent.getStringExtra("title"));
        editTextPlace.setText(intent.getStringExtra("place"));
        editTextDate.setText(intent.getStringExtra("date"));
        editTextTime.setText(intent.getStringExtra("time"));
    }

    private void setFieldListeners() {
        editTextPlace.setOnClickListener(v -> showPlacePicker());
        editTextDate.setOnClickListener(v -> showDatePicker());
        editTextTime.setOnClickListener(v -> showTimePicker());
    }

    private void showPlacePicker() {
        String[] places = {"C102", "C301", "C407", "C102", "C702"};
        new AlertDialog.Builder(this)
                .setTitle("Choose a room")
                .setItems(places, (dialog, which) -> editTextPlace.setText(places[which]))
                .show();
    }

    private void showDatePicker() {
        int year = dateCalendar.get(Calendar.YEAR);
        int month = dateCalendar.get(Calendar.MONTH);
        int day = dateCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {

            dateCalendar.set(Calendar.YEAR, selectedYear);
            dateCalendar.set(Calendar.MONTH, selectedMonth);
            dateCalendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            editTextDate.setText(format.format(dateCalendar.getTime()));

        }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        int hour = timeCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = timeCalendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this, (view, selectedHour, selectedMinute) -> {

            timeCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
            timeCalendar.set(Calendar.MINUTE, selectedMinute);

            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
            editTextTime.setText(format.format(timeCalendar.getTime()));

        }, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_monitor_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuItemSave && validateData()) {
            returnResult();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("title", editTextTitle.getText().toString());
        returnIntent.putExtra("place", editTextPlace.getText().toString());
        returnIntent.putExtra("date", editTextDate.getText().toString());
        returnIntent.putExtra("time", editTextTime.getText().toString());

        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private boolean validateData() {
        if (isEmptyField(editTextTitle, "Title cannot be empty")) return false;
        if (isEmptyField(editTextPlace, "Place cannot be empty")) return false;
        if (isEmptyField(editTextDate, "Date cannot be empty")) return false;
        if (isEmptyField(editTextTime, "Time cannot be empty")) return false;
        return true;
    }

    private boolean isEmptyField(EditText field, String errorMsg) {
        if (field.getText().toString().trim().isEmpty()) {
            field.setError(errorMsg);
            return true;
        } else {
            field.setError(null);
            return false;
        }
    }
}

