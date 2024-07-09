package org.dci.walletapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class EditTransactionActivity extends AppCompatActivity {
    private Spinner inOrOutCategory;
    private EditText amountEditText;
    private Spinner categorySpinner;
    private EditText dateEditText;
    private EditText descriptionEditText;
    private Button saveButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int transactionIndex = getIntent().getIntExtra("position", 1);
        Transaction currentTransaction = Transaction.getTransactionsList(this).get(transactionIndex);
        inOrOutCategory = findViewById(R.id.sourceSpinner);
        amountEditText = findViewById(R.id.amountEditText);
        categorySpinner = findViewById(R.id.category);
        dateEditText = findViewById(R.id.dateEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        String[] spinnerOptions = {"Income", "Expense"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inOrOutCategory.setAdapter(adapter);
        inOrOutCategory.setSelection(currentTransaction.isIncome() ? 0 : 1);

        amountEditText.setHint(currentTransaction.getAmount() + " €");

        List<String> categories;
        if (inOrOutCategory.getSelectedItem().equals("Income")) {
            categories = MainActivity.getIncomesCategorieslist();
        } else {
            categories = MainActivity.getExpensesCategorieslist();
        }
        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, categories);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        categorySpinner.setSelection(categories.indexOf(currentTransaction.getCategory()));
    }
}