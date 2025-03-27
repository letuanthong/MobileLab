package dev.mobile.bai3;

    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.widget.Button;

    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity {
        private ArrayList<RecyclerData> recyclerDataArrayList;
        private RecyclerView recyclerView;
        private RecyclerViewAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            recyclerView = findViewById(R.id.recyclerView);
            recyclerDataArrayList = new ArrayList<>();

            recyclerDataArrayList.add(new RecyclerData("Apple", false));
            recyclerDataArrayList.add(new RecyclerData("Samsung", false));
            recyclerDataArrayList.add(new RecyclerData("Nokia", false));
            recyclerDataArrayList.add(new RecyclerData("Oppo", false));

            adapter = new RecyclerViewAdapter(recyclerDataArrayList, this);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int itemId = item.getItemId();

            if (itemId == R.id.action_check_uncheck_all) {
                boolean allChecked = true;
                for (RecyclerData data : recyclerDataArrayList) {
                    if (!data.getChecked()) {
                        allChecked = false;
                        break;
                    }
                }
                for (RecyclerData data : recyclerDataArrayList) {
                    data.setChecked(!allChecked);
                }
                adapter.notifyDataSetChanged();
                return true;
            } else if (itemId == R.id.action_delete_selected) {
                showConfirmationDialog("Delete selected items", "Are you sure you want to delete the selected items?", () -> adapter.removeSelected());
                return true;
            } else if (itemId == R.id.action_delete_all) {
                showConfirmationDialog("Delete all items", "Are you sure you want to delete all items?", () -> adapter.removeAll());
                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }
        }

        private void showConfirmationDialog(String title, String message, Runnable onConfirm) {
            new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Yes", (dialog, which) -> onConfirm.run())
                    .setNegativeButton("No", null)
                    .show();
        }
    }