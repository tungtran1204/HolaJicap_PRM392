package com.example.holajicap;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.example.holajicap.adapter.FragmentPagerAdapter;
import com.example.holajicap.fragment.AccountFragment;
import com.example.holajicap.fragment.OverviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ViewPager viewPager = findViewById(R.id.viewPager);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverviewFragment());   // Position 0
//        adapter.addFragment(new WalletFragment()); // Position 1
//        adapter.addFragment(new AddFragment());    // Position 2 - Cho nay dung Activity -> Ko dung Fragment
        adapter.addFragment(new AccountFragment()); // Position 3

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
                Intent intent = new Intent(NavigationActivity.this, ViewTransactionActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.mAdd) {
                Intent intent = new Intent(NavigationActivity.this, AddTransactionActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.mAccount) {
                viewPager.setCurrentItem(3);
                return true;
            }
            return false;
        });
    }

}
