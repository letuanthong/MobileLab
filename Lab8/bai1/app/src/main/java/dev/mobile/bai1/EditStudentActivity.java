package dev.mobile.bai1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditStudentActivity extends AppCompatActivity {

    private EditText id, name, phone, email;
    private Button save;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Student");

        id = findViewById(R.id.ID);
        name = findViewById(R.id.Name);
        phone = findViewById(R.id.Phone);
        email = findViewById(R.id.Email);
        save = findViewById(R.id.buttonSave);

        Intent intent = getIntent();
        Student student = intent.getParcelableExtra(MainActivity.EXTRA_STUDENTS);

        id.setText(student.getId().toString());
        name.setText(student.getName());
        email.setText(student.getEmail());
        phone.setText(student.getPhone());

        save.setOnClickListener(v -> {
            addNewStudent(
                    Integer.parseInt(id.getText().toString()),
                    name.getText().toString(),
                    email.getText().toString(),
                    phone.getText().toString() );
        });
    }

    private void addNewStudent(Integer id,String name, String email, String phone){

        OkHttpClient client = new OkHttpClient();
        String editStudentURL = "http://10.0.2.2:8080/update-student.php";
        RequestBody body = new FormBody.Builder()
                .add("id",id.toString())
                .add("name",name)
                .add("email",email)
                .add("phone",phone)
                .build();

        Request request = new Request.Builder()
                .url(editStudentURL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("onFailure",e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String responseDataJSON = response.body().string();
                    Log.d("onResponse", responseDataJSON);
                    Intent result = new Intent(EditStudentActivity.this, MainActivity.class);
                    startActivity(result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            EditStudentActivity.this.finish();
                        }
                    });
                } catch (IOException e) {
                    Log.d("onResponse", e.getMessage());
                }
            }
        });
    }
}

