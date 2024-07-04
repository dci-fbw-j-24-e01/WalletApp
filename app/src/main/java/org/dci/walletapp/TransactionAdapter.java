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


        Transaction transaction = transactions.get(position);

        holder.getAmount().setText(transaction.getFormattedAmount());
        holder.getDateTime().setText(transaction.getDateTime().toString());
        holder.getCategory().setText(transaction.getCategory());
        holder.getDescription().setText(transaction.getDescription());

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
