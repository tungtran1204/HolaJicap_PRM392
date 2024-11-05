package com.example.holajicap;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class OtpActivity extends AppCompatActivity {
    private EditText otpEditText;
    private String sentOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sentOtp = getIntent().getStringExtra("otp");
        otpEditText = findViewById(R.id.otp);
        Button confirmButton = findViewById(R.id.button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp = otpEditText.getText().toString().trim();

                // Kiểm tra OTP có được nhập chưa
                if (TextUtils.isEmpty(enteredOtp)) {
                    Toast.makeText(OtpActivity.this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
                    return;
                }

                // So sánh mã OTP nhập vào với OTP đã gửi
                if (enteredOtp.equals(sentOtp)) {
                    // Chuyển sang trang đổi mật khẩu
                    Intent intent = new Intent(OtpActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("email", getIntent().getStringExtra("email"));
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(OtpActivity.this, "Mã OTP không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}