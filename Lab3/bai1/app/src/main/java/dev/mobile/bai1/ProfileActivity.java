package dev.mobile.bai1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView textView;
    private EditText nameEditText;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameEditText = findViewById(R.id.nameEditText);
        buttonSave = findViewById(R.id.saveExitbutton);
        textView = findViewById(R.id.textView2);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        textView.setText("Xin chào, " + email + ". Vui lòng nhập tên");


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredName = nameEditText.getText().toString();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.putExtra("name", enteredName);
                startActivity(intent);
            }
        });
    }
}
