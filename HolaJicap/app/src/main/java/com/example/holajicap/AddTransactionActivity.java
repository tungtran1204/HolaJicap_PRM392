package com.example.holajicap;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {
    private EditText editTextAmount;
    private ImageView imv_category_ava;
    private TextView tvChooseTransactionType;
    private EditText editTextNotes;
    private TextView tvChooseWallet;
    private Button saveButton;
    private TextView dateTextView;
    private String selectedDate;
    private HolaJicapDatabase db;

    private int selectedWalletId = -1;
    private int selectedCategoryId = -1;
    private String selectedCategoryType = "";

    // Add intent chooseTransactionMethod
    private ActivityResultLauncher<Intent> chooseWalletLauncher;
    private ActivityResultLauncher<Intent> chooseTransactionTypeLauncher;
    private static final int REQUEST_CODE_CHOOSE_METHOD = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        // Initialize toolbar
        ImageView back_icon = findViewById(R.id.left_icon);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When click on back_icon, back to MainActivity
                finish();
            }
        });
        toolbar_title.setText("Thêm giao dịch");
        // Initialize views
        editTextAmount = findViewById(R.id.editTextAmount);
        imv_category_ava = findViewById(R.id.imv_transaction_type_icon);
        tvChooseTransactionType = findViewById(R.id.tv_chooseTransactionType);
        editTextNotes = findViewById(R.id.editTextNotes);
        tvChooseWallet = findViewById(R.id.tv_chooseWallet);
        saveButton = findViewById(R.id.saveButton);
        dateTextView = findViewById(R.id.dateTextView);


        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveTransaction();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Khởi tạo launcher cho chooseTransactionMethod
        chooseWalletLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        // Lấy dữ liệu từ Intent và cập nhật TextView
                        selectedWalletId = result.getData().getIntExtra("selectedWalletId", -1); // Lưu walletId được chọn
                        String selectedWallet = result.getData().getStringExtra("selectedWallet");
                        tvChooseWallet.setText(selectedWallet);
                    }
                }
        );
        // Khởi tạo launcher cho chooseTransactionType
        chooseTransactionTypeLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        // Nhận icon và tên thẻ đã chọn
                        selectedCategoryId = result.getData().getIntExtra("selectedCategoryId", -1);
                        selectedCategoryType = result.getData().getStringExtra("selectedCategoryType");
                        int selectedIcon = result.getData().getIntExtra("selectedIcon", -1);
                        String selectedType = result.getData().getStringExtra("selectedTitle");

                        // Cập nhật ImageView và TextView với dữ liệu nhận được
                        if (selectedIcon != -1) {
                            imv_category_ava.setImageResource(selectedIcon);
                        }
                        if (selectedType != null) {
                            tvChooseTransactionType.setText(selectedType);
                        }
                    }
                }
        );
        // Thiết lập sự kiện cho TextView "Choose Transaction Method"
        tvChooseWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTransactionActivity.this, ChooseWalletActivity.class);
                chooseWalletLauncher.launch(intent); // Sử dụng startActivityForResult thay vì startActivity
            }
        });
        tvChooseTransactionType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi mở ChooseTransactionTypeActivity từ AddTransactionActivity
                Intent intent = new Intent(AddTransactionActivity.this, ChooseTransactionTypeActivity.class);
                chooseTransactionTypeLauncher.launch(intent);  // 1 là requestCode

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Lấy kết quả từ Intent và cập nhật TextView
            String selectedWallet = data.getStringExtra("selectedWallet");
            if (selectedWallet != null) {
                tvChooseWallet.setText(selectedWallet);
            }
            // Lấy dữ liệu từ Intent của chooseType trả về
            String selectedTitle = data.getStringExtra("selectedTitle");
            int selectedIcon = data.getIntExtra("selectedIcon", -1);  // -1 là giá trị mặc định nếu không có dữ liệu
