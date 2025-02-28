package com.example.lab01_bai04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button convertButton;
    private Button clearButton;
    private EditText usEditText;
    private EditText vndEditText;
    private EditText euroEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        convertButton = findViewById(R.id.convertButton);
        clearButton = findViewById(R.id.clearButton);
        usEditText = findViewById(R.id.usEditText);
        vndEditText = findViewById(R.id.vndEditText);
        euroEditText = findViewById(R.id.euroEditText);

    //         Người dùng chỉ được phép nhập vào số tiền bằng US Dollar, các EditText Euros và VND
    // chỉ readonly.
    // • Khi nhấn nút Convert, ứng dụng sẽ tự chuyển đổi sang đơn vị tiền tệ Euro và VND và hiển
    // thị lên EditText tương ứng.
    // • Khi nhấn nút Clear, giá trị trong các EditText sẽ bị xóa

        vndEditText.setEnabled(false);
        euroEditText.setEnabled(false);


        convertButton.setOnClickListener(v -> {
            String us = usEditText.getText().toString();
            if (us.isEmpty()) {
                usEditText.setError("Please enter a number");
                return;
            }
            double usd = Double.parseDouble(us);
            double vnd = usd * 25500;
            double euro = usd * 0.96;
            vndEditText.setText(String.valueOf(vnd));
            euroEditText.setText(String.valueOf(euro));
        });

        clearButton.setOnClickListener(v -> {
            usEditText.setText("");
            vndEditText.setText("");
            euroEditText.setText("");
        });

        
    }
}