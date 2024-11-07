package com.example.holajicap;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.holajicap.dao.TransactionDao;
import com.example.holajicap.db.HolaJicapDatabase;

public class TransactionRepository {
    private static final String TAG = "TransactionRepository"; // Tag cho log
    private TransactionDao transactionDao;

    public TransactionRepository(Application application) {
        HolaJicapDatabase db = HolaJicapDatabase.getInstance(application);
        transactionDao = db.transactionDao();
    }

    public LiveData<Double> getTotalRevenueCurrentMonth(int userId) {
        LiveData<Double> revenue = transactionDao.getTotalRevenueCurrentMonth(userId);
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
        LiveData<Double> expenditure = transactionDao.getTotalExpenditureCurrentMonth(userId);
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
        LiveData<Double> revenue = transactionDao.getTotalRevenueLastMonth(userId);
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
        LiveData<Double> expenditure = transactionDao.getTotalExpenditureLastMonth(userId);
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