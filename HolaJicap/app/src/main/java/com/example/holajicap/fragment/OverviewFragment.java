package com.example.holajicap.fragment;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.holajicap.adapter.SpendingAdapter;
import com.example.holajicap.model.SpendingItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class OverviewFragment extends Fragment {

    private ImageView ivNotification;
    private TextView tvSeeAll;
    private TextView tvViewReport;

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

        //Spending Recycle View
        RecyclerView rvHighestSpending = view.findViewById(R.id.rv_spending_list);
        rvHighestSpending.setLayoutManager(new LinearLayoutManager(getContext()));

        List<SpendingItem> spendingItems = new ArrayList<>();
        spendingItems.add(new SpendingItem(R.drawable.baseline_message_24, "Food & Beverage", 80));
        spendingItems.add(new SpendingItem(R.drawable.baseline_message_24, "Rental", 15));
        spendingItems.add(new SpendingItem(R.drawable.baseline_message_24, "Shopping", 5));

        SpendingAdapter adapter = new SpendingAdapter(spendingItems);
        rvHighestSpending.setAdapter(adapter);

        return view;
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
    //
    private void openMonthlyReportScreen() {
        Intent intent = new Intent(getActivity(), MonthlyReportActivity.class);
        startActivity(intent);
    }



}
