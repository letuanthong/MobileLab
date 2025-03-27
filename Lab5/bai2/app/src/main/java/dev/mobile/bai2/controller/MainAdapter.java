package dev.mobile.bai2.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.bai2.R;
import dev.mobile.bai2.model.Schedule;
import lombok.Setter;

@Setter
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Schedule> items = new ArrayList<>();
    private int selectedPosition = -1;
    public MainAdapter(List<Schedule> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getPosition() {
        return selectedPosition;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewRoom;
        TextView textViewDate;
        TextView textViewTime;
        Switch switchActive;


        ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewRoom = itemView.findViewById(R.id.textViewRoom);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            switchActive = itemView.findViewById(R.id.switchActive);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectedPosition = getAdapterPosition();
                    return false;
                }
            });
        }

        void bind(Schedule schedule) {
            textViewTitle.setText(schedule.getTitle());
            textViewRoom.setText(schedule.getPlace());
            textViewDate.setText(schedule.getDate());
            textViewTime.setText(schedule.getTime());

            switchActive.setOnCheckedChangeListener(null);
            switchActive.setChecked(schedule.isEnabled());
            switchActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    items.get(getAdapterPosition()).setEnabled(isChecked);
                    boolean isNeedToChange = MainActivity.isActionBarSwitchEnabled;

                    if (isNeedToChange) {
                        MainActivity.filterAndDisplay(isNeedToChange);
                    }
                }
            });
        }
    }
}
