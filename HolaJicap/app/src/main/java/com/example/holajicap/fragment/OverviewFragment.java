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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.MonthlyReportActivity;
import com.example.holajicap.MyWalletActivity;
import com.example.holajicap.NotificationActivity;
import com.example.holajicap.R;
import com.example.holajicap.SpendingData;
import com.example.holajicap.adapter.SpendingAdapter;
import com.example.holajicap.dao.TransactionDao;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Wallet;

import java.util.ArrayList;
import java.util.List;


public class OverviewFragment extends Fragment {

    private ImageView ivNotification;
    private TextView tvSeeAll;
    private TextView tvViewReport;
    private RecyclerView spendingRecyclerView;
    private SpendingAdapter spendingAdapter;
    private HolaJicapDatabase db;
    private int userId;

    public OverviewFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        // Initialize views
        ivNotification = view.findViewById(R.id.iv_notification);
        tvSeeAll = view.findViewById(R.id.tv_see_all);
        tvViewReport = view.findViewById(R.id.tv_view_report);

        // Set up click listeners for interactions
        ivNotification.setOnClickListener(v -> openNotificationScreen());
        tvSeeAll.setOnClickListener(v -> openMyWalletScreen());
        tvViewReport.setOnClickListener(v -> openMonthlyReportScreen());

        // Initialize the adapter and RecyclerView
        spendingRecyclerView = view.findViewById(R.id.spendingRecyclerView);
        spendingAdapter = new SpendingAdapter(requireContext());
        spendingRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        spendingRecyclerView.setAdapter(spendingAdapter);
        db = HolaJicapDatabase.getInstance(requireContext());

        // Initialize database instance
        db = HolaJicapDatabase.getInstance(requireContext());

        // Load data into the adapter
        loadTopCategories();

        return view;
    }



    private void loadTopCategories() {
        int userId = getCurrentUserId();

        new Thread(() -> {
            TransactionDao transactionDao = db.transactionDao();
            List<SpendingData> topCategories = transactionDao.getTopCategoriesWithPercentage(userId);

            for (SpendingData data : topCategories) {
                Log.d("OverviewFragment", "Category: " + data.getCateName() + ", Total Amount: "
                        + data.getTotalAmount() + ", Percentage: " + data.getPercentage());
            }

            requireActivity().runOnUiThread(() -> spendingAdapter.setData(topCategories));
        }).start();
    }

    private int getCurrentUserId() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("UserId", -1); // Default to -1 if no user is found
    }


    private void openNotificationScreen() {
        Intent intent = new Intent(getActivity(), NotificationActivity.class);
        startActivity(intent);
    }
    //
    private void openMyWalletScreen() {
        Intent intent = new Intent(getActivity(), MyWalletActivity.class);
        startActivity(intent);
    }

    private void openMonthlyReportScreen() {
        Intent intent = new Intent(getActivity(), MonthlyReportActivity.class);
        startActivity(intent);
    }



}