//            selectedCategoryType = data.getStringExtra("selectedCategoryType");  // Nhận `cateType` từ Intent

            // Cập nhật TextView và ImageView với dữ liệu mới
            TextView transactionTypeTextView = findViewById(R.id.tv_chooseTransactionType);
            ImageView transactionTypeIcon = findViewById(R.id.imv_transaction_type_icon);

            if (selectedTitle != null) {
                transactionTypeTextView.setText(selectedTitle);  // Đặt tên thẻ đã chọn
            }

            if (selectedIcon != -1) {
                transactionTypeIcon.setImageResource(selectedIcon);  // Đặt icon thẻ đã chọn
            }
        }
    }

    private void showDatePickerDialog() {
        // Show date picker dialog
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Cập nhật TextView với ngày đã chọn
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    Date selectedDate = calendar.getTime();

                    // Định dạng ngày theo "yyyy/MM/dd" để lưu
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                    String formattedDate = sdf.format(selectedDate);

                    // Cập nhật TextView hiển thị dạng "dd/MM/yyyy"
                    SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    dateTextView.setText(displayFormat.format(selectedDate));

                    // Lưu lại selectedDate để sau này dùng khi lưu Transaction
                    this.selectedDate = formattedDate;
//                    dateTextView.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                }, year, month, day);

        datePickerDialog.show();
    }

    private void saveTransaction() throws ParseException {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        db = HolaJicapDatabase.getInstance(getApplicationContext());
        // Get input values
        // Kiểm tra và lấy giá trị amount
        String amountText = editTextAmount.getText().toString();
        if (TextUtils.isEmpty(amountText)) {
            Toast.makeText(this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
            return;
        }
        double amount = Double.parseDouble(amountText);

        // Kiểm tra và lấy giá trị notes
        String notes = editTextNotes.getText().toString();

        // Kiểm tra các giá trị còn lại
        String walletType = tvChooseWallet.getText().toString();
        String transactionType = tvChooseTransactionType.getText().toString();


        if (TextUtils.isEmpty(transactionType) || selectedDate == null || TextUtils.isEmpty(walletType)) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin ", Toast.LENGTH_SHORT).show();
            return;
        }
        // Thực hiện các thao tác cơ sở dữ liệu trong transaction để đảm bảo tính toàn vẹn
        db.runInTransaction(() -> {
            // Điều chỉnh số dư ví
            if (selectedCategoryType != null) {
                if (selectedCategoryType.equals("Expenditure")) {
                    // Trừ số tiền cho ví nếu là chi tiêu
                    db.walletDao().updateWalletBalance(selectedWalletId, -amount);
                } else if (selectedCategoryType.equals("Revenue")) {
                    // Cộng số tiền cho ví nếu là thu nhập
                    db.walletDao().updateWalletBalance(selectedWalletId, amount);
                }

                // Thêm giao dịch vào CSDL
                Transaction transaction = new Transaction(0, userId, selectedWalletId, amount, notes, selectedDate, selectedCategoryId);
                db.transactionDao().insert(transaction);

                // Hiển thị thông báo thành công
                runOnUiThread(() -> {
                    Toast.makeText(this, "Thêm giao dịch thành công!", Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                });
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Loại giao dịch không hợp lệ", Toast.LENGTH_SHORT).show());
            }
        });

    }

    public void linkToChooseTransactionTypeActivity(View view) {
        // Handle click on "Choose Transaction Type"
        // Start ChooseTransactionTypeActivity
        // You can use an Intent to navigate to the new activity
         Intent intent = new Intent(this, ChooseTransactionTypeActivity.class);
         startActivity(intent);
    }

    public void linkToChooseWalletActivity(View view) {
        Intent intent = new Intent(this, ChooseWalletActivity.class);
        startActivity(intent);
    }
}
//======================Get UserId=====================
//SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
//int userId = sharedPreferences.getInt("userId", -1);