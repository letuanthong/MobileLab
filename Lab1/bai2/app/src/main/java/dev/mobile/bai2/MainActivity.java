package dev.mobile.bai2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    private Button button;

    private final String defaultText = "Enter your content here";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        editText.setText(defaultText);

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && editText.getText().toString().equals(defaultText)) {
                editText.setText("");
            }
        });

        button.setOnClickListener(v -> {
            // Get text from EditText and set it to TextView
            String text = editText.getText().toString();
            textView.setText(text);

            // Reset EditText to default text
            editText.setText(defaultText);

            editText.clearFocus();
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enable or disable button
                if (s.toString().equalsIgnoreCase("OFF")) {
                    button.setEnabled(false);
                } else if (s.toString().equalsIgnoreCase("ON")) {
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed after text changes
            }
        });
    }
}