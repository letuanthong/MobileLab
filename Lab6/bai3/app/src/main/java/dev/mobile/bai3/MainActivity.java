package dev.mobile.bai3;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.mobile.bai3.model.Task;
import dev.mobile.bai3.model.TaskModel;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Task> tasks;
    private ItemAdapter adapter;
    private RecyclerView view;
    private TaskModel taskModel;
    private boolean isChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = findViewById(R.id.view);

        tasks = new ArrayList<>();
        adapter = new ItemAdapter();
        taskModel = new TaskModel(this);
//        for (int i = 1; i < 1 + (int)(Math.random() * ((10 - 1) + 1)); i++) {
//            tasks.add(new Task("Title " + i, "Location " + i, new Date().toString(), false));
//        }

        tasks = taskModel.get(isChecked);
        adapter.setTasks(tasks);
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this));
        registerForContextMenu(view);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.app_bar_switch);
        checkable.setChecked(isChecked);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_switch);
        SwitchCompat mySwitch = (SwitchCompat) menuItem.getActionView();

        mySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            this.isChecked = isChecked;
            tasks = taskModel.get(isChecked);
            adapter.setTasks(tasks);
            adapter.notifyDataSetChanged();
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (id == R.id.btnAdd) {

            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivityForResult(intent, 1);

        }else if(id == R.id.btnRemoveAll) {
            builder.setTitle("Remove All");
            builder.setMessage("Are you sure you want to remove all tasks?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                taskModel.clear();
                tasks.clear();
                adapter.removeAll();
                adapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("No", null);
            builder.show();
        }else if(id == R.id.btnAbout) {
//                create dialog
            builder.setTitle("About");
            builder.setMessage("This is a simple app that allows you to add tasks to a list.");
            builder.setPositiveButton("OK", null);
            builder.show();

        }else if(id == R.id.app_bar_switch){
            System.out.println("ischecked " + isChecked);
            isChecked = !isChecked;
            tasks = taskModel.get(isChecked);
            adapter.setTasks(tasks);
            adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String place = data.getStringExtra("place");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
//            tasks.add(new Task(name, place, date + " " + time, false));
            adapter.addTask(taskModel, new Task(name, place, date + " " + time, false));
            tasks = taskModel.get(isChecked);
            adapter.setTasks(tasks);
            adapter.notifyDataSetChanged();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String place = data.getStringExtra("place");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            int position = adapter.getIndexSelected();
            tasks.get(position).setTitle(name);
            tasks.get(position).setLocation(place);
            tasks.get(position).setDate(date + " " + time);
            adapter.updateTask(position, tasks.get(position));
            tasks = taskModel.get(isChecked);
            adapter.setTasks(tasks);
            adapter.notifyDataSetChanged();
        }
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        getMenuInflater().inflate(R.menu.context_menu, menu);
//    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getIndexSelected();
        int id = item.getItemId();
        switch (id) {
            case 0:
                Intent intent = new Intent(this, AddTaskActivity.class);
                String[] date = tasks.get(position).getDate().split(" ");
                intent.putExtra("name", tasks.get(position).getTitle());
                intent.putExtra("place", tasks.get(position).getLocation());
                intent.putExtra("date", date[0]);
                intent.putExtra("time", date[1]);
                startActivityForResult(intent, 2);
                break;
            case 1: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Remove");
                builder.setMessage("Are you sure you want to remove this task?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    Task taskRemoved = tasks.remove(position);
                    taskModel.delete((int) taskRemoved.getId());
                    adapter.notifyDataSetChanged();
                });
                builder.setNegativeButton("No", null);
                builder.show();
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

}