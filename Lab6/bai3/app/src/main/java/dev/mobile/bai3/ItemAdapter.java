package dev.mobile.bai3;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.mobile.bai3.model.Task;
import dev.mobile.bai3.model.TaskModel;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(View view, int position, boolean isLongClick);
    }
    private ArrayList<Task> tasks;
    private ArrayList<Boolean> checkStates;
    private int indexSelected = -1;
    private boolean showCompleted = false;
    private TaskModel taskModel;

    public void setShowCompleted(boolean b) {
        showCompleted = b;
    }
    public int getIndexSelected() {
        return indexSelected;
    }


    public void setIndexSelected(int indexSelected) {
        this.indexSelected = indexSelected;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        taskModel = new TaskModel(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_event, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(tasks.get(position));

        holder.swActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = holder.swActive.isChecked();
                tasks.get(position).setState(isChecked);
                toggleActive(holder);
                String title = holder.tvTitle.getText().toString();
                String location = holder.tvLocation.getText().toString();
                String date = holder.tvDate.getText().toString();
                taskModel.update((int) tasks.get(position).getId(), title, location, date, isChecked ? 1 : 0);
            }
        });

        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {

                    Log.e("LONG CLICK", "onItemClick: long click " + position);
//                    show context menu
                    view.showContextMenu();
//                    get item selected
                    setIndexSelected(position);

                } else {
                    Log.e("SHORT CLICK", "onItemClick: short click" + position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return tasks.size() > 0 ? tasks.size() : 0;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void addTask( TaskModel taskModel, Task task) {
        tasks.add(task);
        if (taskModel == null) {
            Log.e("NULL", "addTask: NULL");
        } else {
            taskModel.add(task.getTitle(), task.getLocation(), task.getDate(), task.getState());
        }
        notifyDataSetChanged();
    }

    public void updateTask(int position, Task task) {
        tasks.set(position, task);
        taskModel.update((int) task.getId(), task.getTitle(), task.getLocation(), task.getDate(), task.getState() ? 1 : 0);
        notifyDataSetChanged();
    }

    public void removeAll() {
        tasks.clear();
        notifyDataSetChanged();
    }

    public void toggleActive(ItemViewHolder holder) {
//        holder.swActive.setChecked(!holder.swActive.isChecked());
        if (holder.swActive.isChecked()) {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | 16);
            holder.tvLocation.setPaintFlags(holder.tvLocation.getPaintFlags() | 16);
            holder.tvDate.setPaintFlags(holder.tvDate.getPaintFlags() | 16);
        } else {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() & (~16));
            holder.tvLocation.setPaintFlags(holder.tvLocation.getPaintFlags() & (~16));
            holder.tvDate.setPaintFlags(holder.tvDate.getPaintFlags() & (~16));
        }
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener, View.OnCreateContextMenuListener {
        private TextView tvTitle, tvLocation, tvDate;
        private Switch swActive;
        private OnItemClickListener onItemClickListener;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDate = itemView.findViewById(R.id.tvDate);
            swActive = itemView.findViewById(R.id.swActive);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this);
        }


        public void bind(Task task) {
            tvTitle.setText(task.getTitle());
            tvLocation.setText(task.getLocation());
            tvDate.setText(task.getDate());
            swActive.setChecked(task.getState());
        }

        public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public boolean onLongClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition(), true);
            return true;
        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
            menu.add(0, 0, 1, "Edit");
            menu.add(0, 1, 2, "Delete");
        }
    }

}
