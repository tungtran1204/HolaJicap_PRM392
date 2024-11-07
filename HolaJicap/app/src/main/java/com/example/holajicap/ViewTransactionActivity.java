package com.example.holajicap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.adapter.TransactionAdapter;
import com.example.holajicap.dao.CategoryDao;
import com.example.holajicap.dao.TransactionDao;
import com.example.holajicap.dao.UserDao;
import com.example.holajicap.dao.WalletDao;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Transaction;
import com.example.holajicap.model.Wallet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewTransactionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;
    private TextView tvBalance;
    private WalletDao walletDao;
    private TransactionDao transactionDao;
    private CategoryDao categoryDao;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hiển thị nút quay lại (Back)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Khởi tạo TextView
        tvBalance = findViewById(R.id.tv_balance);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Kết nối với cơ sở dữ liệu
        HolaJicapDatabase db = HolaJicapDatabase.getInstance(this);
        transactionDao = db.transactionDao();
        categoryDao = db.categoryDao();
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
        adapter = new TransactionAdapter(this, transactions, categoryDao);
        recyclerView.setAdapter(adapter);

        // Lấy tổng số tiền và hiển thị
        new Thread(() -> {
            double totalBalance = walletDao.getTotalBalanceByUserId(currentUserId);

            // Định dạng số tiền
            DecimalFormat decimalFormat = new DecimalFormat("#,###"); // Định dạng không có dấu thập phân
            final String formattedBalance = decimalFormat.format(totalBalance);

            runOnUiThread(() -> tvBalance.setText(formattedBalance));
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.transaction_menu, menu);

        // Lấy SearchView từ menu
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Thiết lập listener cho SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Khi người dùng nhấn nút tìm kiếm
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Khi người dùng nhập vào SearchView
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Kiểm tra nếu item được chọn là nút back
        if (item.getItemId() == android.R.id.home) {
            navigateToNavigationActivity();
            return true;
        } else if (item.getItemId() == R.id.action_search) {
            // Khi nhấn vào biểu tượng tìm kiếm
            return true; // Trả về true để cho phép xử lý tiếp
        }
        return super.onOptionsItemSelected(item);
    }


    private void navigateToNavigationActivity() {
        Intent intent = new Intent(ViewTransactionActivity.this, NavigationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Kết thúc Activity hiện tại để không quay lại được nó nữa
    }

    // Thêm phương thức onDestroy nếu cần
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Dọn dẹp các đối tượng nếu cần thiết
    }
}
