package com.example.holajicap.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.R;
import com.example.holajicap.model.SpendingItem;

import java.util.List;

public class SpendingAdapter extends RecyclerView.Adapter<SpendingAdapter.SpendingViewHolder> {

    private final List<SpendingItem> spendingItems;

    public SpendingAdapter(List<SpendingItem> spendingItems) {
        this.spendingItems = spendingItems;
    }

    @NonNull
    @Override
    public SpendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spending, parent, false);
        return new SpendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpendingViewHolder holder, int position) {
        SpendingItem item = spendingItems.get(position);
        holder.icon.setImageResource(item.getIconResId());
        holder.name.setText(item.getName());
        holder.percentage.setText(item.getPercentage() + "%");
    }

    @Override
    public int getItemCount() {
        return spendingItems.size();
    }

    static class SpendingViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name, percentage;

        SpendingViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_spending_icon);
            name = itemView.findViewById(R.id.tv_spending_name);
            percentage = itemView.findViewById(R.id.tv_spending_percentage);
        }
    }
}
