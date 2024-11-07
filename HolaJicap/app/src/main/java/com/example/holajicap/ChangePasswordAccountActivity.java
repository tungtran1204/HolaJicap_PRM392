package com.example.holajicap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.User;

public class ChangePasswordAccountActivity extends AppCompatActivity {
    private HolaJicapDatabase db;
    private EditText edtcurrentpass;
    private EditText edtnewpass;
    private EditText edtcfpass;
    private Button btncfpass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_account);

        db = HolaJicapDatabase.getInstance(this);


        edtcurrentpass = findViewById(R.id.edt_currentpass);
        edtnewpass = findViewById(R.id.edt_newpass);
        edtcfpass = findViewById(R.id.edt_cfpass);
        btncfpass = findViewById(R.id.buttonConfirmChangePassword);

        btncfpass.setOnClickListener(v -> changePassword());

    }

    private void changePassword() {
        String currentPassword = edtcurrentpass.getText().toString();
        String newPassword = edtnewpass.getText().toString();
        String confirmPassword = edtcfpass.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        new Thread(() -> {
            try {
                User user = db.userDao().getUserById(userId);

                if (user != null && user.getPassword().equals(currentPassword)) {
                    if (newPassword.equals(confirmPassword)) {
                        user.setPassword(newPassword);
                        db.userDao().updateUser(user);

                        runOnUiThread(() -> {
                            Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Mật khẩu hiện tại không chính xác", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
