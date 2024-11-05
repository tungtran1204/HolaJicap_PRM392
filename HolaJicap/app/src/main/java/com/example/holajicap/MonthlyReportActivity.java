package com.example.holajicap;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holajicap.dao.TransactionDao;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Category;
import com.example.holajicap.model.Transaction;
import com.example.holajicap.model.TransactionWithCategory;
import com.example.holajicap.model.Wallet;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MonthlyReportActivity extends AppCompatActivity {
    private HolaJicapDatabase db;
    private BarChart barChart;
    private PieChart pieChartExpense;
    private PieChart pieChartIncome;
    private TransactionDao transactionDao;
    private TextView totalAmountTextView;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);
        initViews();
        userId = getUserId();

        logAllTransactions();
        setupTabLayout((TabLayout) findViewById(R.id.tab_layout));

        calculateAndDisplayTotalBalance();
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
    }

    private void initViews() {
        db = HolaJicapDatabase.getInstance(getApplicationContext());
        barChart = findViewById(R.id.barChart);
        pieChartExpense = findViewById(R.id.pie_chart_expense);
        pieChartIncome = findViewById(R.id.pie_chart_income);
        totalAmountTextView = findViewById(R.id.total_amount);
        transactionDao = db.transactionDao();
    }

    private int getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1);
    }

    private void calculateAndDisplayTotalBalance() {
        int totalBalance = 0;
        for (Wallet wallet : db.walletDao().getWalletsByUserId(userId)) {
            int balance = (int) wallet.getBalance();
            totalBalance += balance;
        }
        totalAmountTextView.setText(String.format(Locale.getDefault(), "%,d", totalBalance));
    }

    private void logAllTransactions() {
        transactionDao.getAll().forEach(transaction -> Log.d("MonthlyReportActivity", "Transaction: " + transaction));
    }

    private void setupTabLayout(@NonNull TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setText("Tháng trước"));
        tabLayout.addTab(tabLayout.newTab().setText("Tháng này"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateChartDataForSelectedMonth(tab.getPosition() == 1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        tabLayout.getTabAt(1).select();
    }

    private void updateChartDataForSelectedMonth(boolean isCurrentMonth) {
        String[] dateRange = getDateRange(isCurrentMonth);
        updateBarChart(dateRange);
        updatePieChart(dateRange);
    }

    private String[] getDateRange(boolean isCurrentMonth) {
        Calendar calendar = Calendar.getInstance();
        if (!isCurrentMonth) calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = formatDate(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = formatDate(calendar.getTime());
        return new String[]{startDate, endDate};
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(date);
    }

    private void updateBarChart(String[] dateRange) {
        List<Transaction> transactions = transactionDao.getTransactionsInRangeByUserId(userId, dateRange[0], dateRange[1]);
        float[] incomeTotals = new float[5];
        float[] expenseTotals = new float[5];
        categorizeTransactions(transactions, incomeTotals, expenseTotals);

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            entries.add(new BarEntry(i, new float[]{incomeTotals[i], expenseTotals[i]}));
        }

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.red));
        dataSet.setStackLabels(new String[]{"Thu nhập", "Chi tiêu"});
        barChart.setData(new BarData(dataSet));

        setupChartAppearance();
    }

    private void categorizeTransactions(List<Transaction> transactions, float[] incomeTotals, float[] expenseTotals) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        for (Transaction transaction : transactions) {
            try {
                int index = getTransactionIndex(sdf.parse(transaction.getDate()));
                if (transaction.getCateId() > 32) incomeTotals[index] += transaction.getAmount();
                else if (transaction.getCateId() >= 1)
                    expenseTotals[index] -= Math.abs(transaction.getAmount());
            } catch (Exception e) {
                Log.e("MonthlyReportActivity", "Error parsing date: " + transaction.getDate(), e);
            }
        }
    }

    private int getTransactionIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return (day - 1) / 6;
    }

    private void setupChartAppearance() {
        barChart.getLegend().setEnabled(true);
        Legend legend = barChart.getLegend();
        legend.setTextColor(getResources().getColor(R.color.white));

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"1-6", "7-13", "14-20", "21-27", "28-31"}));
        xAxis.setTextColor(getResources().getColor(R.color.white));

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(getResources().getColor(R.color.white));
        barChart.getAxisRight().setEnabled(false);
        barChart.invalidate();
    }

    private void updatePieChart(String[] dateRange) {
        List<TransactionWithCategory> transactionsWithCategories = transactionDao.getTransactionsWithCategories(userId, dateRange[0], dateRange[1]);
        Map<String, Float> expenseTotals = new HashMap<>();
        Map<String, Float> incomeTotals = new HashMap<>();

        // Phân loại giao dịch vào expenseTotals và incomeTotals
        for (TransactionWithCategory twc : transactionsWithCategories) {
            Transaction transaction = twc.getTransaction();
            Category category = twc.getCategory();

            if (category != null) {
                String cateName = category.getCateName();
                float amount = (float) transaction.getAmount();

                // Chi tiêu
                if (transaction.getCateId() >= 1 && transaction.getCateId() <= 32) {
                    expenseTotals.put(cateName, expenseTotals.getOrDefault(cateName, 0f) + amount);
                }
                // Thu nhập
                else if (transaction.getCateId() > 32) {
                    incomeTotals.put(cateName, incomeTotals.getOrDefault(cateName, 0f) + amount);
                }
            }
        }

        // Cập nhật biểu đồ chi tiêu
        updatePieChartByCate(pieChartExpense, expenseTotals);
        // Cập nhật biểu đồ thu nhập
        updatePieChartByCate(pieChartIncome, incomeTotals);
    }

    private void updatePieChartByCate(PieChart pieChart, Map<String, Float> totals) {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : totals.entrySet()) {
            pieEntries.add(new PieEntry(entry.getValue(), entry.getKey())); // Sử dụng cateName làm label
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieDataSet.setValueTextSize(10f);

        // Chỉ hiển thị phần trăm, không có tên danh mục hoặc giá trị thực tế
        pieDataSet.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(false); // Tắt hiển thị nhãn bên trong

        pieChart.setDrawHoleEnabled(true); // Bật vẽ hình tròn bên trong
        pieChart.setHoleColor(Color.TRANSPARENT); // Màu của hình tròn bên trong (có thể thay đổi)
        pieChart.setHoleRadius(60f); // Thiết lập bán kính hình tròn bên trong (tùy chỉnh theo ý thích)
        // Thiết lập Legend
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.WHITE); // Màu chữ cho legend
        legend.setTextSize(12f); // Kích thước chữ cho legend
        legend.setForm(Legend.LegendForm.SQUARE); // Hình dạng của biểu tượng
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER); // Căn chỉnh theo chiều ngang
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); // Căn chỉnh theo chiều dọc xuống dưới
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL); // Đặt hướng của legend là ngang

        pieChart.setData(new PieData(pieDataSet));
        pieChart.invalidate();
    }

}
