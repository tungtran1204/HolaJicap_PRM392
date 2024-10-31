package com.example.holajicap.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.ChooseTransactionTypeActivity;
import com.example.holajicap.R;
import com.example.holajicap.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private List<Category> categories;
    private Activity activity;
    private Context context;


    public CategoryAdapter(List<Category> categories, Activity activity) {
        this.categories = categories;
        this.activity = activity;
    }

    public CategoryAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }
    public CategoryAdapter(List<Category> categories, Activity activity, Context context) {
        this.categories = categories;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_card, parent, false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryHolder holder, int position) {
        Category category = categories.get(position);

        // Chuyển đổi tên tệp thành resource ID
        int resId = context.getResources().getIdentifier(category.getCateIcon(), "drawable", context.getPackageName());

        // Đặt resource ID vào ImageView
        holder.imv_category_ava.setImageResource(resId);

        // Đặt tên danh mục
        holder.tv_title.setText(category.getCateName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        ImageView imv_category_ava;
        TextView tv_title;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            imv_category_ava = itemView.findViewById(R.id.imv_category_ava);
            tv_title = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Category selectedCategory = categories.get(pos);

                        // Tìm resource ID từ tên tệp icon
                        int resId = context.getResources().getIdentifier(
                                selectedCategory.getCateIcon(), "drawable", context.getPackageName()
                        );

                        // Tạo Intent để chứa dữ liệu của thẻ được chọn
                        Intent intent = new Intent();
                        intent.putExtra("selectedTitle", selectedCategory.getCateName()); // truyền tên
                        intent.putExtra("selectedIcon", resId);   // truyền icon

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
