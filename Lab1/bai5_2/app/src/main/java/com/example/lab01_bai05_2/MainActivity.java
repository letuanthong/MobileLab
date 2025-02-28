package com.example.lab01_bai05_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import android.widget.RadioButton;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    private RadioButton android;
    private RadioButton ios;
    private RadioButton windows;
    private RadioButton rim;
    private TextView textView;

    private Button click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android = findViewById(R.id.radioButton1);
        ios = findViewById(R.id.radioButton2);
        windows = findViewById(R.id.radioButton3);
        rim = findViewById(R.id.radioButton4);
        click = findViewById(R.id.button);


        //         Khi nhấn nút Cick here to see Results, TextView sẽ hiển thị nội dung phản ánh các lựa
        // chọn của người dùng.
        //The following were selected
        //Android: true
        //iOS: false
        //Windows: true
        //RIM: false
        //         Về nguyên tắc, chỉ một RadioButton được chọn tại một thời điểm. Để làm được điều đó,
        // các RadioButton phải được đặt trong cùng một nhóm. Sử dụng thẻ <RadioGroup>
        textView = findViewById(R.id.textViewText);
        click.setOnClickListener(v -> {
            String text = "The following were selected\n";
            text += "Android: " + android.isChecked() + "\n";
            text += "iOS: " + ios.isChecked() + "\n";
            text += "Windows: " + windows.isChecked() + "\n";
            text += "RIM: " + rim.isChecked() + "\n";
            textView.setText(text);

        });
    }
}
