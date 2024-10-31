package com.example.holajicap.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.R;
import com.example.holajicap.model.TransactionMethodType;

import java.util.List;


public class TransactionMethodTypeAdapter extends RecyclerView.Adapter<TransactionMethodTypeAdapter.TransactionMethodTypeHolder>{
    private List<TransactionMethodType> transactionMethodTypes;
    private Activity activity;
    public TransactionMethodTypeAdapter(List<TransactionMethodType> transactionMethodTypes) {
        this.transactionMethodTypes = transactionMethodTypes;
    }
    public TransactionMethodTypeAdapter(List<TransactionMethodType> transactionMethodTypes, Activity activity) {
        this.transactionMethodTypes = transactionMethodTypes;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TransactionMethodTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transactionmethodtype_card, parent, false);
        return new TransactionMethodTypeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionMethodTypeHolder holder, int position) {
        holder.tv_title.setText(transactionMethodTypes.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return transactionMethodTypes.size();
    }

    class TransactionMethodTypeHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        public TransactionMethodTypeHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        // Lấy đối tượng đã chọn
                        TransactionMethodType selectedTransactionMethodType = transactionMethodTypes.get(pos);

                        // Truyền dữ liệu về Activity
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("selectedWallet", selectedTransactionMethodType.getTitle());

                        // Gọi setResult và finish từ activity
                        activity.setResult(Activity.RESULT_OK, resultIntent);
                        activity.finish();
                    }
                }
            });
        }
    }
}
