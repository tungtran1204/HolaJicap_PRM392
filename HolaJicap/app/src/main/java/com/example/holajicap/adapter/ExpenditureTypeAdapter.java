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
import com.example.holajicap.model.ExpenditureType;

import java.util.List;

public class ExpenditureTypeAdapter extends RecyclerView.Adapter<ExpenditureTypeAdapter.ExpenditureTypeHolder> {
    private List<ExpenditureType> expenditureTypes;
    private Activity activity;

    public ExpenditureTypeAdapter(List<ExpenditureType> expenditureTypes) {
        this.expenditureTypes = expenditureTypes;
    }

    @NonNull
    @Override
    public ExpenditureTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_expendituretype_card, parent, false);
        return new ExpenditureTypeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenditureTypeHolder holder, int position) {
        holder.imv_expenditure_ava.setImageResource(expenditureTypes.get(position).getAva());
        holder.tv_title.setText(expenditureTypes.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return expenditureTypes.size();
    }

    class ExpenditureTypeHolder extends RecyclerView.ViewHolder {
        ImageView imv_expenditure_ava;
        TextView tv_title;

        public ExpenditureTypeHolder(@NonNull View itemView) {
            super(itemView);
            imv_expenditure_ava = itemView.findViewById(R.id.imv_expenditure_ava);
            tv_title = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        ExpenditureType selectedExpenditureType = expenditureTypes.get(pos);

                        // Tạo Intent để chứa dữ liệu của thẻ được chọn
                        Intent intent = new Intent();
                        intent.putExtra("selectedTitle", selectedExpenditureType.getTitle()); // truyền tên
                        intent.putExtra("selectedIcon", selectedExpenditureType.getAva());   // truyền icon

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
