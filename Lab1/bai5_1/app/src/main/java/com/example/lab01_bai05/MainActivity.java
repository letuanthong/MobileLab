package com.example.lab01_bai05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CheckBox android;
    private CheckBox ios;
    private CheckBox windows;
    private CheckBox rim;
    private TextView textView;

    private Button click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android = findViewById(R.id.checkBoxAndroid);
        ios = findViewById(R.id.checkBoxIOS);
        windows = findViewById(R.id.checkBoxWindows);
        rim = findViewById(R.id.checkBoxRIM);
        click = findViewById(R.id.button);
        

    //         Khi nhấn nút Cick here to see Results, TextView sẽ hiển thị nội dung phản ánh các lựa
    // chọn của người dùng.
    //The following were selected
    //Android: true
    //iOS: false
    //Windows: true
    //RIM: false
        textView = findViewById(R.id.textViewText);
        click.setOnClickListener(v -> {
            String result = "The following were selected\n";
            result += "Android: " + android.isChecked() + "\n";
            result += "iOS: " + ios.isChecked() + "\n";
            result += "Windows: " + windows.isChecked() + "\n";
            result += "RIM: " + rim.isChecked() + "\n";
            textView.setText(result);
        });
    }
}