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
import com.example.holajicap.dao.CategoryDao;
import com.example.holajicap.model.Category;
import com.example.holajicap.model.Transaction;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactions;
    private Context context;
    private CategoryDao categoryDao;


    public TransactionAdapter(Context context, List<Transaction> transactions, CategoryDao categoryDao) {
        this.context = context;
        this.transactions = transactions;
        this.categoryDao = categoryDao; // Khởi tạo categoryDao
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

        // Format số tiền
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        holder.amountTextView.setText(numberFormat.format(transaction.getAmount()));

        // Lấy thông tin Category từ categoryDao
        Category category = categoryDao.getCategoryById(transaction.getCateId());
        holder.categoryTextView.setText(category.getCateName());

        // Kiểm tra loại Category để thay đổi màu
        if (category.getCateType().equalsIgnoreCase("Expenditure")) {
            holder.amountTextView.setTextColor(ContextCompat.getColor(context, R.color.red)); // Màu đỏ
        } else if (category.getCateType().equalsIgnoreCase("Revenue")) {
            holder.amountTextView.setTextColor(ContextCompat.getColor(context, R.color.blue)); // Màu xanh
        } else {
            holder.amountTextView.setTextColor(ContextCompat.getColor(context, R.color.black)); // Màu mặc định
        }

        // Hiển thị ngày tháng
        holder.dateTextView.setText(transaction.getDate());

        // Thiết lập sự kiện click để chỉnh sửa giao dịch
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
