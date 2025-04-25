package com.example.sender;

import static com.example.sender.R.id.contentMess;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText messContent;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messContent = findViewById(contentMess);
        send = findViewById(R.id.sendMessage);

        send.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.example.myapplication.action_broadcast");
            intent.putExtra("mess",messContent.getText().toString());
            sendBroadcast(intent);
            Toast.makeText(getApplicationContext(),"Message sent",Toast.LENGTH_SHORT).show();
        });
    }
}