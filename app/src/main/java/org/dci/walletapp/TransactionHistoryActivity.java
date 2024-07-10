package org.dci.walletapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity implements TransactionAdapter.TransactionDeleteHandler {

    private Spinner spinnerTransactionsCategory;
    private RecyclerView listOfTransactions;
    private SwitchCompat editOrDeleteSwitch;
    private List<Transaction> transactionList;
    private TransactionAdapter transactionAdapter;
    private List<Transaction> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        transactionList = JsonFilesOperations.getInstance().readTransactions(this);


        // TODO: for testing only, needs to be removed later
        // everytime the activity opens without transactions in the JSON storage
        // it will add some transactions for quick testing
        if (transactionList.isEmpty()) {
            testToWriteDataInJSON();
        }


        filteredList = new ArrayList<>();
        filteredList.addAll(transactionList);

        initializeViews();
        setupRecyclerView(false);
        setupSpinner();
        setupSwitchListener();
        lineDividerBetweenTransactions();

    }

    private void setupSwitchListener() {
        editOrDeleteSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setupRecyclerView(isChecked);
        });


    }

    private void lineDividerBetweenTransactions() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listOfTransactions.getContext(),
                LinearLayoutManager.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.custom_divider);
        if (dividerDrawable != null) {
            dividerItemDecoration.setDrawable(dividerDrawable);
        }
        listOfTransactions.addItemDecoration(dividerItemDecoration);
    }

    private void setupSpinner() {

        List<String> transactionCategories = new ArrayList<>();
        transactionCategories.add("All");
        transactionCategories.add("Incomes");
        transactionCategories.add("Expenses");

        ArrayAdapter<String> transactionArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, transactionCategories);
        spinnerTransactionsCategory.setAdapter(transactionArrayAdapter);
        spinnerTransactionsCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fetchTransactionsByCategory(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupRecyclerView(boolean isChecked) {

        transactionAdapter = new TransactionAdapter(this, filteredList, isChecked, this);
        listOfTransactions.setLayoutManager(new LinearLayoutManager(this));
        listOfTransactions.setAdapter(transactionAdapter);
    }

    private void initializeViews() {
        spinnerTransactionsCategory = findViewById(R.id.spinnerTransactionsCategory);
        editOrDeleteSwitch = findViewById(R.id.editOrDeleteSwitch);
        listOfTransactions = findViewById(R.id.listOfTransactions);
    }

    private void fetchTransactionsByCategory(int position) {

        List<Transaction> filteredTransactions;
        switch (position) {
            case 1:
                filteredTransactions = getTransactionsByType(true);
                break;
            case 2:
                filteredTransactions = getTransactionsByType(false);
                break;
            default:
                filteredTransactions = JsonFilesOperations.getInstance().readTransactions(this);
                break;
        }

        this.filteredList = filteredTransactions;
        listOfTransactions.setAdapter(new TransactionAdapter(this, filteredList, editOrDeleteSwitch.isChecked(), this));

        if (filteredTransactions.isEmpty()) {
            Toast.makeText(this, "No Transactions", Toast.LENGTH_LONG).show();
        }
    }

    private List<Transaction> getTransactionsByType(boolean isIncome) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            if (transaction.isIncome() == isIncome) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // Refresh your transactions here...
            spinnerTransactionsCategory.setSelection(0);
            fetchTransactionsByCategory(0);// This method should reload the transaction list
        }
    }


    private void testToWriteDataInJSON() {

        List<Transaction> transactions = new ArrayList<>();
        String dateTimeString = "2024-07-02T15:14:00.639424";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        Transaction transaction = new Transaction(100,
                LocalDateTime.parse(dateTimeString, formatter),
                "Test Description",
                false,
                "Test");
        Transaction transaction1 = new Transaction(200,
                LocalDateTime.parse(dateTimeString, formatter),
                "Test Description",
                false,
                "Test");
        Transaction transaction2 = new Transaction(300,
                LocalDateTime.parse(dateTimeString, formatter),
                "Test Description",
                true,
                "Test");
        Transaction transaction3 = new Transaction(400.5,
                LocalDateTime.parse(dateTimeString, formatter),
                "Test Description",
                false,
                "Test");
        Transaction transaction4 = new Transaction(500,
                LocalDateTime.parse(dateTimeString, formatter),
                "Test Description",
                true,
                "Test");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        JsonFilesOperations filesOperations = JsonFilesOperations.getInstance();
        filesOperations.writeTransactions(this, transactions);
        transactionList = transactions;
    }

    @Override
    public void onDelete(Transaction transaction) {
        transactionList.remove(transaction);
        filteredList.remove(transaction);
        listOfTransactions.setAdapter(new TransactionAdapter(this, filteredList, editOrDeleteSwitch.isChecked(), this));

        JsonFilesOperations.getInstance().writeTransactions(this, transactionList);
    }
}