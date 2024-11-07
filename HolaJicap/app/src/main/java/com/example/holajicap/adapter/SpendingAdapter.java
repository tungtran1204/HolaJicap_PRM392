package com.example.holajicap.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.holajicap.R;
import com.example.holajicap.model.CategorySpending;
import java.util.List;

public class SpendingAdapter extends RecyclerView.Adapter<SpendingAdapter.SpendingViewHolder> {

    private final List<CategorySpending> categorySpendingList;
    private final Context context;

    public SpendingAdapter(Context context, List<CategorySpending> categorySpendingList) {
        this.context = context;
        this.categorySpendingList = categorySpendingList;
    }

    @NonNull
    @Override
    public SpendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_spending, parent, false);
        return new SpendingViewHolder(view);
    }

    public void setData(List<CategorySpending> newData) {
        categorySpendingList.clear();
        if (newData != null) {
            categorySpendingList.addAll(newData);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull SpendingViewHolder holder, int position) {
        CategorySpending item = categorySpendingList.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String currencyCode = sharedPreferences.getString("currency_code", "VND");

        // Set category icon based on cateIcon
        int iconResId = context.getResources().getIdentifier(
                item.getCateIcon(), "drawable", context.getPackageName());
        if (iconResId != 0) {
            holder.iconImageView.setImageResource(iconResId);
        } else {
            // Set a default icon or handle the case where the icon is not found
            holder.iconImageView.setImageResource(R.drawable.logo); // Thay bằng tài nguyên mặc định
        }

        // Set category name
        holder.categoryNameTextView.setText(item.getCateName());

        // Display total amount for the category
        holder.amountTextView.setText(String.format("%.0f %s", item.getTotalAmount(), currencyCode));
    }

    @Override
    public int getItemCount() {
        return categorySpendingList.size();
    }

    public static class SpendingViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView categoryNameTextView;
        TextView amountTextView;

        public SpendingViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconView);
            categoryNameTextView = itemView.findViewById(R.id.nameTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView1);
        }
    }
}
