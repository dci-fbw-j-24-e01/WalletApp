package org.dci.walletapp;

import android.content.Intent;
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

public class TransactionHistoryActivity extends AppCompatActivity {

    TextView titleTransactionHistory;

    Spinner spinnerTransactionsCategory;

    RecyclerView listOfTransactions;

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

        titleTransactionHistory = findViewById(R.id.titleTransactionHistory);
        spinnerTransactionsCategory = findViewById(R.id.spinnerTransactionsCategory);
        listOfTransactions = findViewById(R.id.listOfTransactions);


        //write to Transaction.JSON file (Just for development purpose)

//        [{"amount":321.1,"dateTime":"2024-07-02T15:14:00.639424","description":"Test Description",
//                "income":false,"source":"Test"},{"amount":321.1,"dateTime":"2024-07-02T15:14:19.317111",
//                "description":"Test Description","income":false,"source":"Test"}]

//        testToWriteDataInJSON();




        List<String> transactionCategories = new ArrayList<>();

        transactionCategories.add(0, "All");
        transactionCategories.add(1, "Incomes");
        transactionCategories.add(2, "Expenses");

        ArrayAdapter<String> transactionArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, transactionCategories);
        spinnerTransactionsCategory.setAdapter(transactionArrayAdapter);

        List<Transaction> transactionList = JsonFilesOperations.getInstance().readTransactions(this);
        Log.d("SK",transactionList.size()+"");
        TransactionAdapter transactionAdapter = new TransactionAdapter(transactionList);
        listOfTransactions.setLayoutManager(new LinearLayoutManager(this));
        listOfTransactions.setAdapter(transactionAdapter);


//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listOfTransactions.getContext(),
//                new LinearLayoutManager(this).getOrientation());
//        listOfTransactions.addItemDecoration(dividerItemDecoration);


        spinnerTransactionsCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void testToWriteDataInJSON() {
        List<Transaction> transactions = new ArrayList<>();

        String dateTimeString = "2024-07-02T15:14:00.639424";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        Transaction transaction = new Transaction(321.1,
                LocalDateTime.parse(dateTimeString, formatter),
                "Test Description",
                false,
                "Test");
        Transaction transaction1 = new Transaction(321.1,
                LocalDateTime.parse(dateTimeString, formatter),
                "Test Description",
                false,
                "Test");
        Transaction transaction2 = new Transaction(321.1,
                LocalDateTime.parse(dateTimeString, formatter),
                "Test Description",
                true,
                "Test");
        Transaction transaction3 = new Transaction(321.1,
                LocalDateTime.parse(dateTimeString, formatter),
                "Test Description",
                false,
                "Test");
        Transaction transaction4 = new Transaction(321.1,
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
    }
}