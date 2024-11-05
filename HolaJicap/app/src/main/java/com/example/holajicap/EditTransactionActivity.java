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

public class EditTransactionActivity extends AppCompatActivity {
    private EditText editTextAmount;
    private ImageView imv_category_ava;
    private TextView tvChooseTransactionType;
    private EditText editTextNotes;
    private TextView tvChooseWallet;
    private Button saveButton;
    private Button deleteButton; // Nút xóa
    private TextView dateTextView;
    private String selectedDate;
    private HolaJicapDatabase db;

    private int selectedWalletId = -1;
    private int selectedCategoryId = -1;
    private String selectedCategoryType = "";
    private int transactionId; // ID của giao dịch cần chỉnh sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        transactionId = intent.getIntExtra("transactionId", -1); // ID giao dịch
        double amount = intent.getDoubleExtra("amount", 0);
        String notes = intent.getStringExtra("notes");
        selectedDate = intent.getStringExtra("date");
        selectedWalletId = intent.getIntExtra("walletId", -1);
        selectedCategoryId = intent.getIntExtra("categoryId", -1);
        selectedCategoryType = intent.getStringExtra("categoryType");

        // Khởi tạo cơ sở dữ liệu
        db = HolaJicapDatabase.getInstance(getApplicationContext());

        // Khởi tạo các view
        editTextAmount = findViewById(R.id.editTextAmount);
        imv_category_ava = findViewById(R.id.imv_transaction_type_icon);
        tvChooseTransactionType = findViewById(R.id.tv_chooseTransactionType);
        editTextNotes = findViewById(R.id.editTextNotes);
        tvChooseWallet = findViewById(R.id.tv_chooseWallet);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton); // Khởi tạo nút xóa
        dateTextView = findViewById(R.id.dateTextView);

        // Đổ dữ liệu vào các trường
        editTextAmount.setText(String.valueOf(amount));
        editTextNotes.setText(notes);
        dateTextView.setText(selectedDate); // Đổ ngày vào TextView

        // Thiết lập sự kiện cho nút lưu
        saveButton.setOnClickListener(view -> {
            try {
                updateTransaction();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        // Thiết lập sự kiện cho nút xóa
        deleteButton.setOnClickListener(view -> deleteTransaction());

        // Xử lý sự kiện chọn ngày
        dateTextView.setOnClickListener(view -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        // Hiển thị dialog chọn ngày
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    Date selectedDate = calendar.getTime();

                    // Định dạng ngày theo "yyyy/MM/dd"
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                    String formattedDate = sdf.format(selectedDate);

                    // Cập nhật TextView hiển thị dạng "dd/MM/yyyy"
                    SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    dateTextView.setText(displayFormat.format(selectedDate));

                    // Lưu lại selectedDate để sau này dùng khi cập nhật Transaction
                    this.selectedDate = formattedDate;
                }, year, month, day);

        datePickerDialog.show();
    }

    private void updateTransaction() throws ParseException {
        // Lấy giá trị từ các trường nhập liệu
        String amountText = editTextAmount.getText().toString();
        if (TextUtils.isEmpty(amountText)) {
            Toast.makeText(this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
            return;
        }
        double amount = Double.parseDouble(amountText);
        String notes = editTextNotes.getText().toString();
        String walletType = tvChooseWallet.getText().toString();
        String transactionType = tvChooseTransactionType.getText().toString();

        if (TextUtils.isEmpty(transactionType) || selectedDate == null || TextUtils.isEmpty(walletType)) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin ", Toast.LENGTH_SHORT).show();
            return;
        }

        db.runInTransaction(() -> {
            // Cập nhật giao dịch trong cơ sở dữ liệu
            Transaction transaction = new Transaction(transactionId, 0, selectedWalletId, amount, notes, selectedDate, selectedCategoryId);
            db.transactionDao().update(transaction.getTransId(), transaction.getAmount(), transaction.getNote(), transaction.getDate(), transaction.getCateId());

            // Hiển thị thông báo thành công
            runOnUiThread(() -> {
                Toast.makeText(this, "Cập nhật giao dịch thành công!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    private void deleteTransaction() {
        db.runInTransaction(() -> {
            db.transactionDao().deleteById(transactionId); // Xóa giao dịch theo ID
            runOnUiThread(() -> {
                Toast.makeText(this, "Giao dịch đã được xóa!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
