package dev.mobile.bai2.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.bai2.R;
import dev.mobile.bai2.model.Schedule;
import dev.mobile.bai2.service.DataGenerator;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CODE_ADD = 1;
    static final int REQUEST_CODE_EDIT = 2;
    private RecyclerView recyclerView;
    private DataGenerator dataGenerator = new DataGenerator();
    private static MainAdapter adapter;
    private static List<Schedule> items;
    static boolean isActionBarSwitchEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeUIComponents();
        setupRecyclerView();
    }

    private void initializeUIComponents() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        items = dataGenerator.getData();
        adapter = new MainAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_actionbar, menu);
        MenuItem itemSwitch = menu.findItem(R.id.action_switch);
        Switch switchAB = itemSwitch.getActionView().findViewById(R.id.switchAB);

        switchAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isActionBarSwitchEnabled = isChecked;
                filterAndDisplay(isChecked);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    static void filterAndDisplay(boolean isChecked) {
        if (!isChecked) {
            adapter.setItems(items);
        } else {
            List<Schedule> enabledItems = new ArrayList<>();

            for (Schedule s : items) {
                if (s.isEnabled()) {
                    enabledItems.add(s);
                }
            }
            adapter.setItems(enabledItems);
        }
        adapter.notifyDataSetChanged();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, EventMonitorActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
            return true;
        } else if (id == R.id.action_remove_all) {
            removeAllItems();
            return true;
        }else if (id == R.id.action_about) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void removeAllItems() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Are you sure you want to remove all?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    items.clear();
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_main_on_holding_item, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = adapter.getPosition();

        if (item.getItemId() == R.id.menuItemEdit) {
            Intent intent = new Intent(MainActivity.this, EventMonitorActivity.class);
            intent.putExtra("title", items.get(position).getTitle());
            intent.putExtra("place", items.get(position).getPlace());
            intent.putExtra("date", items.get(position).getDate());
            intent.putExtra("time", items.get(position).getTime());
            startActivityForResult(intent, REQUEST_CODE_EDIT);
            return true;
        } else if (item.getItemId() == R.id.menuItemDelete) {
            items.remove(position);
            adapter.notifyDataSetChanged();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Schedule newSchedule = new Schedule(
                    data.getStringExtra("title"),
                    data.getStringExtra("place"),
                    data.getStringExtra("date"),
                    data.getStringExtra("time")
            );

            switch (requestCode) {
                case REQUEST_CODE_ADD:
                    items.add(newSchedule);
                    break;
                case REQUEST_CODE_EDIT:
                    items.set(adapter.getPosition(), newSchedule);
                    break;
            }
            adapter.notifyDataSetChanged();
        } else {
            String message = requestCode == REQUEST_CODE_ADD ?
                    "Add event cancelled" : "Edit event cancelled";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}