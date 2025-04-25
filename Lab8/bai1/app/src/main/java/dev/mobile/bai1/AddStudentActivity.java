package dev.mobile.bai1;

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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddStudentActivity extends AppCompatActivity {
    private EditText id, name, phone, email;
    private Button save;

    private static final MediaType JSON = MediaType.parse("multipart/form-data; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Student");

        id = findViewById(R.id.ID);
        name = findViewById(R.id.Name);
        phone = findViewById(R.id.Phone);
        email = findViewById(R.id.Email);
        save = findViewById(R.id.buttonSave);

        id.setEnabled(false);

        save.setOnClickListener(v -> {
            if(name.getText().toString().isEmpty()){
                name.setError("Please fill student's name");
            }
            if(email.getText().toString().isEmpty()){
                email.setError("Please fill student's email");
            }
            if(phone.getText().toString().isEmpty()){
                phone.setError("Please fill student's phone");
            }
            else{
                addNewStudent(
                        name.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString() );
            }

        });
    }

    private void addNewStudent(String name, String email, String phone){

        OkHttpClient client = new OkHttpClient();
        String addStudentURL = "http://10.0.2.2:8080/add-student.php";
        RequestBody body = new FormBody.Builder()
                .add("name",name)
                .add("email",email)
                .add("phone",phone)
                .build();

        Request request = new Request.Builder()
                .url(addStudentURL)
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
                    Intent result = new Intent(AddStudentActivity.this, MainActivity.class);
                    startActivity(result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddStudentActivity.this.finish();
                        }
                    });
                } catch (IOException e) {
                    Log.d("onResponse", e.getMessage());
                }
            }
        });
    }
}

