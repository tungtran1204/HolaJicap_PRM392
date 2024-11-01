package com.example.holajicap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.adapter.WalletAdapter;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Wallet;

import java.util.List;

public class ChooseWalletActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WalletAdapter adapter;
    private List<Wallet> walletList;
    private HolaJicapDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_wallet);
        db = HolaJicapDatabase.getInstance(getApplicationContext());
        // Initialize toolbar
        ImageView back_icon = findViewById(R.id.left_icon);
        TextView title = findViewById(R.id.toolbar_title);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When click on back_icon, back to MainActivity
                finish();
            }
        });
        title.setText("Chọn ví");

//        TransactionMethodType t1 = new TransactionMethodType("Tiền mặt");
//        TransactionMethodType t2 = new TransactionMethodType("Chuyển khoản");
//        TransactionMethodType t3 = new TransactionMethodType("Phương thức khác");
//
//        List<TransactionMethodType> transactionMethodTypes = new ArrayList<>();
//        transactionMethodTypes.add(t1);
//        transactionMethodTypes.add(t2);
//        transactionMethodTypes.add(t3);
        recyclerView = findViewById(R.id.rec_wallets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        walletList = db.walletDao().getWalletsByUserId(userId);
        // Set adapter
        adapter = new WalletAdapter(walletList, this);
        recyclerView.setAdapter(adapter);

    }
}