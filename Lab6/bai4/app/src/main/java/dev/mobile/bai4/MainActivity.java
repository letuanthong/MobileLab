package dev.mobile.bai4;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private ArrayList<File> files;
    private ItemAdapter itemAdapter;
    private File root;
    private Stack<String> path;
    private RecyclerView recyclerView;
    private Button btnBack;
    private String folderName;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.view);
        btnBack = findViewById(R.id.btnBack);
        files = new ArrayList<>();
        itemAdapter = new ItemAdapter();
        path = new Stack<>();
        root = Environment.getRootDirectory();
        path.push(root.getAbsolutePath());

        // request permission to writeable external storage

        if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
            // read external storage
            // read all file names from external storage
            // add file names to files
            // setAdapter
            for (File file : Objects.requireNonNull(root.listFiles())) {
                files.add(file);
            }
        } else {
            // read internal storage
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stack<String> currentPath = itemAdapter.getPath();
                if (currentPath.size() > 1) {
                    currentPath.pop();
                    files.clear();
                    files.addAll(Arrays.asList(Objects.requireNonNull(new File(currentPath.peek()).listFiles())));
                    itemAdapter.setAdapter(files, currentPath);
                    recyclerView.setAdapter(itemAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
            }
        });
        itemAdapter.setAdapter(files, path);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (id == R.id.btnAddFolder) {
            builder.setTitle("Add folder");
            final EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    folderName = input.getText().toString();
                    if (ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        askPermission();
                    } else {
                        createDir(itemAdapter.getPath().peek(), folderName);
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else if (id == R.id.btnAddFile) {
            // add file
            builder.setTitle("Add file");
            final EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fileName = input.getText().toString();
                    if (ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        askPermission();
                    } else {
                        createFile(itemAdapter.getPath().peek(), fileName);
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void askPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private void createDir(String root, String name) {
        File file = new File(root + "/" + name);
        if (!file.exists()) {
            file.mkdirs();
            files.clear();
            files.addAll(Arrays.asList(Objects.requireNonNull(new File(root).listFiles())));
            itemAdapter.setAdapter(files, itemAdapter.getPath());
            recyclerView.setAdapter(itemAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }
    }

    private void createFile(String root, String name) {
        File file = new File(root + "/" + name);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    files.clear();
                    files.addAll(Arrays.asList(Objects.requireNonNull(new File(root).listFiles())));
                    itemAdapter.setAdapter(files, itemAdapter.getPath());
                    recyclerView.setAdapter(itemAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                } else {
                    Toast.makeText(this, "Failed to create file", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error creating file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // read external storage
                createDir(itemAdapter.getPath().peek(), folderName);
            } else {
                askPermission();
            }
        }
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }
}