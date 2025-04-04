package dev.mobile.bai4;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private ArrayList<File> files = new ArrayList<>();
    private Stack<String> path = new Stack<>();
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(files.get(position));
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = files.get(position);
                if (file.isDirectory()) {
                    path.push(file.getAbsolutePath());
                    files.clear();
                    for (File f : file.listFiles()) {
                        files.add(f);
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size() > 0 ? files.size() : 0;
    }

    public void setAdapter(ArrayList<File> files,
                           Stack<String> path) {
        this.files = files;
        this.path = path;
        notifyDataSetChanged();
    }

    public Stack<String> getPath() {
        return path;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFileName;
        private CheckBox checkBox;
        private View itemView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            tvFileName = itemView.findViewById(R.id.tvFileName);
            checkBox = itemView.findViewById(R.id.checkBox);
        }

        public View getItemView() {
            return itemView;
        }

        public void bind(File file) {
            tvFileName.setText(file.getName());
        }
    }
}

