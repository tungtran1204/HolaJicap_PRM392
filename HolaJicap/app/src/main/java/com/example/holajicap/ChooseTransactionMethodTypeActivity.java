package com.example.holajicap;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.adapter.TransactionMethodTypeAdapter;
import com.example.holajicap.model.TransactionMethodType;

import java.util.ArrayList;
import java.util.List;

public class ChooseTransactionMethodTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_transaction_method_type);
        // Initialize toolbar
        ImageView back_icon = findViewById(R.id.left_icon);
        TextView title = findViewById(R.id.toolbar_title);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When click on back_icon, back to MainActivity
                finish();
            }
        });
        title.setText("Chọn ví");

        TransactionMethodType t1 = new TransactionMethodType("Tiền mặt");
        TransactionMethodType t2 = new TransactionMethodType("Chuyển khoản");
        TransactionMethodType t3 = new TransactionMethodType("Phương thức khác");

        List<TransactionMethodType> transactionMethodTypes = new ArrayList<>();
        transactionMethodTypes.add(t1);
        transactionMethodTypes.add(t2);
        transactionMethodTypes.add(t3);

        RecyclerView rec = findViewById(R.id.rec_transactionMethodTypes);
        TransactionMethodTypeAdapter adapter = new TransactionMethodTypeAdapter(transactionMethodTypes, this);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setAdapter(adapter);
    }
}