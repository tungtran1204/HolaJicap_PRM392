package com.example.holajicap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.adapter.CurrencyAdapter;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Currency;
import com.example.holajicap.model.User;

import java.util.List;

public class ChooseCurrencyActivity extends AppCompatActivity {
    private HolaJicapDatabase db;
    private CurrencyAdapter adapter;
    private ImageView selectedFlagImageView;
    private TextView selectedCurrencyTextView;
    private RecyclerView recyclerView;
    private Currency selectedCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_currency);

        db = HolaJicapDatabase.getInstance(this);

        selectedFlagImageView = findViewById(R.id.ivSelectedFlag);
        selectedCurrencyTextView = findViewById(R.id.tvSelectedCurrency);
        recyclerView = findViewById(R.id.rvCurrencyList);
        TextView btnEdit = findViewById(R.id.btnEdit);
        Button btn_continue = findViewById(R.id.btn_Continue);

        adapter = new CurrencyAdapter(this, currency -> {
            selectedCurrency = currency;
            selectedCurrencyTextView.setText(currency.getCurrency_name());
            int resId = getFlagResourceId(currency.getCurrency_imageResId());
            selectedFlagImageView.setImageResource(resId);
            recyclerView.setVisibility(View.GONE);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.GONE);

        loadDefaultCurrency();

        btnEdit.setOnClickListener(v -> {
            recyclerView.setVisibility(recyclerView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            if (recyclerView.getVisibility() == View.VISIBLE) {
                loadCurrencies();
            }
        });

        btn_continue.setOnClickListener(v -> {
            if (selectedCurrency != null) {
                updateSelectedCurrency(selectedCurrency);
            } else {
                Toast.makeText(this, "Vui lòng chọn đơn vị tiền tệ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDefaultCurrency() {
        new Thread(() -> {
            try {
                Currency defaultCurrency = db.currencyDao().getCurrencyById(1);
                if (defaultCurrency != null) {
                    selectedCurrency = defaultCurrency;
                    runOnUiThread(() -> {
                        selectedCurrencyTextView.setText(defaultCurrency.getCurrency_name());
                        int resId = getFlagResourceId(defaultCurrency.getCurrency_imageResId());
                        selectedFlagImageView.setImageResource(resId);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this,
                        "Lỗi tải tiền tệ mặc định: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void loadCurrencies() {
        new Thread(() -> {
            try {
                List<Currency> currencies = db.currencyDao().getAllCurrencies();
                runOnUiThread(() -> {
                    if (currencies != null && !currencies.isEmpty()) {
                        adapter.setCurrencies(currencies);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(this, "Không có tiền tệ nào.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(this, "Lỗi tải danh sách tiền tệ: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void updateSelectedCurrency(Currency newSelected) {
        new Thread(() -> {
            try {
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                int userId = sharedPreferences.getInt("userId", -1);

                if (userId != -1) {
                    db.userDao().updateUserCurrency(userId, newSelected.getCurrency_id());

                    Log.d("CurrencyUpdate", "Updated currency for user " + userId +
                            " to currency " + newSelected.getCurrency_id());

                    runOnUiThread(() -> {
                        Toast.makeText(this, "Đã cập nhật tiền tệ thành công!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChooseCurrencyActivity.this,
                                NavigationActivity.class);
                        startActivity(intent);
                        finish();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(this,
                            "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this,
                        "Lỗi cập nhật tiền tệ: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private int getFlagResourceId(String imageName) {
        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        return resId != 0 ? resId : R.drawable.flag_vietnam;
    }
}