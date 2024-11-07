package com.example.holajicap.fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.MonthlyReportActivity;
import com.example.holajicap.MyWalletActivity;
import com.example.holajicap.NotificationActivity;
import com.example.holajicap.R;
import com.example.holajicap.TransactionViewModel;
import com.example.holajicap.adapter.SpendingAdapter;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.CategorySpending;
import com.example.holajicap.model.Wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OverviewFragment extends Fragment {

    private ImageView ivNotification;
    private TextView tvSeeAll;
    private TextView tvViewReport;
    private RecyclerView spendingRecyclerView;
    private SpendingAdapter spendingAdapter;
    private HolaJicapDatabase db;
    private TextView totalAmountTextView1;
    private TextView totalAmountTextView2;
    private int userId;

    private TransactionViewModel transactionViewModel;
    private TextView textViewRevenueCurrentMonth;
    private TextView textViewExpenditureCurrentMonth;
    private TextView textViewRevenueLastMonth;
    private TextView textViewExpenditureLastMonth;
    private String currencyCode;

    private static final String TAG = "OverviewFragment";

    public OverviewFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        currencyCode = sharedPreferences.getString("currency_code", "VND");

        Log.d(TAG, "Retrieved current UserId: " + userId);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        // Initialize views
        ivNotification = view.findViewById(R.id.iv_notification);
        tvSeeAll = view.findViewById(R.id.tv_see_all);
        tvViewReport = view.findViewById(R.id.tv_view_report);
        totalAmountTextView1 = view.findViewById(R.id.tv_total_balance);
        totalAmountTextView2 = view.findViewById(R.id.tv_cash_balance);

        // Set up click listeners for interactions
        ivNotification.setOnClickListener(v -> openNotificationScreen());
        tvSeeAll.setOnClickListener(v -> openMyWalletScreen());
        tvViewReport.setOnClickListener(v -> openMonthlyReportScreen());

        // Initialize RecyclerView
        spendingRecyclerView = view.findViewById(R.id.spendingRecyclerView);
        spendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize ViewModel
        transactionViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(TransactionViewModel.class);

        // Initialize TextViews for revenue and expenditure
        textViewRevenueCurrentMonth = view.findViewById(R.id.textViewRevenueCurrentMonth);
        textViewExpenditureCurrentMonth = view.findViewById(R.id.textViewExpenditureCurrentMonth);
        textViewRevenueLastMonth = view.findViewById(R.id.textViewRevenueLastMonth);
        textViewExpenditureLastMonth = view.findViewById(R.id.textViewExpenditureLastMonth);

        // Initialize adapter with an empty list initially
        spendingAdapter = new SpendingAdapter(getContext(), new ArrayList<>());
        spendingRecyclerView.setAdapter(spendingAdapter);


        // Initialize database
        db = HolaJicapDatabase.getInstance(getContext());

        // List total amount
        calculateAndDisplayTotalBalance();
        // Load data
        loadTopCategories();
        // Thiết lập observer cho LiveData
        setupObservers();

        refreshData();
        return view;
    }

    private void setupObservers() {
        transactionViewModel.getTotalRevenueCurrentMonth(userId).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double revenue) {
                textViewRevenueCurrentMonth.setText(String.format(Locale.getDefault(), "%.2f %s", revenue != null ? revenue : 0, currencyCode));
            }
        });

        transactionViewModel.getTotalExpenditureCurrentMonth(userId).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double expenditure) {
                textViewExpenditureCurrentMonth.setText(String.format(Locale.getDefault(), "%.2f %s", expenditure != null ? expenditure : 0, currencyCode));
            }
        });

        transactionViewModel.getTotalRevenueLastMonth(userId).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double revenue) {
                textViewRevenueLastMonth.setText(String.format(Locale.getDefault(), "%.2f %s", revenue != null ? revenue : 0, currencyCode));
            }
        });

        transactionViewModel.getTotalExpenditureLastMonth(userId).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double expenditure) {
                textViewExpenditureLastMonth.setText(String.format(Locale.getDefault(), "%.2f %s", expenditure != null ? expenditure : 0, currencyCode));
            }
        });
    }

    private void calculateAndDisplayTotalBalance() {
        int totalBalance = 0;
        for (Wallet wallet : db.walletDao().getWalletsByUserId(userId)) {
            int balance = (int) wallet.getBalance();
            totalBalance += balance;
        }
        totalAmountTextView1.setText(String.format(Locale.getDefault(), "%,d %s", totalBalance, currencyCode));
        totalAmountTextView2.setText(String.format(Locale.getDefault(), "%,d %s", totalBalance, currencyCode));
    }

    private void loadTopCategories() {
        Log.d(TAG, "Using userId: " + userId);

        new Thread(() -> {
            List<CategorySpending> topCategories = db.transactionDao().getTotalAmountPerCategory(userId);
            Log.d(TAG, "Top categories loaded from DB: " + (topCategories != null ? topCategories.size() : "null"));

            requireActivity().runOnUiThread(() -> {
                if (spendingAdapter != null) {
                    Log.d(TAG, "Updating adapter with top categories data");
                    spendingAdapter.setData(topCategories);
                } else {
                    Log.e(TAG, "spendingAdapter is null, cannot update data");
                }
            });
        }).start();
    }

    private void openNotificationScreen() {
        Intent intent = new Intent(getActivity(), NotificationActivity.class);
        startActivity(intent);
    }

    private void openMyWalletScreen() {
        Intent intent = new Intent(getActivity(), MyWalletActivity.class);
        startActivity(intent);
    }

    private void openMonthlyReportScreen() {
        Intent intent = new Intent(getActivity(), MonthlyReportActivity.class);
        startActivity(intent);
    }

    public void refreshData() {
        Log.d(TAG, "refreshData() called. Refreshing data in OverviewFragment.");
        calculateAndDisplayTotalBalance();
        loadTopCategories();
    }


}