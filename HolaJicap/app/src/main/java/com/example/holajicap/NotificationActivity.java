package com.example.holajicap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.holajicap.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView rvNotifications;
    private TextView tvNoNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);

        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> onBackPressed());

        rvNotifications = findViewById(R.id.rv_notifications);
        tvNoNotifications = findViewById(R.id.tv_no_notifications);

        // Load notifications (mock or from data source)
        List<NotificationItem> notifications = getNotifications();

        if (notifications.isEmpty()) {
            tvNoNotifications.setVisibility(View.VISIBLE);
            rvNotifications.setVisibility(View.GONE);
        } else {
            tvNoNotifications.setVisibility(View.GONE);
            rvNotifications.setVisibility(View.VISIBLE);
            rvNotifications.setLayoutManager(new LinearLayoutManager(this));
            rvNotifications.setAdapter(new NotificationAdapter(notifications));
        }
    }

    private List<NotificationItem> getNotifications() {
        // Sample notifications
        List<NotificationItem> notifications = new ArrayList<>();
        notifications.add(new NotificationItem("You have a new message", "2 hours ago"));
        notifications.add(new NotificationItem("Your profile was updated", "Yesterday"));
        return notifications;
    }


    public class NotificationItem {
        private String content;
        private String timeReceived;

        public NotificationItem(String content, String timeReceived) {
            this.content = content;
            this.timeReceived = timeReceived;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTimeReceived() {
            return timeReceived;
        }

        public void setTimeReceived(String timeReceived) {
            this.timeReceived = timeReceived;
        }
    }


}
