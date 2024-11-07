package com.example.holajicap;

import android.app.Activity;
import android.app.DatePickerDialog;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holajicap.dao.CategoryDao;
import com.example.holajicap.db.HolaJicapDatabase;
import com.example.holajicap.model.Transaction;
import com.example.holajicap.model.Category;

import java.text.DecimalFormat;
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

        ImageView back_icon = findViewById(R.id.left_icon);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        back_icon.setOnClickListener(view -> finish());
        toolbar_title.setText("Chỉnh sửa hoặc xoá giao dịch");

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        transactionId = intent.getIntExtra("transactionId", -1); // ID giao dịch
        if (transactionId == -1) {
            Toast.makeText(this, "Giao dịch không hợp lệ", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

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

        // Tải dữ liệu giao dịch
        loadTransactionData();

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

        // Thiết lập sự kiện cho chọn nhóm và chọn phương thức giao dịch
        tvChooseTransactionType.setOnClickListener(view -> {
            // Mở Activity chọn nhóm
            Intent intent1 = new Intent(EditTransactionActivity.this, ChooseTransactionTypeActivity.class);
            intent1.putExtra("selectedCategoryId", selectedCategoryId);
            startActivityForResult(intent1, 1);
        });

        tvChooseWallet.setOnClickListener(view -> {
            // Mở Activity chọn ví
            Intent intent1 = new Intent(EditTransactionActivity.this, ChooseWalletActivity.class);
            intent1.putExtra("selectedWalletId", selectedWalletId);
            startActivityForResult(intent1, 2);
        });
    }

    private void loadTransactionData() {
        // Tải thông tin giao dịch từ cơ sở dữ liệu
        Transaction transaction = db.transactionDao().getTransactionById(transactionId);
        if (transaction != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            editTextAmount.setText(decimalFormat.format(transaction.getAmount()));
            editTextNotes.setText(transaction.getNote());
            dateTextView.setText(transaction.getDate());

            // Lấy và hiển thị thông tin ví
            selectedWalletId = transaction.getWalletId();
            if (selectedWalletId != -1) {
                String walletName = db.walletDao().getWalletById(selectedWalletId).getWalletName(); // Lấy tên ví
                tvChooseWallet.setText(walletName); // Hiển thị tên ví
            }

            // Lấy và hiển thị thông tin danh mục
            selectedCategoryId = transaction.getCateId();
            if (selectedCategoryId != -1) {
                Category category = db.categoryDao().getCategoryById(selectedCategoryId); // Lấy danh mục
                if (category != null) {
                    tvChooseTransactionType.setText(category.getCateName()); // Hiển thị tên danh mục
                    // Giả sử cateIcon là một đường dẫn đến tài nguyên drawable
                    int iconResId = getResources().getIdentifier(category.getCateIcon(), "drawable", getPackageName());
                    imv_category_ava.setImageResource(iconResId); // Hiển thị biểu tượng danh mục
                }
            }
        } else {
            Toast.makeText(this, "Không tìm thấy giao dịch", Toast.LENGTH_SHORT).show();
            finish();
        }
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
        String amountText = editTextAmount.getText().toString().replace(",", ""); // Xóa dấu phẩy
        if (TextUtils.isEmpty(amountText)) {
            Toast.makeText(this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
            return;
        }
        double amount = Double.parseDouble(amountText);
        String notes = editTextNotes.getText().toString();

        // Kiểm tra thông tin ví và loại giao dịch
        String walletType = tvChooseWallet.getText().toString();
        String transactionType = tvChooseTransactionType.getText().toString();

        // Lấy thông tin giao dịch cũ
        Transaction oldTransaction = db.transactionDao().getTransactionById(transactionId);
        if (oldTransaction == null) {
            Toast.makeText(this, "Giao dịch không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        // Nếu người dùng chưa chọn ngày mới, giữ nguyên ngày cũ
        if (TextUtils.isEmpty(selectedDate)) {
            selectedDate = oldTransaction.getDate(); // Giữ nguyên ngày cũ
        }

        // Nếu loại giao dịch chưa được chọn, lấy từ giao dịch cũ
        if (TextUtils.isEmpty(transactionType)) {
            selectedCategoryId = oldTransaction.getCateId();
            Category category = db.categoryDao().getCategoryById(selectedCategoryId);
            if (category != null) {
                transactionType = category.getCateType();
                tvChooseTransactionType.setText(category.getCateName());
                int iconResId = getResources().getIdentifier(category.getCateIcon(), "drawable", getPackageName());
                imv_category_ava.setImageResource(iconResId);
            }
        }

        // Nếu ví chưa được chọn, lấy từ giao dịch cũ
        if (selectedWalletId == -1) {
            selectedWalletId = oldTransaction.getWalletId();
            String walletName = db.walletDao().getWalletById(selectedWalletId).getWalletName();
            tvChooseWallet.setText(walletName);
        }



        // Cập nhật giao dịch trong cơ sở dữ liệu
        db.runInTransaction(() -> {
            Transaction transaction = new Transaction(transactionId, 0, selectedWalletId, amount, notes, selectedDate, selectedCategoryId);
            db.transactionDao().update(transaction.getTransId(), transaction.getAmount(), transaction.getNote(), transaction.getDate(), transaction.getCateId());

            // Cập nhật số dư ví tương ứng
            double oldAmount = oldTransaction.getAmount();
            String oldCategoryType = db.categoryDao().getCategoryById(oldTransaction.getCateId()).getCateType();

            // Điều chỉnh số dư ví cho giao dịch cũ
            if (oldCategoryType.equals("Expenditure")) {
                db.walletDao().updateWalletBalance(selectedWalletId, oldAmount); // Hoàn lại số tiền
            } else if (oldCategoryType.equals("Revenue")) {
                db.walletDao().updateWalletBalance(selectedWalletId, -oldAmount); // Hoàn lại số tiền
            }

            // Cập nhật số dư ví với giao dịch mới
            String newCategoryType = db.categoryDao().getCategoryById(selectedCategoryId).getCateType();
            if (newCategoryType.equals("Expenditure")) {
                db.walletDao().updateWalletBalance(selectedWalletId, -amount); // Trừ số tiền mới
            } else if (newCategoryType.equals("Revenue")) {
                db.walletDao().updateWalletBalance(selectedWalletId, amount); // Cộng số tiền mới
            }

            // Hiển thị thông báo thành công
            runOnUiThread(() -> {
                Toast.makeText(this, "Cập nhật giao dịch thành công!", Toast.LENGTH_SHORT).show();

                // Khởi động lại ViewTransactionActivity
                Intent intent = new Intent(EditTransactionActivity.this, ViewTransactionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Đảm bảo rằng Activity trước đó sẽ được khởi động lại.
                startActivity(intent);
                finish(); // Kết thúc EditTransactionActivity
            });
        });
    }

    private void deleteTransaction() {
        db.runInTransaction(() -> {
            // Lấy thông tin giao dịch cần xóa
            Transaction transactionToDelete = db.transactionDao().getTransactionById(transactionId);

            if (transactionToDelete != null) {
                // Lấy ví và số tiền từ giao dịch
                int selectedWalletId = transactionToDelete.getWalletId();
                double amount = transactionToDelete.getAmount();
                CategoryDao categoryDao = db.categoryDao();
                String selectedCategoryType = categoryDao.getCategoryById(transactionToDelete.getCateId()).getCateType();

                // Điều chỉnh số dư ví
                if (selectedCategoryType != null) {
                    if (selectedCategoryType.equals("Expenditure")) {
                        // Trừ số tiền cho ví nếu là chi tiêu
                        db.walletDao().updateWalletBalance(selectedWalletId, amount);
                    } else if (selectedCategoryType.equals("Revenue")) {
                        // Cộng số tiền cho ví nếu là thu nhập
                        db.walletDao().updateWalletBalance(selectedWalletId, -amount);
                    }
                }

                // Xóa giao dịch
                db.transactionDao().deleteById(transactionId);

                // Thông báo và quay lại màn hình trước
                runOnUiThread(() -> {
                    Toast.makeText(this, "Giao dịch đã được xóa!", Toast.LENGTH_SHORT).show();
                    // Gửi Intent để yêu cầu ViewTransactionActivity cập nhật dữ liệu
                    Intent intent = new Intent(EditTransactionActivity.this, ViewTransactionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Đảm bảo rằng Activity trước đó sẽ được khởi động lại.
                    startActivity(intent);
                    finish();
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 1) { // Chọn nhóm
                selectedCategoryId = data.getIntExtra("selectedCategoryId", -1);
                Category category = db.categoryDao().getCategoryById(selectedCategoryId); // Lấy danh mục
                if (category != null) {
                    tvChooseTransactionType.setText(category.getCateName()); // Hiển thị tên danh mục
                    int iconResId = getResources().getIdentifier(category.getCateIcon(), "drawable", getPackageName());
                    imv_category_ava.setImageResource(iconResId); // Hiển thị biểu tượng danh mục
                }
            } else if (requestCode == 2) { // Chọn ví
                selectedWalletId = data.getIntExtra("selectedWalletId", -1);
                String walletName = db.walletDao().getWalletById(selectedWalletId).getWalletName(); // Giả sử có phương thức này
                tvChooseWallet.setText(walletName);
            }
        }
    }
}