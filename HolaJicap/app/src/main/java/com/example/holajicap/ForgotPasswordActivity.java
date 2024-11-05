package com.example.holajicap;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holajicap.dao.UserDao;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.User;
import com.example.holajicap.utils.Mail;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText emailEditText;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.otp);
        userDao = HolaJicapDatabase.getInstance(this).userDao();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button sendButton = findViewById(R.id.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgotPasswordActivity.this,
                            "Vui lòng nhập email của bạn",
                            Toast.LENGTH_SHORT).show();
                } else {
                    User user = userDao.checkEmailExists(email);
                    if (user == null) {
                        Toast.makeText(ForgotPasswordActivity.this,
                                "Email này chưa được đăng ký",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        String otp = generateOTP();
                        String subject = "HolaJicap OTP";
                        String body = "<html><body>" +
                                "<p>Mã OTP của bạn là: <strong>" + otp + "</strong></p>" +
                                "<img src='https://octopush.com/wp-content/uploads/2022/12/LV-2-code-otp-1.png' " +
                                "alt='OTP Image' width='200' height='100'/>" +
                                "</body></html>";

                        Mail.send(email, subject, body);

                        Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                        intent.putExtra("otp", otp);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        Toast.makeText(ForgotPasswordActivity.this,
                                "Đã gửi mã OTP đến email của bạn!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private String generateOTP() {
        int otpLength = 6;
        StringBuilder otp = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
