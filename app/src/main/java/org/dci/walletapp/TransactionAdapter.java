package org.dci.walletapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionViewHolder> {

    private final List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }


    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TextView amount = holder.getAmount();
        TextView dateTime = holder.getDateTime();
        TextView category = holder.getCategory();
        TextView description = holder.getDescription();

        Transaction transaction = transactions.get(position);

        amount.setText(transactions.get());

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
