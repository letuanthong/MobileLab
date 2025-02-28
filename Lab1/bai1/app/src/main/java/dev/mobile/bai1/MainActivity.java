package dev.mobile.bai1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button countButton;
    private Button toastButton;
    private int count = 0;

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

        textView = findViewById(R.id.textView);
        countButton = findViewById(R.id.countButton);
        toastButton = findViewById(R.id.toastButton);

        countButton.setOnClickListener(v -> {
            count++;
            textView.setText(String.valueOf(count));
        });

        toastButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Reset Count!", Toast.LENGTH_SHORT).show();
            count = 0;
            textView.setText(String.valueOf(count));
        });
    }
}