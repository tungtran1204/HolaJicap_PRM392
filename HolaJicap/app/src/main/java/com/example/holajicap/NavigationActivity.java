package com.example.holajicap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;


import com.example.holajicap.adapter.FragmentPagerAdapter;
import com.example.holajicap.fragment.AccountFragment;
import com.example.holajicap.fragment.OverviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int userId;
    private FragmentPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        ViewPager viewPager = findViewById(R.id.viewPager);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverviewFragment());   // Position 0
//        adapter.addFragment(new WalletFragment()); // Position 1
//        adapter.addFragment(new AddFragment());    // Position 2 - Cho nay dung Activity -> Ko dung Fragment
        adapter.addFragment(new AccountFragment());    // Position 3

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    bottomNavigationView.setSelectedItemId(R.id.mHome);
                }
//                else if (position == 1) {
//                    bottomNavigationView.setSelectedItemId(R.id.mWallet);
//                }
//                else if (position == 2) {
//                    bottomNavigationView.setSelectedItemId(R.id.mAdd);
//                }   - Cho nay dung Activity -> Ko dung Fragment
                else if (position == 3) {
                    bottomNavigationView.setSelectedItemId(R.id.mAccount);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.mHome) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (itemId == R.id.mWallet) {
                Log.d("NavigationActivity", "Opening ViewTransactionActivity");
                Intent intent = new Intent(NavigationActivity.this, ViewTransactionActivity.class);
                startActivityForResult(intent, 2); // Use a request code like 2
                return true;
            } else if (itemId == R.id.mAdd) {
                Log.d("NavigationActivity", "Opening AddTransactionActivity");
                Intent intent = new Intent(NavigationActivity.this, AddTransactionActivity.class);
                startActivityForResult(intent, 1);  // Use a request code like 1
                return true;
            } else if (itemId == R.id.mAccount) {
                viewPager.setCurrentItem(3);
                return true;
            }
            return false;
        });
    }

    public Fragment getFragmentAtPosition(int position) {
        return adapter.getItem(position);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Log.d("NavigationActivity", "AddTransactionActivity completed successfully");

            BottomNavigationView bottomNav = findViewById(R.id.navigation);
            bottomNav.setSelectedItemId(R.id.mHome);

            // Check if OverviewFragment is loaded and try refreshing data
            OverviewFragment overviewFragment = (OverviewFragment) adapter.getItem(0); // Giả sử OverviewFragment ở vị trí 0

            if (overviewFragment != null && overviewFragment.isVisible()) {
                Log.d("NavigationActivity", "OverviewFragment is visible. Triggering data refresh.");
                overviewFragment.refreshData();  // Assuming refreshData() is a method in OverviewFragment
            } else {
                Log.d("NavigationActivity", "OverviewFragment not visible or not found. Ensure it's loaded correctly.");
            }
        }

    }

}
