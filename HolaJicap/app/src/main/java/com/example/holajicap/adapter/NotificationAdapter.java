package com.example.holajicap.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.NotificationActivity;
import com.example.holajicap.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<NotificationActivity.NotificationItem> notifications;

    public NotificationAdapter(List<NotificationActivity.NotificationItem> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationActivity.NotificationItem notification = notifications.get(position);
        holder.tvNotificationContent.setText(notification.getContent());
        holder.tvNotificationTime.setText(notification.getTimeReceived());
        holder.ivNotificationIcon.setImageResource(R.drawable.baseline_message_24); // Replace with icon drawable
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNotificationIcon;
        TextView tvNotificationContent, tvNotificationTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNotificationIcon = itemView.findViewById(R.id.ivMessageIcon);
            tvNotificationContent = itemView.findViewById(R.id.tvNotificationContent);
            tvNotificationTime = itemView.findViewById(R.id.tvNotificationTime);
        }
    }
}
