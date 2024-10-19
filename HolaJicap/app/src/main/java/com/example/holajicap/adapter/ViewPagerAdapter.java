package com.example.holajicap.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.holajicap.R;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<String> titles;
    private List<Integer> images;

    // Constructor nhận vào danh sách tiêu đề và hình ảnh
    public ViewPagerAdapter(List<String> titles, List<Integer> images) {
        this.titles = titles;
        this.images = images;
    }

    // Tạo View cho mỗi trang trong ViewPager
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_item, parent, false);
        return new ViewHolder(view);
    }

    // Gán dữ liệu vào View của mỗi trang
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(titles.get(position));
        holder.imageView.setImageResource(images.get(position));
    }

    // Trả về số lượng trang
    @Override
    public int getItemCount() {
        return titles.size();
    }

    // ViewHolder giữ các thành phần của layout từng trang
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

