package com.example.holajicap.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.R;
import com.example.holajicap.SpendingData;

import java.util.ArrayList;
import java.util.List;

public class SpendingAdapter extends RecyclerView.Adapter<SpendingAdapter.SpendingViewHolder> {

    private List<SpendingData> spendingDataList = new ArrayList<>();
    private Context context;

    public SpendingAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SpendingData> newData) {
        spendingDataList.clear();
        if (newData != null) {
            spendingDataList.addAll(newData);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SpendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spending, parent, false);
        return new SpendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpendingViewHolder holder, int position) {
        SpendingData data = spendingDataList.get(position);
        Log.d("SpendingAdapter", "Category: " + data.getCateName()
                + ", Percentage: " + data.getPercentage());

        holder.nameTextView.setText(data.getCateName());
        holder.amountTextView.setText(String.format("%.2f%%", data.getPercentage()));

        int iconResId = context.getResources().getIdentifier(data.getCateIcon(), "drawable", context.getPackageName());
        if (iconResId != 0) {
            holder.iconView.setImageResource(iconResId);
        } else {
            holder.iconView.setImageResource(R.drawable.baseline_account_balance_wallet_24);
        }
    }

    @Override
    public int getItemCount() {
        return spendingDataList.size();
    }

    public static class SpendingViewHolder extends RecyclerView.ViewHolder {
        ImageView iconView;
        TextView nameTextView, amountTextView;

        public SpendingViewHolder(@NonNull View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.iconView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView1);
        }
    }
}



