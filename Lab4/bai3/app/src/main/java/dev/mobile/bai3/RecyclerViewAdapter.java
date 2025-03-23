package dev.mobile.bai3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<RecyclerData> courseDataArrayList;
    private Context mcontext;

    public RecyclerViewAdapter(ArrayList<RecyclerData> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        RecyclerData recyclerData = courseDataArrayList.get(position);
        holder.courseTV.setText(recyclerData.getTitle());
        // holder.courseIV.setImageResource(recyclerData.getImgid());
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView courseTV;
        private Button buttonRemove;

        private CheckBox checkBox;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTV = itemView.findViewById(R.id.idTVCourse);
            checkBox = itemView.findViewById(R.id.checkBox);
            courseTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(v.getContext(), courseTV.getText().toString(), duration);
                    toast.show();
                }
            });

            // set event for checkbox
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    RecyclerData recyclerData = courseDataArrayList.get(position);
                    recyclerData.setChecked(checkBox.isChecked());
                    courseDataArrayList.set(position, recyclerData);
                }
            });
        }
    }

    public void removeAt(int position) {
        courseDataArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, courseDataArrayList.size());

    }

    public void removeAll() {
        int size = courseDataArrayList.size();
        courseDataArrayList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void removeSelected() {
        int i = 0;
        while (i < courseDataArrayList.size()) {
            if (courseDataArrayList.get(i).getChecked()) {
                removeAt(i);
            } else {
                i++;
            }
        }
    }

}
