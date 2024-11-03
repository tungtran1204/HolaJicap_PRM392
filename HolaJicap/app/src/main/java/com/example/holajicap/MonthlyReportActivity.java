
package com.example.holajicap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holajicap.dao.TransactionDao;
import com.example.holajicap.dao.WalletDao;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Transaction;
import com.example.holajicap.model.Wallet;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MonthlyReportActivity extends AppCompatActivity {
    private HolaJicapDatabase db;
    private BarChart barChart;
    private TransactionDao transactionDao;
    private TextView totalAmountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);
        db = HolaJicapDatabase.getInstance(getApplicationContext());
        barChart = findViewById(R.id.barChart);
        totalAmountTextView = findViewById(R.id.total_amount);
        transactionDao = HolaJicapDatabase.getInstance(getApplicationContext()).transactionDao();
        logAllTransactions();

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        setupTabLayout(tabLayout);
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabLayout.getTabAt(1).select();
    }

    private int calculateTotalBalance() {
        int totalBalance = 0;
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        List<Wallet> wallets = db.walletDao().getWalletsByUserId(userId); // Lấy danh sách các ví
        for (Wallet wallet : wallets) {
            totalBalance += wallet.getBalance(); // Cộng dồn số dư của mỗi ví
        }
        return totalBalance; // Trả về tổng số dư
    }

    private void updateBarChart() {
        // Thiết lập khoảng ngày cho tháng hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = "2024/01/01";
//        String startDate = formatDate(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = "2024/01/31";
//        String endDate = formatDate(calendar.getTime());

        // Lấy danh sách giao dịch trong tháng
        List<Transaction> transactions = transactionDao.getTransactionsInRange(startDate, endDate);
        Log.d("MonthlyReportActivity", "Số lượng giao dịch lấy được: " + transactions.size());

        int totalBalance = calculateTotalBalance();
        totalAmountTextView.setText(String.format(Locale.getDefault(), "%,d", totalBalance));

        // Tạo mảng lưu trữ tổng số tiền từng phần của tháng
        float[] amounts = new float[5];
        float[] incomeTotals = new float[5]; // Tổng thu nhập
        float[] expenseTotals = new float[5]; // Tổng chi tiêu
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        // Phân loại giao dịch thành 5 phần của tháng
        for (Transaction transaction : transactions) {
            try {
                Date date = sdf.parse(transaction.getDate());
                Calendar transactionCalendar = Calendar.getInstance();
                transactionCalendar.setTime(date);
                int day = transactionCalendar.get(Calendar.DAY_OF_MONTH);
                int index;
                if (day >= 1 && day <= 6) index = 0;
                else if (day >= 7 && day <= 13) index = 1;
                else if (day >= 14 && day <= 20) index = 2;
                else if (day >= 21 && day <= 27) index = 3;
                else index = 4;

                // Tính toán tổng thu và chi dựa trên cateId
                if (transaction.getCateId() > 32) { // Thu nhập
                    incomeTotals[index] += transaction.getAmount();
                } else if (transaction.getCateId() >= 1 && transaction.getCateId() <= 32) { // Chi tiêu
                    expenseTotals[index] -= Math.abs(transaction.getAmount()); // Dùng giá trị âm cho chi tiêu
                }
            } catch (Exception e) {
                Log.e("MonthlyReportActivity", "Lỗi parsing date: " + transaction.getDate(), e);
            }
        }

        // Tạo dữ liệu cho biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            entries.add(new BarEntry(i, new float[]{incomeTotals[i], expenseTotals[i]})); // Gán tổng thu và chi (chi tiêu là số âm)
        }

        // Thiết lập DataSet dạng stacked bar
        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.red));
        dataSet.setStackLabels(new String[]{"Thu nhập", "Chi tiêu"});
        dataSet.setDrawValues(false); // Ẩn số trên đỉnh cột

        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.getLegend().setEnabled(true);
        Legend legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(getResources().getColor(R.color.white));
        setupAxes();
        barChart.invalidate();
    }

    private void logAllTransactions() {
        List<Transaction> allTransactions = transactionDao.getAll();
        for (Transaction transaction : allTransactions) {
            Log.d("MonthlyReportActivity", "Transaction: " + transaction.toString());
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return sdf.format(date);
    }

    private void setupTabLayout(@NonNull TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setText("Tháng trước"));
        tabLayout.addTab(tabLayout.newTab().setText("Tháng này"));

        // Handle tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateChartDataForSelectedMonth(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void updateChartDataForSelectedMonth(int month) {
        if (month == 0) {
            updateBarChartForPreviousMonth();
        } else {
            updateBarChartForCurrentMonth();
        }
    }

    private void updateBarChartForCurrentMonth() {
        updateBarChart();
    }

    private void updateBarChartForPreviousMonth() {
        // Cập nhật khoảng thời gian cho tháng trước
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1); // Giảm một tháng
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = formatDate(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = formatDate(calendar.getTime());

        // Lấy danh sách giao dịch trong tháng trước
        List<Transaction> transactions = transactionDao.getTransactionsInRange(startDate, endDate);
        Log.d("MonthlyReportActivity", "Số lượng giao dịch tháng trước: " + transactions.size());

        // Tạo mảng lưu trữ tổng số tiền từng phần của tháng
        float[] amounts = new float[5];
        float[] incomeTotals = new float[5]; // Tổng dương cho từng cột
        float[] expenseTotals = new float[5]; // Tổng âm cho từng cột

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        // Phân loại giao dịch thành 5 phần của tháng
        for (Transaction transaction : transactions) {
            try {
                Date date = sdf.parse(transaction.getDate());
                Calendar transactionCalendar = Calendar.getInstance();
                transactionCalendar.setTime(date);
                int day = transactionCalendar.get(Calendar.DAY_OF_MONTH);

                int index;
                if (day >= 1 && day <= 6) index = 0;
                else if (day >= 7 && day <= 13) index = 1;
                else if (day >= 14 && day <= 20) index = 2;
                else if (day >= 21 && day <= 27) index = 3;
                else index = 4;

                if (transaction.getCateId() > 32) {
                    incomeTotals[index] += transaction.getAmount();
                } else if (transaction.getCateId() >= 1 && transaction.getCateId() <= 32) {
                    expenseTotals[index] -= Math.abs(transaction.getAmount());
                }
            } catch (Exception e) {
                Log.e("MonthlyReportActivity", "Lỗi parsing date: " + transaction.getDate(), e);
            }
        }

        // Tạo dữ liệu cho biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            entries.add(new BarEntry(i, new float[]{incomeTotals[i], expenseTotals[i]})); // Gán tổng thu và chi (chi tiêu là số âm)
        }

        // Thiết lập DataSet dạng stacked bar
        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(getResources().getColor(R.color.blue), getResources().getColor(R.color.red));
        dataSet.setStackLabels(new String[]{"Thu nhập", "Chi tiêu"});
        dataSet.setDrawValues(false); // Ẩn số trên đỉnh cột

        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.getLegend().setEnabled(true);
        Legend legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(getResources().getColor(R.color.white));
        // Thiết lập trục X và Y
        setupAxes();
        barChart.invalidate();
    }

    private void setupAxes() {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"1-6", "7-13", "14-20", "21-27", "28-31"}));
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getResources().getColor(R.color.white));

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(getResources().getColor(R.color.white));

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
    }
}
