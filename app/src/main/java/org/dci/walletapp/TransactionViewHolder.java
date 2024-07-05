package org.dci.walletapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionViewHolder extends RecyclerView.ViewHolder {

    private final TextView amountTextView;
    private final TextView dateTimeTextView;
    private final TextView descriptionTextView;
    private final TextView categoryTextView;
    private final TextView incomeTextView;

    private final Button editTransactionButton;
    private final Button deleteTransactionButton;
    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);
        this.amountTextView = itemView.findViewById(R.id.amountTextView);
        this.dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);;
        this.descriptionTextView = itemView.findViewById(R.id.descriptionTextView);;
        this.categoryTextView = itemView.findViewById(R.id.categoryTextView);;
        this.incomeTextView = itemView.findViewById(R.id.incomeTextView);;
        this.editTransactionButton = itemView.findViewById(R.id.editTransactionButton);;
        this.deleteTransactionButton = itemView.findViewById(R.id.deleteTransactionButton);;
    }

    public TextView getAmountTextView() {
        return amountTextView;
    }

    public TextView getDateTimeTextView() {
        return dateTimeTextView;
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
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
