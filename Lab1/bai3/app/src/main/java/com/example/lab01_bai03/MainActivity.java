package com.example.lab01_bai03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private TextView percentValueTV;
    private TextView tipValueTV;
    private TextView totalValueTV;
    private Button addButton;
    private Button subtractButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText);
        percentValueTV = findViewById(R.id.percentValueTV);
        tipValueTV = findViewById(R.id.tipValueTV);
        totalValueTV = findViewById(R.id.totalValueTV);
        addButton = findViewById(R.id.addButton);
        subtractButton = findViewById(R.id.subtractButton);
        //     Người dùng nhập số tiền cần thanh toán vào mục Bill Amount tương ứng
        // • Percent tiền tip mặc định là 10% hóa đơn, giá trị của Tip và Total sẽ được cập nhật tự
        // động khi người dùng thay đổi giá trị Bill Amount.
        // • Người dùng có thể nhấn nút “+”, “-” để tăng hoặc giảm % tiền tip và giá trị của Tip và
        // Total sẽ được cập nhật tự động theo sự thay đổi này.
        // • Percent tối thiểu là 5%, khi giảm đến 5% thì button “-” sẽ bị disabled

        percentValueTV.setText("10%");

        addButton.setOnClickListener(v -> {
            int percent = Integer.parseInt(percentValueTV.getText().toString().replace("%", ""));
            if (percent < 100) {
                percent += 1;
                percentValueTV.setText(percent + "%");
                calculate();
            }
        });

        subtractButton.setOnClickListener(v -> {
            int percent = Integer.parseInt(percentValueTV.getText().toString().replace("%", ""));
            if (percent > 5) {
                percent -= 1;
                percentValueTV.setText(percent + "%");
                calculate();
            }
        });

        //disable subtract button
        percentValueTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                subtractButton.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int percent = Integer.parseInt(percentValueTV.getText().toString().replace("%", ""));
                if (percent == 5) {
                    subtractButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //edit text event
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                calculate();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculate();
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        });

    }

    private void calculate() {
        String billAmount = editText.getText().toString();
        if (billAmount.isEmpty()) {
            tipValueTV.setText("0");
            totalValueTV.setText("0");
        } else {
            int percent = Integer.parseInt(percentValueTV.getText().toString().replace("%", ""));
            double bill = Double.parseDouble(billAmount);
            double tip = bill * percent / 100;
            double total = bill + tip;
            //lam tron 2 chu so thap phan
            tipValueTV.setText("$"+String.format("%.2f", tip));
            totalValueTV.setText("$"+String.format("%.2f", total));
        }
    }
    
}