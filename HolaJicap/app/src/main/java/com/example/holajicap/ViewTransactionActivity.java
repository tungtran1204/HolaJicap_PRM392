package com.example.holajicap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.adapter.TransactionAdapter;
import com.example.holajicap.dao.TransactionDao;
import com.example.holajicap.dao.UserDao;
import com.example.holajicap.dao.WalletDao;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Transaction;
import com.example.holajicap.model.Wallet;

import java.util.ArrayList;
import java.util.List;

public class ViewTransactionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;
    private TextView tvBalance;
    private WalletDao walletDao;
    private TransactionDao transactionDao;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);

        // Khởi tạo TextView
        tvBalance = findViewById(R.id.tv_balance);

        // Thiết lập Toolbar như là ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Kết nối với cơ sở dữ liệu
        HolaJicapDatabase db = HolaJicapDatabase.getInstance(this);
        transactionDao = db.transactionDao();
        walletDao = db.walletDao();

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        currentUserId = sharedPreferences.getInt("userId", -1);

        // Truy vấn giao dịch của người dùng
        transactions = transactionDao.getTransactionsByUserId(currentUserId);

        // Kiểm tra nếu danh sách giao dịch không null
        if (transactions == null) {
            transactions = new ArrayList<>(); // Đảm bảo không bị lỗi nếu không có giao dịch nào
        }

        // Thiết lập Adapter cho RecyclerView
        adapter = new TransactionAdapter(this, transactions);
        recyclerView.setAdapter(adapter);

        // Lấy tổng số tiền và hiển thị
        new Thread(() -> {
            double totalBalance = walletDao.getTotalBalanceByUserId(currentUserId);
            runOnUiThread(() -> tvBalance.setText(String.valueOf(totalBalance)));
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Gắn menu vào Toolbar
        getMenuInflater().inflate(R.menu.transaction_menu, menu);
        return true;
    }


    // Thêm phương thức onDestroy nếu cần
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Dọn dẹp các đối tượng nếu cần thiết
    }
}
