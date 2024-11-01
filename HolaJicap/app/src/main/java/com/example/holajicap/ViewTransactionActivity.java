package com.example.holajicap;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.adapter.TransactionAdapter;
import com.example.holajicap.model.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewTransactionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);

        // Thiết lập Toolbar như là ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách giao dịch
        transactions = new ArrayList<>();
        // Ví dụ thêm giao dịch vào danh sách


        adapter = new TransactionAdapter(this, transactions);
        recyclerView.setAdapter(adapter);
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
