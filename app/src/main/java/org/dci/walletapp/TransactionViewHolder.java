package org.dci.walletapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionViewHolder extends RecyclerView.ViewHolder {

    private final TextView amountTextView;
    private final TextView dateTimeTextView;
    private final TextView categoryTextView;
    private final TextView incomeTextView;
    private final Button editTransactionButton;
    private final Button deleteTransactionButton;
    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);
        this.amountTextView = itemView.findViewById(R.id.amountTextView);
        this.dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
        this.categoryTextView = itemView.findViewById(R.id.categoryTextView);
        this.incomeTextView = itemView.findViewById(R.id.incomeTextView);
        this.editTransactionButton = itemView.findViewById(R.id.editTransactionButton);
        this.deleteTransactionButton = itemView.findViewById(R.id.deleteTransactionButton);
        deleteTransactionButton.setBackgroundTintList(null);
        editTransactionButton.setBackgroundTintList(null);

        editTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(EditTransactionActivity.class);
            }
        });
    }

    public TextView getAmountTextView() {
        return amountTextView;
    }
    public TextView getDateTimeTextView() {
        return dateTimeTextView;
    }
    public TextView getCategoryTextView() {
        return categoryTextView;
    }
    public TextView getIncomeTextView() {
        return incomeTextView;
    }
    public Button getEditTransactionButton() {
        return editTransactionButton;
    }
    public Button getDeleteTransactionButton() {
        return deleteTransactionButton;
    }
}
