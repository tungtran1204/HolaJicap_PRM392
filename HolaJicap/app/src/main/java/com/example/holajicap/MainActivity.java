package com.example.holajicap;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.holajicap.adapter.ViewPagerAdapter;

import me.relex.circleindicator.CircleIndicator3;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private ViewPagerAdapter adapter;
    private CircleIndicator3 indicator;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    //Test HNHiep
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton = findViewById(R.id.signIn);
        Button registerButton = findViewById(R.id.signUp);
        // Thay đổi màu của status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.black)); // Đặt màu đen
        }
        // Khởi tạo ViewPager2 và CircleIndicator
        viewPager2 = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);

        // Dữ liệu hình ảnh và tiêu đề
        List<String> titles = Arrays.asList("Tiết kiệm", "Quản lý tài chính", "Giảm chi tiêu", "Giảm chi tiêu", "Giảm chi tiêu");
        List<Integer> images = Arrays.asList(R.drawable.login, R.drawable.login, R.drawable.login, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground);

        // Khởi tạo Adapter và set cho ViewPager
        adapter = new ViewPagerAdapter(titles, images);
        viewPager2.setAdapter(adapter);

        // Liên kết CircleIndicator với ViewPager2
        indicator.setViewPager(viewPager2);
        // Tạo Handler để tự động lướt
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == viewPager2.getAdapter().getItemCount()) {
                    currentPage = 0;
                }
                viewPager2.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // Thay đổi 3000 thành thời gian bạn muốn (3 giây)
            }
        };
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); // Dừng lướt khi Activity không còn hiển thị
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000); // Bắt đầu lại lướt khi Activity được hiển thị
    }
}


