package com.example.holajicap.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.ChooseWalletActivity;
import com.example.holajicap.R;
import com.example.holajicap.model.Wallet;

import java.util.List;

public class MyWalletAdapter extends RecyclerView.Adapter<MyWalletAdapter.WalletViewHolder> {
    private List<Wallet> walletList;
    private Context context;

    public MyWalletAdapter(List<Wallet> walletList, Context context) {
        this.walletList = walletList;
        this.context = context;
    }

    public MyWalletAdapter() {
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallet, parent, false);
        return new WalletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        Wallet wallet = walletList.get(position);
        holder.walletName.setText(wallet.getWalletName());
        holder.walletBalance.setText(String.valueOf(wallet.getBalance()));


        // Optional: Set wallet icon based on type, here using a default icon
        holder.walletIcon.setImageResource(R.drawable.wallet_choose);
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public void setData(List<Wallet> newWallets) {
        this.walletList = newWallets;
        notifyDataSetChanged();
    }

    static class WalletViewHolder extends RecyclerView.ViewHolder {
        ImageView walletIcon;
        TextView walletName, walletBalance;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            walletIcon = itemView.findViewById(R.id.imv_wallet_ava);
            walletName = itemView.findViewById(R.id.tv_wallet_name);
            walletBalance = itemView.findViewById(R.id.wallet_balance);

        }
    }
}
