package com.example.fetch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetch.model.Hiring;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Hiring> items;

    public ListAdapter(List<Hiring> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hiring hiringData = items.get(position);
        holder.listIdText.setText("List ID: " + hiringData.getListId());
        holder.nameText.setText("Name: " + hiringData.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView listIdText;
        public TextView nameText;

        public ViewHolder(View itemView) {
            super(itemView);
            listIdText = itemView.findViewById(R.id.listIdText);
            nameText = itemView.findViewById(R.id.nameText);
        }
    }
}
