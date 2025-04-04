package dev.mobile.bai2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private Button readInternalButton;
    private Button writeInternalButton;
    private Button readExternalButton;
    private Button writeExternalButton;
    private EditText dataText;
    private String dataFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readInternalButton = findViewById(R.id.readInternalButton);
        writeInternalButton = findViewById(R.id.writeInternalButton);
        readExternalButton = findViewById(R.id.readExternalButton);
        writeExternalButton = findViewById(R.id.writeExternalButton);

        dataText = findViewById(R.id.dataText);

        String filePath = getFilesDir() + "/" + dataFileName;
        System.out.println(filePath);
        String externalFilePath = "/sdcard/Download/" + dataFileName;
        System.out.println(externalFilePath);
        File dataFile = new File(filePath);
        File externalDataFile = new File(externalFilePath);

        readExternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Scanner scanner = new Scanner(externalDataFile);
                    String line = "";
                    if (scanner.hasNextLine())
                        line = scanner.nextLine();
                    String message = "The data from external storage is " + line;
                    Toast toast = Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        writeExternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PrintWriter printWriter = new PrintWriter(externalDataFile);
                    printWriter.print(dataText.getText().toString());
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        readInternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Scanner scanner = new Scanner(dataFile);
                    String data = "";
                    if (scanner.hasNextLine()) {
                        data = scanner.nextLine();
                    }
                    String message = "The data from internal storage: " + data;
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        writeInternalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PrintWriter printWriter = new PrintWriter(dataFile);
                    printWriter.print(dataText.getText().toString());
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}