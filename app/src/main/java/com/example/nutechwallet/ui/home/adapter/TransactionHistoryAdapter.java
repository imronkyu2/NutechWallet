package com.example.nutechwallet.ui.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutechwallet.R;
import com.example.nutechwallet.databinding.AdapterTransactionHistoryBinding;
import com.example.nutechwallet.model.history.TransactionHistoryResponse;
import com.example.nutechwallet.util.Const;

import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {
    private final List<TransactionHistoryResponse> itemsList;
    private final Context context;

    public TransactionHistoryAdapter(Context context, List<TransactionHistoryResponse> itemsList) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AdapterTransactionHistoryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TransactionHistoryResponse data = itemsList.get(position);
        if (data.getTransactionType().equals(Const.Transaction.TOPUP)){
            holder.viewBinding.tvSubtitle.setText("TOPUP");
            holder.viewBinding.tvSubtitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_marking_topup, 0, 0, 0);
            holder.viewBinding.imageStatus.setImageResource(R.drawable.ic_grow_up);
            holder.viewBinding.tvTotal.setTextColor(context.getColor(R.color.green_60CF97));
        }else {
            holder.viewBinding.tvSubtitle.setText("TRANSFER");
            holder.viewBinding.tvSubtitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_marking_transfer, 0, 0, 0);
            holder.viewBinding.imageStatus.setImageResource(R.drawable.ic_grow_down);
            holder.viewBinding.tvTotal.setTextColor(context.getColor(R.color.orange_FB6365));
        }

        holder.viewBinding.tvDate.setText(data.getTransactionTime());
        holder.viewBinding.tvTotal.setText(Const.formatRupiah((double) data.getAmount()));
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterTransactionHistoryBinding viewBinding;

        public ViewHolder(@NonNull AdapterTransactionHistoryBinding itemView) {
            super(itemView.getRoot());
            this.viewBinding = itemView;
        }
    }
}