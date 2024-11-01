package com.example.holajicap.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.R;
import com.example.holajicap.ChooseTransactionTypeActivity;
import com.example.holajicap.model.RevenueType;

import java.util.List;

public class RevenueTypeAdapter extends RecyclerView.Adapter<RevenueTypeAdapter.RevenueTypeHolder> {
    private List<RevenueType> revenueTypes;
    private Activity activity;
    public RevenueTypeAdapter(List<RevenueType> revenueTypes) {
        this.revenueTypes = revenueTypes;
    }

    @NonNull
    @Override
    public RevenueTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_revenuetype_card, parent, false);
        return new RevenueTypeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueTypeHolder holder, int position) {
        holder.imv_revenue_ava.setImageResource(revenueTypes.get(position).getAva());
        holder.tv_title.setText(revenueTypes.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return revenueTypes.size();
    }
    class RevenueTypeHolder extends RecyclerView.ViewHolder {
        ImageView imv_revenue_ava;
        TextView tv_title;
        public RevenueTypeHolder(@NonNull View itemView) {
            super(itemView);
            imv_revenue_ava = itemView.findViewById(R.id.imv_revenue_ava);
            tv_title = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        RevenueType selectedRevenueType = revenueTypes.get(pos);

                        // Tạo Intent để chứa dữ liệu của thẻ được chọn
                        Intent intent = new Intent();
                        intent.putExtra("selectedTitle", selectedRevenueType.getTitle()); // truyền tên
                        intent.putExtra("selectedIcon", selectedRevenueType.getAva());   // truyền icon

                        // Gửi dữ liệu về Activity gọi nó (AddTransactionActivity)
                        ((ChooseTransactionTypeActivity) itemView.getContext()).setResult(Activity.RESULT_OK, intent);

                        // Kết thúc Activity hiện tại và quay lại Activity trước
                        ((ChooseTransactionTypeActivity) itemView.getContext()).finish();
                    }
                }
            });
        }
    }
}
