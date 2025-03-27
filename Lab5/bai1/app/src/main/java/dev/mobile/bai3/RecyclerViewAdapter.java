package dev.mobile.bai3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.mobile.bai3.RecyclerData;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<RecyclerData> recyclerDataArrayList;
    private Context context;

    public RecyclerViewAdapter(ArrayList<RecyclerData> recyclerDataArrayList, Context context) {
        this.recyclerDataArrayList = recyclerDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerData data = recyclerDataArrayList.get(position);
        holder.textViewTitle.setText(data.getTitle());
        holder.checkBox.setChecked(data.getChecked());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            data.setChecked(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return recyclerDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public void removeSelected() {
        ArrayList<RecyclerData> newList = new ArrayList<>();
        for (RecyclerData data : recyclerDataArrayList) {
            if (!data.getChecked()) {
                newList.add(data);
            }
        }
        recyclerDataArrayList = newList;
        notifyDataSetChanged();
    }

    public void removeAll() {
        recyclerDataArrayList.clear();
        notifyDataSetChanged();
    }
}