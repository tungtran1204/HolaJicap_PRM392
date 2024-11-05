package com.example.holajicap.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.EditTransactionActivity;
import com.example.holajicap.R;
import com.example.holajicap.model.Transaction;
import java.text.NumberFormat;
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

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        holder.amountTextView.setText(numberFormat.format(transaction.getAmount()));
        holder.categoryTextView.setText(transaction.getNote());
        holder.dateTextView.setText(transaction.getDate());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditTransactionActivity.class);
            intent.putExtra("transactionId", transaction.getTransId());
            context.startActivity(intent);
        });

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
