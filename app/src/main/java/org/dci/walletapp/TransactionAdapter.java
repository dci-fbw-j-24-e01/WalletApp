package org.dci.walletapp;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionViewHolder> {

    List<Transaction> transactionList;

    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_transaction_item_view, parent, false);

        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.getAmountTextView().setText(String.valueOf(transactionList.get(position).getAmount()));
        holder.getDateTimeTextView().setText(String.valueOf(transactionList.get(position).getDateTime()));
        holder.getDescriptionTextView().setText(transactionList.get(position).getDescription());
        holder.getCategoryTextView().setText(transactionList.get(position).getCategory());
        holder.getIncomeTextView().setText(String.valueOf(transactionList.get(position).isIncome()));



    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }


}
