package org.dci.walletapp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionViewHolder> {

    List<Transaction> transactionList;
    private Activity activity;

    private SwitchCompat editOrDeleteSwitch;
    private boolean isSwitchChecked = false;

    public TransactionAdapter(Activity activity, List<Transaction> transactionList, SwitchCompat editOrDeleteSwitch) {
        this.activity = activity;
        this.transactionList = transactionList;
        this.editOrDeleteSwitch = editOrDeleteSwitch;
        setupSwitchListener();
    }

    private void setupSwitchListener() {
        editOrDeleteSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isSwitchChecked = isChecked;
            Log.d("SK", isChecked ? "Checked" : "UnChecked");
            notifyDataSetChanged(); // Notify the adapter to refresh the views
        });
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
        if (transactionList.get(position).isIncome()) {
            holder.getAmountTextView().setText("Amount: " + transactionList.get(position).getAmount());
            holder.getAmountTextView().setTextColor(holder.itemView.getResources().getColor(R.color.dark_olive_green));
        } else {
            holder.getAmountTextView().setText("Amount: " + transactionList.get(position).getAmount());
            holder.getAmountTextView().setTextColor(holder.itemView.getResources().getColor(R.color.maroon));
        }


        holder.getDateTimeTextView().setText("Date Time: " + transactionList.get(position).getDateTime());
        holder.getDescriptionTextView().setText("Description: " + transactionList.get(position).getDescription());
        holder.getCategoryTextView().setText("Category: " + transactionList.get(position).getCategory());
        holder.getIncomeTextView().setText(String.valueOf(transactionList.get(position).isIncome()));
        updateButtonVisibility(holder);


        holder.getEditTransactionButton().setOnClickListener(view -> {

            Intent intent = new Intent(activity, EditTransactionDummyActivity.class);
            intent.putExtra("DummyText", "Edit transaction Screen!");
            activity.startActivityForResult(intent, 1);
        });

        holder.getDeleteTransactionButton().setOnClickListener(view -> {

            Intent intent = new Intent(activity, EditTransactionDummyActivity.class);
            intent.putExtra("DummyText", "Delete transaction Screen!");
            activity.startActivityForResult(intent, 1);
        });

    }

    private void updateButtonVisibility(TransactionViewHolder holder) {
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


    public void updateTransactions(List<Transaction> filteredTransactions) {
        transactionList.clear();
        transactionList.addAll(filteredTransactions);
        notifyDataSetChanged();
    }
}
