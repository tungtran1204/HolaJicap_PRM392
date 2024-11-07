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
import com.example.holajicap.TransactionRepository;
import com.example.holajicap.TransactionViewModel;
import com.example.holajicap.adapter.SpendingAdapter;
import com.example.holajicap.dao.TransactionDao;
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

    private static final String TAG = "OverviewFragment";

    public OverviewFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        Log.d("OverviewFragment", "Retrieved current UserId: " + userId);
        Log.d("OverviewFragment", "UserId: " + userId);

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

        // khoi tao
        transactionViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(TransactionViewModel.class);

        // Ánh xạ các TextView
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
                textViewRevenueCurrentMonth.setText((revenue != null ? revenue : 0) + " VND" );
            }
        });

        transactionViewModel.getTotalExpenditureCurrentMonth(userId).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double expenditure) {
                textViewExpenditureCurrentMonth.setText((expenditure != null ? expenditure : 0)+ " VND");
            }
        });

        transactionViewModel.getTotalRevenueLastMonth(userId).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double revenue) {
                textViewRevenueLastMonth.setText((revenue != null ? revenue : 0) + " VND");
            }
        });

        transactionViewModel.getTotalExpenditureLastMonth(userId).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double expenditure) {
                textViewExpenditureLastMonth.setText((expenditure != null ? expenditure : 0) + " VND");
            }
        });
    }

    private void calculateAndDisplayTotalBalance() {
        int totalBalance = 0;
        for (Wallet wallet : db.walletDao().getWalletsByUserId(userId)) {
            int balance = (int) wallet.getBalance();
            totalBalance += balance;
        }
        totalAmountTextView1.setText(String.format(Locale.getDefault(), "%,d VND", totalBalance));
        totalAmountTextView2.setText(String.format(Locale.getDefault(), "%,d VND", totalBalance));
    }


    private void loadTopCategories() {

        Log.d("OverviewFragment", "Using userId: " + userId);

        new Thread(() -> {
            TransactionDao transactionDao = db.transactionDao();

            List<CategorySpending> topCategories = transactionDao.getTotalAmountPerCategory(userId);
            Log.d("OverviewFragment", "Top categories loaded from DB: " + (topCategories != null ? topCategories.size() : "null"));

            requireActivity().runOnUiThread(() -> {
                if (spendingAdapter != null) {
                    Log.d("OverviewFragment", "Updating adapter with top categories data");
                    spendingAdapter.setData(topCategories);
                } else {
                    Log.e("OverviewFragment", "spendingAdapter is null, cannot update data");
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
        Log.d("OverviewFragment", "refreshData() called. Refreshing data in OverviewFragment.");

        // Gọi lại các phương thức tải dữ liệu để làm mới UI
        calculateAndDisplayTotalBalance();
        loadTopCategories();
    }

}
