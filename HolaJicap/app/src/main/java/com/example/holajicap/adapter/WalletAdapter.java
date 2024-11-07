package com.example.holajicap.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.ChooseTransactionTypeActivity;
import com.example.holajicap.ChooseWalletActivity;
import com.example.holajicap.R;
import com.example.holajicap.model.Category;
import com.example.holajicap.model.Wallet;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletHolder> {
    private List<Wallet> walletList;
    private Activity activity;
    private Context context;

    public WalletAdapter() {
    }

    public WalletAdapter(List<Wallet> walletList) {
        this.walletList = walletList;
    }

    public WalletAdapter(Activity activity) {
        this.activity = activity;
    }

    public WalletAdapter(Context context) {
        this.context = context;
    }

    public WalletAdapter(List<Wallet> walletList, Activity activity) {
        this.walletList = walletList;
        this.activity = activity;
    }

    public WalletAdapter(List<Wallet> walletList, Context context) {
        this.walletList = walletList;
        this.context = context;
    }

    public WalletAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public WalletAdapter(List<Wallet> walletList, Activity activity, Context context) {
        this.walletList = walletList;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public WalletAdapter.WalletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wallet_card, parent, false);
        return new WalletHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.WalletHolder holder, int position) {
        Wallet wallet = walletList.get(position);
        holder.tv_wallet_name.setText(wallet.getWalletName());
        holder.wallet_balance.setText(String.valueOf(wallet.getBalance()));
        holder.wallet_currency.setText(wallet.getCurrency());
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    class WalletHolder extends RecyclerView.ViewHolder {
        TextView tv_wallet_name;
        TextView wallet_balance;
        TextView wallet_currency;

        public WalletHolder(@NonNull View itemView) {
            super(itemView);
            tv_wallet_name = itemView.findViewById(R.id.tv_wallet_name);
            wallet_balance = itemView.findViewById(R.id.wallet_balance);
            wallet_currency = itemView.findViewById(R.id.wallet_currency);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Wallet selectedWallet = walletList.get(pos);

                        // Tạo Intent để chứa dữ liệu của thẻ được chọn
                        Intent intent = new Intent();
                        intent.putExtra("selectedWalletId", selectedWallet.getWalletId()); // Truyền `walletId`
                        intent.putExtra("selectedWallet", selectedWallet.getWalletName()); // truyền tên
                        intent.putExtra("selectedBalance", selectedWallet.getBalance()); // truyền số dư
                        intent.putExtra("selectedCurrency", selectedWallet.getCurrency()); // truyền tiền tệ

                        // Gửi dữ liệu về Activity gọi nó (AddTransactionActivity)
                        ((ChooseWalletActivity) itemView.getContext()).setResult(Activity.RESULT_OK, intent);

                        // Kết thúc Activity hiện tại và quay lại Activity trước
                        ((ChooseWalletActivity) itemView.getContext()).finish();
                    }
                }
            });
        }
    }



}
