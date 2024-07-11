package org.dci.walletapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionViewHolder> {

    public interface TransactionDeleteHandler {
        void onDelete(Transaction transaction);
    }

    private final List<Transaction> transactionList;
    private final TransactionHistoryActivity activity;
    private boolean isSwitchChecked;
    private final TransactionDeleteHandler transactionDeleteHandler;

    public TransactionAdapter(TransactionHistoryActivity activity, List<Transaction> transactionList, boolean isSwitchChecked, TransactionDeleteHandler transactionDeleteHandler) {
        this.activity = activity;
        this.transactionList = transactionList;
        this.isSwitchChecked = isSwitchChecked;
        this.transactionDeleteHandler = transactionDeleteHandler;
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

        String formattedBalance = getAmountInCurrency(transactionList.get(position).getAmount());
        holder.getAmountTextView().setText(formattedBalance);

        if (transactionList.get(position).isIncome()) {
            holder.getAmountTextView().setTextColor(holder.itemView.getResources().getColor(R.color.dark_olive_green));
        } else {
            holder.getAmountTextView().setTextColor(holder.itemView.getResources().getColor(R.color.maroon));
        }

        String date = String.valueOf(transactionList.get(position).formatDateTime());
        holder.getDateTimeTextView().setText(date);
        holder.getCategoryTextView().setText(transactionList.get(position).getCategory());
        holder.getIncomeTextView().setText(String.valueOf(transactionList.get(position).isIncome()));
        updateEditOrDeleteButtonVisibility(holder);

        holder.getDeleteTransactionButton().setOnClickListener(view -> new AlertDialog.Builder(activity)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this transaction?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    deleteTransaction(position);
                })
                .setNegativeButton(android.R.string.no, null)
                .show());

        holder.getEditTransactionButton().setOnClickListener(view -> {

            Intent intent = new Intent(activity, EditTransactionDummyActivity.class);
            intent.putExtra("ManageTransaction", "Edit transaction Screen!");
            activity.startActivityForResult(intent, 1);

        });
    }

    private String getAmountInCurrency(double amount) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("de", "DE"));
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format(amount);
    }

    private void updateEditOrDeleteButtonVisibility(TransactionViewHolder holder) {
        if (isSwitchChecked) {
            holder.getEditTransactionButton().setVisibility(View.VISIBLE);
            holder.getDeleteTransactionButton().setVisibility(View.VISIBLE);
        } else {
            holder.getEditTransactionButton().setVisibility(View.INVISIBLE);
            holder.getDeleteTransactionButton().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    private void deleteTransaction(int position) {
        Transaction transaction = transactionList.get(position);
        activity.setAmountTextView();
        transactionDeleteHandler.onDelete(transaction);
    }
}