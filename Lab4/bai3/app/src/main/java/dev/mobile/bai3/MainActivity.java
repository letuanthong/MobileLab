package dev.mobile.bai3;

import android.os.Bundle;
import android.widget.Button;

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
    private Button buttonRemoveAll;
    private Button buttonRemoveSelected;

    private RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        buttonRemoveSelected = findViewById(R.id.buttonRemoveSelected);
        buttonRemoveAll = findViewById(R.id.buttonRemoveAll);
        recyclerDataArrayList = new ArrayList<>();

        recyclerDataArrayList.add(new RecyclerData("Apple", false));
        recyclerDataArrayList.add(new RecyclerData("Samsung",false));
        recyclerDataArrayList.add(new RecyclerData("Nokia",false));
        recyclerDataArrayList.add(new RecyclerData("Oppo",false));


        adapter = new RecyclerViewAdapter(recyclerDataArrayList, this);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        buttonRemoveAll.setOnClickListener(view -> adapter.removeAll());

        buttonRemoveSelected.setOnClickListener(view -> adapter.removeSelected());


    }
}