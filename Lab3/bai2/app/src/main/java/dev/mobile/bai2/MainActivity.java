package dev.mobile.bai2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText editTextURL;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextURL = findViewById(R.id.editTextURL);
        button = findViewById(R.id.button);

        button.setOnClickListener(view->{
            String urlValue = editTextURL.getText().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+urlValue));
            startActivity(intent);

        });
    }
}