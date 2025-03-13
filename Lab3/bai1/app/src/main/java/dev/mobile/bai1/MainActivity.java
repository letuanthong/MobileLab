package dev.mobile.bai1;

import android.app.Activity;
import android.content.Intent;
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
    private EditText editTextEmail;
    private Button signInbutton;
    private static final int PROFILE_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        editTextEmail = findViewById(R.id.emailEditText);
        signInbutton = findViewById(R.id.signInButton);
        textView = findViewById(R.id.textView);
        textView.setText("Xin chào, vui lòng đăng nhập");

        signInbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredEmail = editTextEmail.getText().toString();

                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("email", enteredEmail);
                startActivityForResult(intent, PROFILE_REQUEST_CODE);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });


        if (name != null) {
            // Xử lý dữ liệu trả về từ ProfileActivity
            editTextEmail.setText(name);
            textView.setText("Hẹn gặp lại");
            signInbutton.setVisibility(View.GONE);
        }
    }
}