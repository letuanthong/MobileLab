package dev.mobile.bai1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private final ArrayList<Contact> contactList;
    private ItemClickListener mItemListener;

    public ContactAdapter(ArrayList<Contact> contactList, Context mcontext, ItemClickListener itemClickListener) {
        this.contactList = contactList;
        this.mItemListener = itemClickListener;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.username.setText(contact.getUsername());

        holder.itemView.setOnClickListener(v -> {
            mItemListener.onItemClick(contact);
        });
    }

    public interface ItemClickListener {
        void onItemClick(Contact contact);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Contact> newList) {
        contactList.clear();
        contactList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ContactHolder extends RecyclerView.ViewHolder {
        private final TextView username;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            this.username = itemView.findViewById(R.id.username);
        }
    }
}

