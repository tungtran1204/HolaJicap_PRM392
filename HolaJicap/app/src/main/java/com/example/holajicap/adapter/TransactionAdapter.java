package com.example.holajicap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.R;
import com.example.holajicap.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactions;
    private Context context;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.amountTextView.setText(String.valueOf(transaction.getAmount())); // Đảm bảo amount là String
        holder.categoryTextView.setText(transaction.getNote());

        // Chuyển đổi Date thành String
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()); // Định dạng ngày
        String formattedDate = sdf.format(transaction.getDate());
        holder.dateTextView.setText(formattedDate); // Thiết lập chuỗi định dạng vào TextView

        holder.amountTextView.setTextColor(ContextCompat.getColor(context, R.color.blue));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView amountTextView;
        public TextView categoryTextView;
        public TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
