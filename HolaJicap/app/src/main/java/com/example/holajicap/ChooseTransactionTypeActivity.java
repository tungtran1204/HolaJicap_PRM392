package com.example.holajicap;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.holajicap.fragment.ExpenditureFragment;
import com.example.holajicap.fragment.RevenueFragment;
import com.google.android.material.tabs.TabLayout;

public class ChooseTransactionTypeActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_transaction_type);
        // Initialize fragment
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ExpenditureFragment())
                .addToBackStack(null)
                .commit();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()){
                    case 0:
                        fragment = new ExpenditureFragment();
                        break;
                    case 1:
                        fragment = new RevenueFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        // Initialize toolbar
        ImageView back_icon = findViewById(R.id.left_icon);
        TextView title = findViewById(R.id.toolbar_title);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When click on back_icon, back to AddTransactionActivity
                finish();
            }
        });
        title.setText("Chọn nhóm");


    }
}