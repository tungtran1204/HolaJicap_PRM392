package com.example.holajicap;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity {
    private EditText editTextAmount;
    private TextView tvChooseTransactionType;
    private EditText editTextNotes;
    private TextView tvChooseTransactionMethod;
    private Button saveButton;
    private TextView dateTextView;
    // Add intent chooseTransactionMethod
    private ActivityResultLauncher<Intent> chooseTransactionMethodLauncher;
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
        tvChooseTransactionType = findViewById(R.id.tv_chooseTransactionType);
        editTextNotes = findViewById(R.id.editTextNotes);
        tvChooseTransactionMethod = findViewById(R.id.tv_chooseTransactionMethod);
        saveButton = findViewById(R.id.saveButton);
        dateTextView = findViewById(R.id.dateTextView);
        // Initialize views for chooseTransactionMethod
        tvChooseTransactionMethod = findViewById(R.id.tv_chooseTransactionMethod);
        tvChooseTransactionType = findViewById(R.id.tv_chooseTransactionType);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTransaction();
            }
        });

        // Khởi tạo launcher cho chooseTransactionMethod
        chooseTransactionMethodLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        // Lấy dữ liệu từ Intent và cập nhật TextView
                        String selectedMethod = result.getData().getStringExtra("selectedMethod");
                        tvChooseTransactionMethod.setText(selectedMethod);
                    }
                }
        );
        // Khởi tạo launcher cho chooseTransactionType
        chooseTransactionTypeLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        // Lấy dữ liệu từ Intent và cập nhật TextView
                        String selectedType = result.getData().getStringExtra("selectedType");
                        tvChooseTransactionMethod.setText(selectedType);
                    }
                }
        );
        // Thiết lập sự kiện cho TextView "Choose Transaction Method"
        tvChooseTransactionMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTransactionActivity.this, ChooseTransactionMethodTypeActivity.class);
                startActivityForResult(intent, 1); // Sử dụng startActivityForResult thay vì startActivity
            }
        });
        tvChooseTransactionType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi mở ChooseTransactionTypeActivity từ AddTransactionActivity
                Intent intent = new Intent(AddTransactionActivity.this, ChooseTransactionTypeActivity.class);
                startActivityForResult(intent, 1);  // 1 là requestCode

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Lấy kết quả từ Intent và cập nhật TextView
            String selectedMethod = data.getStringExtra("selectedMethod");
            if (selectedMethod != null) {
                tvChooseTransactionMethod.setText(selectedMethod);
            }
            // Lấy dữ liệu từ Intent của chooseType trả về
            String selectedTitle = data.getStringExtra("selectedTitle");
            int selectedIcon = data.getIntExtra("selectedIcon", -1);  // -1 là giá trị mặc định nếu không có dữ liệu

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
                    dateTextView.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                }, year, month, day);

        datePickerDialog.show();
    }

    private void saveTransaction() {
        // Get input values
        String amount = editTextAmount.getText().toString();
        String transactionType = tvChooseTransactionType.getText().toString();
        String date = dateTextView.getText().toString();
        String transactionMethod = tvChooseTransactionMethod.getText().toString();
        String notes = editTextNotes.getText().toString();

        // Kiểm tra dữ liệu và lưu giao dịch
        if (amount.isEmpty()) {
            Toast.makeText(this, "Please input the amount", Toast.LENGTH_SHORT).show();
            return;
        }
        if (transactionType.isEmpty()){
            Toast.makeText(this, "Please choose a transaction type", Toast.LENGTH_SHORT).show();
            return;
        }
        if (date.isEmpty()){
            Toast.makeText(this, "Please choose date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (transactionMethod.isEmpty()){
            Toast.makeText(this, "Please choose a transaction method", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lưu dữ liệu (ví dụ như lưu vào CSDL hoặc hiện thông báo thành công)
        Toast.makeText(this, "Thêm giao dịch thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void linkToChooseTransactionTypeActivity(View view) {
        // Handle click on "Choose Transaction Type"
        // Start ChooseTransactionTypeActivity
        // You can use an Intent to navigate to the new activity
         Intent intent = new Intent(this, ChooseTransactionTypeActivity.class);
         startActivity(intent);
    }

    public void linkToChooseTransactionMethodActivity(View view) {
        Intent intent = new Intent(this, ChooseTransactionMethodTypeActivity.class);
        startActivity(intent);
    }
}