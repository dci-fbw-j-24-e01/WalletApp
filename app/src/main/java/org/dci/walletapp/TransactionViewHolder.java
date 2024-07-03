package org.dci.walletapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionViewHolder extends RecyclerView.ViewHolder {

    private final TextView amount;
    private final TextView dateTime;
    private final TextView category;
    private final TextView description;

    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);
        this.amount = itemView.findViewById(R.id.amount);
        this.dateTime = itemView.findViewById(R.id.dateTime);
        this.category = itemView.findViewById(R.id.category);
        this.description = itemView.findViewById(R.id.description);
    }

    public TextView getAmount() {
        return amount;
    }

    public TextView getDateTime() {
        return dateTime;
    }

    public TextView getCategory() {
        return category;
    }

    public TextView getDescription() {
        return description;
    }
}
