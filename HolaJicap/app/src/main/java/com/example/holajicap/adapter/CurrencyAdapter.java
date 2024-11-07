package com.example.holajicap.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holajicap.R;
import com.example.holajicap.model.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {
    private List<Currency> currencies = new ArrayList<>();
    private OnCurrencyClickListener listener;
    private Context context;

    public CurrencyAdapter(Context context, OnCurrencyClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_currency, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Currency currency = currencies.get(position);
        holder.bind(currency);
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }

    private int getFlagResourceId(String imageName) {
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return resId != 0 ? resId : R.drawable.flag_vietnam;
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFlag;
        private TextView tvName;

        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFlag = itemView.findViewById(R.id.ivFlag);
            tvName = itemView.findViewById(R.id.tvName);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onCurrencyClick(currencies.get(position));
                }
            });
        }

        public void bind(Currency currency) {
            tvName.setText(currency.getCurrency_name());
            ivFlag.setImageResource(getFlagResourceId(currency.getCurrency_imageResId()));
        }
    }

    public interface OnCurrencyClickListener {
        void onCurrencyClick(Currency currency);
    }
}