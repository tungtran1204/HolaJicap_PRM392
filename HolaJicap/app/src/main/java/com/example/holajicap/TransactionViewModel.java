package com.example.holajicap;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TransactionViewModel extends AndroidViewModel {
    private static final String TAG = "TransactionViewModel"; // Tag cho log
    private TransactionRepository repository;

    // Constructor nhận Application
    public TransactionViewModel(Application application) {
        super(application);
        repository = new TransactionRepository(application);
    }

    // Các phương thức để lấy dữ liệu từ repository
    public LiveData<Double> getTotalRevenueCurrentMonth(int userId) {
        LiveData<Double> revenue = repository.getTotalRevenueCurrentMonth(userId);
        revenue.observeForever(result -> {
            if (result != null) {
                Log.d(TAG, "Doanh thu tháng này cho userId " + userId + ": " + result);
            } else {
                Log.d(TAG, "Doanh thu tháng này cho userId " + userId + " là null");
            }
        });
        return revenue;
    }

    public LiveData<Double> getTotalExpenditureCurrentMonth(int userId) {
        LiveData<Double> expenditure = repository.getTotalExpenditureCurrentMonth(userId);
        expenditure.observeForever(result -> {
            if (result != null) {
                Log.d(TAG, "Chi tiêu tháng này cho userId " + userId + ": " + result);
            } else {
                Log.d(TAG, "Chi tiêu tháng này cho userId " + userId + " là null");
            }
        });
        return expenditure;
    }

    public LiveData<Double> getTotalRevenueLastMonth(int userId) {
        LiveData<Double> revenue = repository.getTotalRevenueLastMonth(userId);
        revenue.observeForever(result -> {
            if (result != null) {
                Log.d(TAG, "Doanh thu tháng trước cho userId " + userId + ": " + result);
            } else {
                Log.d(TAG, "Doanh thu tháng trước cho userId " + userId + " là null");
            }
        });
        return revenue;
    }

    public LiveData<Double> getTotalExpenditureLastMonth(int userId) {
        LiveData<Double> expenditure = repository.getTotalExpenditureLastMonth(userId);
        expenditure.observeForever(result -> {
            if (result != null) {
                Log.d(TAG, "Chi tiêu tháng trước cho userId " + userId + ": " + result);
            } else {
                Log.d(TAG, "Chi tiêu tháng trước cho userId " + userId + " là null");
            }
        });
        return expenditure;
    }
}