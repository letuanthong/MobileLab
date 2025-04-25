package dev.mobile.bai1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.RecyclerViewHolder> {

    private ArrayList<Student> studentDataArrayList;
    private Context mcontext;

    private OnDoubleClickListener onDoubleClickListener;

    public StudentAdapter(ArrayList<Student> studentDataArrayList, Context mcontext) {
        this.studentDataArrayList = studentDataArrayList;
        this.mcontext = mcontext;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_layout,
                parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Student stu = studentDataArrayList.get(position);
        holder.s_name.setText(stu.getName());
        holder.s_email.setText(stu.getEmail());
        holder.s_phone.setText(stu.getPhone());

    }

    @Override
    public int getItemCount() {
        return studentDataArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        private TextView s_name, s_email, s_phone;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            s_name = itemView.findViewById(R.id.name);
            s_phone = itemView.findViewById(R.id.phone);
            s_email = itemView.findViewById(R.id.email);

            itemView.setOnClickListener(new View.OnClickListener() {
                private long lastClickTime = 0;
                @Override
                public void onClick(View v) {
                    long clickTime = System.currentTimeMillis();
                    if (clickTime - lastClickTime < 300) {
                        // Double-click detected
                        if (onDoubleClickListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                onDoubleClickListener.onDoubleClick(position);
                            }
                        }
                    }
                    lastClickTime = clickTime;
                }
            });

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem del = menu.add(this.getAdapterPosition(),0,getAdapterPosition(),"Delete");
            MenuItem edt = menu.add(this.getAdapterPosition(),1,getAdapterPosition(),"Edit");
            del.setOnMenuItemClickListener(onEditMenu);
            edt.setOnMenuItemClickListener(onEditMenu);
        }
    }

    public void setOnItemDoubleClickListener(OnDoubleClickListener itemlistener){
        this.onDoubleClickListener = itemlistener;
    }

    private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            Student student = studentDataArrayList.get(item.getOrder());
            if(item.getItemId() == 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setTitle("Delete Student")
                        .setMessage("Do you want to delete this student ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteStudent(student);
                                studentDataArrayList.remove(item.getOrder());
                                notifyItemRemoved(item.getOrder());
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }

            if(item.getItemId() == 1 ){

                updateStudent(student);
            }

            return true;
        }
    };

    public void updateStudent(Student student){
        Intent intent = new Intent(this.mcontext, EditStudentActivity.class);
        intent.putExtra("student",student);
        mcontext.startActivity(intent);
    }

    public void deleteStudent(Student student){
        OkHttpClient client = new OkHttpClient();
        String deleteStudentURL = "http://10.0.2.2:8080/delete-student.php";
        RequestBody body = new FormBody.Builder()
                .add("id",student.getId().toString())
                .build();

        Request request = new Request.Builder()
                .url(deleteStudentURL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("onFailure",e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                response.body();
            }
        });
    }

}

