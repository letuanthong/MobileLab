package dev.mobile.bai1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ArrayList<Student> students;
    private StudentAdapter studentAdapter;
    public static final String EXTRA_STUDENTS = "student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv = findViewById(R.id.recyclerView);
        getSupportActionBar().setTitle("Student Management");

        students = new ArrayList<>();
        studentAdapter = new StudentAdapter(students, this);
        rv.setAdapter(studentAdapter);
        loadStudents();

        // Double Click item
        studentAdapter.setOnItemDoubleClickListener(new OnDoubleClickListener() {
            @Override
            public void onDoubleClick(int position) {
                Student s = students.get(position);
                studentAdapter.updateStudent(s);
            }
        });

        LinearLayoutManager linear = new LinearLayoutManager(this);
        rv.setLayoutManager(linear);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_person){
            Intent intent = new Intent(MainActivity.this,AddStudentActivity.class);
            //startActivityForResult(intent, 444);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadStudents() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://10.0.2.2:8080/get-students.php").build();
        Log.d("loadStudents", "Loading students...");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response)
                    throws IOException {
                try {
                    String responseData = response.body().string();
                    Log.d("onResponse", responseData);
                    JSONObject json = new JSONObject(responseData);
//                    boolean status = json.getBoolean("status");
                    JSONArray data = json.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Student student = new Student(
                                item.getInt("id"),
                                item.getString("name"),
                                item.getString("email"),
                                item.getString("phone")
                        );
                        students.add(student);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            studentAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    Log.d("onResponse", e.getMessage());
                }
            }
        });
    }
}