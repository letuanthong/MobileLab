package dev.mobile.bai1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button saveButton;
    private EditText textColor;
    private EditText backgroundColor;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        textView = findViewById(R.id.textView);
        saveButton = findViewById(R.id.saveButton);
        textColor = findViewById(R.id.textColor);
        backgroundColor = findViewById(R.id.backgroundColor);

        Integer clickNum = sharedPreferences.getInt("clicks",1);
        clickNum++;
        textView.setText(clickNum.toString());
        editor.putInt("clicks",clickNum);
        editor.apply();

        String textColorValue = sharedPreferences.getString("textColor","#FFFFFF");
        String backgroundColorValue = sharedPreferences.getString("backgroundColor","#2222FF");

        textView.setTextColor(Color.parseColor(textColorValue));
        textView.setBackgroundColor(Color.parseColor(backgroundColorValue));
        textColor.setText(textColorValue);
        backgroundColor.setText(backgroundColorValue);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("textColor",textColor.getText().toString());
                editor.putString("backgroundColor",backgroundColor.getText().toString());
                editor.apply();
                textView.setTextColor(Color.parseColor(textColor.getText().toString()));
                textView.setBackgroundColor(Color.parseColor(backgroundColor.getText().toString()));
            }
        });

    }
}