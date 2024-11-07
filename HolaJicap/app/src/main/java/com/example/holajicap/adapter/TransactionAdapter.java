package com.example.holajicap.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.EditTransactionActivity;
import com.example.holajicap.R;
import com.example.holajicap.dao.CategoryDao;
import com.example.holajicap.model.Category;
import com.example.holajicap.model.Transaction;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> implements Filterable {
    private List<Transaction> transactions; // Danh sách giao dịch hiện tại
    private List<Transaction> transactionsFull; // Danh sách giao dịch đầy đủ
    private Context context;
    private CategoryDao categoryDao;

    public TransactionAdapter(Context context, List<Transaction> transactions, CategoryDao categoryDao) {
        this.context = context;
        this.transactions = transactions;
        this.transactionsFull = new ArrayList<>(transactions); // Lưu danh sách gốc để sử dụng cho tìm kiếm
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

        // Định dạng số tiền mà không có ký hiệu $
        NumberFormat formatter = new DecimalFormat("#,###"); // Định dạng để hiện "10.000.000"
        String formattedAmount = formatter.format(transaction.getAmount());
        holder.amountTextView.setText(formattedAmount); // Không thêm ký hiệu $

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

        // Định dạng ngày
        String originalDate = transaction.getDate(); // Giả sử ngày ở định dạng yyyy/MM/dd
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = "";

        try {
            Date date = originalFormat.parse(originalDate);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            formattedDate = originalDate; // Nếu có lỗi thì giữ nguyên ngày gốc
        }

        // Hiển thị ngày tháng
        holder.dateTextView.setText(formattedDate);

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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Transaction> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(transactionsFull); // Nếu không có input, trả về danh sách gốc
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Transaction transaction : transactionsFull) {
                        // Kiểm tra nếu bất kỳ thông tin nào trong giao dịch chứa input
                        if (transaction.getNote().toLowerCase().contains(filterPattern) ||
                                categoryDao.getCategoryById(transaction.getCateId()).getCateName().toLowerCase().contains(filterPattern) ||
                                String.valueOf(transaction.getAmount()).contains(filterPattern)) {
                            filteredList.add(transaction);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                transactions.clear();
                transactions.addAll((List<Transaction>) results.values);
                notifyDataSetChanged(); // Cập nhật RecyclerView
            }
        };
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