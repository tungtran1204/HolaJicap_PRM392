package com.example.holajicap;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.charts.PieChart;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.data.PieData;
//import com.github.mikephil.charting.data.PieDataSet;
//import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.tabs.TabLayout;

public class MonthlyReportActivity extends AppCompatActivity {

//    private BarChart barChart;
//    private PieChart pieChartExpense;
//    private PieChart pieChartIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);

        // Back button
        ImageView backButton = findViewById(R.id.iv_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // closes the activity to go back
            }
        });

        // Initialize charts
//        barChart = findViewById(R.id.bar_chart);
//        pieChartExpense = findViewById(R.id.pie_chart_expense);
//        pieChartIncome = findViewById(R.id.pie_chart_income);

        // Setup Tab Layout
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        setupTabLayout(tabLayout);

        // Initialize charts with sample data
//        setupBarChart();
//        setupPieChart(pieChartExpense);
//        setupPieChart(pieChartIncome);
    }

    private void setupTabLayout(@NonNull TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setText("Month 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Month 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Month 3"));

        // Handle tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Logic for switching data based on tab
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

    //    private void setupBarChart() {
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(1, 2000));  // Data for month 1
//        entries.add(new BarEntry(2, 1500));  // Data for month 2
//
//        BarDataSet dataSet = new BarDataSet(entries, "Chi tiêu");
//        dataSet.setColor(getResources().getColor(R.color.red));
//
//        BarData barData = new BarData(dataSet);
//        barChart.setData(barData);
//        barChart.invalidate(); // Refresh chart
//    }
//
//    private void setupPieChart(PieChart pieChart) {
//        ArrayList<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(40, "Category 1"));
//        entries.add(new PieEntry(30, "Category 2"));
//        entries.add(new PieEntry(30, "Category 3"));
//
//        PieDataSet dataSet = new PieDataSet(entries, label);
//        dataSet.setColors(new int[]{R.color.blue, R.color.green, R.color.yellow}, this);
//
//        PieData pieData = new PieData(dataSet);
//        pieChart.setData(pieData);
//        pieChart.invalidate(); // Refresh chart
//    }
//
    private void updateChartDataForSelectedMonth(int month) {
        // Logic for changing data in bar and pie charts based on selected month
    }
}