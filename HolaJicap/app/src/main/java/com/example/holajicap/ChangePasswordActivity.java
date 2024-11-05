package com.example.holajicap;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holajicap.dao.UserDao;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.User;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText newPasswordEditText;
    private String userEmail; // Biến lưu trữ email của người dùng
    private TextView emailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Lấy email từ Intent
        userEmail = getIntent().getStringExtra("email");
        emailTextView = findViewById(R.id.email);
        emailTextView.setText(userEmail);
        newPasswordEditText = findViewById(R.id.editTextTextPassword);
        Button confirmButton = findViewById(R.id.button);
        ImageView backArrow = findViewById(R.id.back_arrow);
        Log.d("ChangePasswordActivity", "Email: " + userEmail);
        // Xử lý nút quay lại
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Xử lý nút xác nhận
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString().trim();

                // Kiểm tra xem mật khẩu mới có được nhập không
                if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cập nhật mật khẩu
                updatePassword(userEmail, newPassword);
            }
        });
    }

    private void updatePassword(String email, String newPassword) {
        HolaJicapDatabase db = HolaJicapDatabase.getInstance(this);
        UserDao userDao = db.userDao();

        User user = userDao.checkEmailExists(email);
        if (user != null) {
            user.setPassword(newPassword);
            userDao.updateUser(user); // Gọi phương thức updateUser để cập nhật thông tin
            Toast.makeText(this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ChangePasswordActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
        }
    }
}
