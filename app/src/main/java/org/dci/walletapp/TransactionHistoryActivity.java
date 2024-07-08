package org.dci.walletapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    TextView titleTransactionHistory;
    Spinner spinnerTransactionsCategory;
    RecyclerView listOfTransactions;
    SwitchCompat editOrDeleteSwitch;
    List<Transaction> transactionList;
    TransactionAdapter transactionAdapter;

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

        initializeViews();
        setupRecyclerView();
        setupSpinner();

//        testToWriteDataInJSON();

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

    private void setupRecyclerView() {
        transactionList = JsonFilesOperations.getInstance().readTransactions(this);
        transactionAdapter = new TransactionAdapter(this, transactionList, editOrDeleteSwitch);
        listOfTransactions.setLayoutManager(new LinearLayoutManager(this));
        listOfTransactions.setAdapter(transactionAdapter);
    }

    private void initializeViews() {
        titleTransactionHistory = findViewById(R.id.titleTransactionHistory);
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
        if (!filteredTransactions.isEmpty()) {
            editOrDeleteSwitch.setVisibility(View.VISIBLE);
            transactionAdapter.updateTransactions(filteredTransactions);
            transactionList = JsonFilesOperations.getInstance().readTransactions(this);
        } else {
            editOrDeleteSwitch.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "NoTransactions", Toast.LENGTH_LONG).show();
        }
    }

    private List<Transaction> getTransactionsByType(boolean isIncome) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            if (transaction.isIncome() == isIncome) {
                filteredTransactions.add(transaction);
            }
        }
        Log.d("SK", filteredTransactions.size() + "");
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
        String date = "2024-03-05";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        Transaction transaction = new Transaction(321.1,
                LocalDate.parse(date, formatter),
                "Test Description",
                false,
                "Test");
        Transaction transaction1 = new Transaction(321.1,
                LocalDate.parse(date, formatter),
                "Test Description",
                false,
                "Test");
        Transaction transaction2 = new Transaction(321.1,
                LocalDate.parse(date, formatter),
                "Test Description",
                true,
                "Test");
        Transaction transaction3 = new Transaction(321.1,
                LocalDate.parse(date, formatter),
                "Test Description",
                false,
                "Test");
        Transaction transaction4 = new Transaction(321.1,
                LocalDate.parse(date, formatter),
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
    }
}