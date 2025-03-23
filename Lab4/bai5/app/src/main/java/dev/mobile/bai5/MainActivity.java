package dev.mobile.bai5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<RecyclerData> recyclerDataArrayList;
    private RecyclerView recyclerView;
    private Button buttonRemove;
    private Button buttonAdd;
    private TextView totalUsersTextView;
    private RecyclerViewAdapter adapter;
    private int totalUsers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        buttonRemove = findViewById(R.id.buttonRemove);
        buttonAdd = findViewById(R.id.buttonAdd);
        totalUsersTextView = findViewById(R.id.textView2);
        recyclerDataArrayList = new ArrayList<>();

        adapter = new RecyclerViewAdapter(recyclerDataArrayList, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        buttonAdd.setOnClickListener(view -> addUsers());

        buttonRemove.setOnClickListener(view -> removeUsers());
    }

    public void addUsers() {
        for (int i = totalUsers + 1; i <= totalUsers + 5; i++) {
            recyclerDataArrayList.add(new RecyclerData("User " + i));
        }
        totalUsers += 5;
        totalUsersTextView.setText("Total users: " + totalUsers);
        adapter.notifyDataSetChanged();
    }

    public void removeUsers() {
        if(totalUsers <= 0 ){
            Toast toast = Toast.makeText(getApplicationContext(),"The user list is empty",Toast.LENGTH_SHORT);
            toast.show();
        }else{
            for (int i = totalUsers; i > totalUsers - 5; i--) {
                recyclerDataArrayList.remove(i - 1);
            }
            totalUsers -= 5;
            totalUsersTextView.setText("Total users: " + totalUsers);
            adapter.notifyDataSetChanged();
        }
    }
}