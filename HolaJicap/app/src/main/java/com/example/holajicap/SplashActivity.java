package com.example.holajicap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false); // Giá trị mặc định là false

        Intent intent;
        if (isLoggedIn) {
            intent = new Intent(SplashActivity.this, AddTransactionActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
