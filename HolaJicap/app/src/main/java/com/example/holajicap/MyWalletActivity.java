package com.example.holajicap;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_wallet);
        // Set up back navigation
        findViewById(R.id.iv_back).setOnClickListener(view -> finish());

        // Example: Setting total and cash balance
        TextView totalBalanceTextView = findViewById(R.id.total_amount);
        TextView cashBalanceTextView = findViewById(R.id.cash_amount);

        // Set these values as needed
        totalBalanceTextView.setText("100,000 ₫"); // Tổng cộng
        cashBalanceTextView.setText("0 ₫"); // Tiền mặt
    }
}
