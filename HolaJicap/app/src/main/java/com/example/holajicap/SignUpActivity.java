package com.example.holajicap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.User;
import com.example.holajicap.model.Wallet;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText emailInput, passwordInput;
    private HolaJicapDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.black)); // Đặt màu đen
        }
        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        TextView signUpTextView = findViewById(R.id.signInText);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
//==============Sign up=================
        //==============Sign up=================
        db = HolaJicapDatabase.getInstance(getApplicationContext());
        emailInput = findViewById(R.id.usname);
        passwordInput = findViewById(R.id.password);
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(SignUpActivity.this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.userDao().checkEmailExists(email) == null) {
                        User newUser = new User(0, null, password, email, 1);
                        db.userDao().signUp(newUser);

                        User registeredUser = db.userDao().getUserByEmail(email);

                        if (registeredUser != null) {
                            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putInt("userId", registeredUser.getUid());
                            editor.apply();

                            Date currentDate = new Date();
                            Wallet newWallet1 = new Wallet(0, registeredUser.getUid(), "Ví tiền mặt",
                                    0, "đ", "Sử dụng tiền mặt", currentDate);
                            Wallet newWallet2 = new Wallet(0, registeredUser.getUid(), "Ví thẻ",
                                    0, "đ", "Sử dụng thẻ", currentDate);
                            Wallet newWallet3 = new Wallet(0, registeredUser.getUid(), "Khác",
                                    0, "đ", "Sử dụng phương thức khác", currentDate);

                            db.walletDao().insert(newWallet1);
                            db.walletDao().insert(newWallet2);
                            db.walletDao().insert(newWallet3);

                            Log.d("SignUpActivity", "Đăng ký thành công: " + registeredUser.getEmail());
                            Intent intent = new Intent(SignUpActivity.this, NavigationActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Lỗi đăng ký người dùng!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}