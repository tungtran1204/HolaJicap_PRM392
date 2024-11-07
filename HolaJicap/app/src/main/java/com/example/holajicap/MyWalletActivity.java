package com.example.holajicap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.adapter.MyWalletAdapter;
import com.example.holajicap.adapter.WalletAdapter;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyWalletActivity extends AppCompatActivity {

    private RecyclerView rvWallets;
    private MyWalletAdapter myWalletAdapter;
    private List<Wallet> walletList = new ArrayList<>();
    private HolaJicapDatabase db;
    private int userId;
    private TextView totalAmountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_wallet);
        // Set up back navigation
        findViewById(R.id.iv_back).setOnClickListener(view -> finish());

        // total amount
        totalAmountTextView = findViewById(R.id.total_amount);


        // Retrieve userId from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        rvWallets = findViewById(R.id.rv_wallets);
        myWalletAdapter = new MyWalletAdapter(walletList, this);
        rvWallets.setAdapter(myWalletAdapter);
        rvWallets.setLayoutManager(new LinearLayoutManager(this));

        // Initialize database
        db = HolaJicapDatabase.getInstance(this);

        calculateAndDisplayTotalBalance();

        loadWallets();

    }

    private void calculateAndDisplayTotalBalance() {
        int totalBalance = 0;
        for (Wallet wallet : db.walletDao().getWalletsByUserId(userId)) {
            int balance = (int) wallet.getBalance();
            totalBalance += balance;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String currencyCode = sharedPreferences.getString("currency_code", "VND");
        totalAmountTextView.setText(String.format(Locale.getDefault(), "%,d %s", totalBalance, currencyCode));
    }

    private void loadWallets() {
        // Fetch wallets for the specified userId asynchronously
        new Thread(() -> {
            List<Wallet> wallets = db.walletDao().getWalletsByUserId(userId);
            runOnUiThread(() -> myWalletAdapter.setData(wallets));
        }).start();
    }
}
