package org.dci.walletapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExpenseActivity extends AppCompatActivity {
    private TextView titleTextView;
    private EditText amountEditText;
    private Spinner categoriesSpinner;
    private EditText dateEditText;
    private EditText timeEditText;

    private EditText descriptionEditText;
    private Button saveButton;
    private Button cancelButton;
    private Button goBackButton;
    private Calendar calendar;
    private String selectedCategory;
    private double amount;
    private String description;
    private boolean isDateSelected;
    private LocalDateTime dateTime;
    private List<Transaction> transactionList;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    String currentDate = simpleDateFormat.format(Calendar.getInstance().getTime());
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");
    String currentTime = simpleTimeFormat.format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setupFieldsIds();
        titleTextView.setText(R.string.new_expense);
        calendar = Calendar.getInstance();
        isDateSelected = false;
        setupCategoriesSpinner(false);

        dateEditText.setOnClickListener(view -> showDatePicker());
        dateEditText.setText(currentDate);
        timeEditText.setOnClickListener(view -> showTimePicker());
        timeEditText.setText(currentTime);

        goBackButton.setOnClickListener(view -> finish());

        cancelButton.setOnClickListener(view -> finish());

        saveButton.setOnClickListener(view -> {

            if (validateForm()) {
                amount = Math.round(amount * 100.0) / 100.0;
                amountEditText.setText(String.format(Locale.getDefault(), "%.2f €", Math.round(amount * 100.0) / 100.0));

                boolean transactionFileExists = JsonFilesOperations.getInstance()
                        .fileExists(this, "transaction.json");

                if (transactionFileExists) {
                    transactionList = JsonFilesOperations.getInstance().readTransactions(this);
                } else {
                    transactionList = new ArrayList<>();
                }

                saveTransaction(transactionList, false);
                isTransactionEditable(false);
                Toast.makeText(this, "New expense saved.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupFieldsIds() {
        titleTextView = findViewById(R.id.titleTextView);
        amountEditText = findViewById(R.id.amountEditText);
        categoriesSpinner = findViewById(R.id.categoriesSpinner);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        goBackButton = findViewById(R.id.backButton);
    }

    private void isTransactionEditable(boolean isEditable) {
        updateButtonVisibility(isEditable);
        handleEditableFields(isEditable);
    }

    private void saveTransaction(List<Transaction> transactionList, boolean isIncome) {
        JsonFilesOperations.getInstance().writeTransactions(this, transactionList);

        Transaction newIncome = new Transaction(
                amount,
                dateTime,
                description,
                isIncome, selectedCategory);

        transactionList.add(newIncome);

        JsonFilesOperations.getInstance().writeTransactions(this, transactionList);
    }

    private void handleEditableFields(boolean isEditable) {
        amountEditText.setFocusable(isEditable);
        amountEditText.setClickable(isEditable);
        amountEditText.setFocusableInTouchMode(isEditable);
        amountEditText.setLongClickable(isEditable);
        descriptionEditText.setFocusable(isEditable);
        descriptionEditText.setClickable(isEditable);
        descriptionEditText.setFocusableInTouchMode(isEditable);
        descriptionEditText.setLongClickable(isEditable);
        dateEditText.setFocusable(isEditable);
        dateEditText.setClickable(isEditable);
        dateEditText.setFocusableInTouchMode(isEditable);
        dateEditText.setLongClickable(isEditable);
        categoriesSpinner.setEnabled(isEditable);
    }

    private void setupCategoriesSpinner(boolean isIncome) {
        List<String> spinnerCategories = JsonFilesOperations.getInstance().readCategoriesJSON(this, isIncome);
        if (isIncome) {
            spinnerCategories.add(0, "Select a source");
        } else {
            spinnerCategories.add(0, "Select a category");
        }

        setupSpinnerAdapter(spinnerCategories);

        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    selectedCategory = categoriesSpinner.getSelectedItem().toString();
                } else {
                    selectedCategory = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSpinnerAdapter(List<String> spinnerCategories) {
        ArrayAdapter<String> spinnerCategoriesAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerCategories);
        categoriesSpinner.setAdapter(spinnerCategoriesAdapter);
    }


    private boolean validateForm() {
        boolean isValid = true;

        String amountString = amountEditText.getText().toString().trim();
        if (!validateAmount(amountString)) {
            isValid = false;
        } else {
            amount = Double.parseDouble(amountString);
        }

        if (!validateSpinnerCategory(selectedCategory, true)) {
            isValid = false;
        }

        if (!isValidDate()) {
            isValid = true;
        }

        dateTime = getDateFromPicker();
        description = descriptionEditText.getText().toString().trim();

        return isValid;
    }

    private boolean validateAmount(String amountString) {
        if (amountString.isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            double amount = Double.parseDouble(amountString);
            if (amount <= 0.0) {
                Toast.makeText(this, "Amount must be greater than zero", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateSpinnerCategory(String selectedCategory, boolean isIncome) {
        if (isIncome) {
            if (selectedCategory == null || selectedCategory.isEmpty() || selectedCategory.equals("Select Income Source")) {
                Toast.makeText(this, "Please select a source", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            if (selectedCategory == null || selectedCategory.isEmpty() || selectedCategory.equals("Select Expense Category")) {
                Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    private boolean isValidDate() {
//        if (!isDateSelected) {
//            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
//        }
        return isDateSelected;
    }

    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    updateDateEditText();
                    isDateSelected = true;
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker(){

        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    calendar.set(hourOfDay, minute);
                    updateTimeEditText();
                    isDateSelected = true;
                }, hh, mm, true);

        timePickerDialog.show();
    }

    private void updateDateEditText() {
        String dateFormat ="dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        dateEditText.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void updateTimeEditText() {
        String timeFormat ="HH:mm";
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timeFormat, Locale.ENGLISH);
        timeEditText.setText(simpleTimeFormat.format(calendar.getTime()));
    }

    private LocalDateTime getDateFromPicker() {
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }

    private void updateButtonVisibility(boolean isIncomeEditable) {
        int goBackVisibility = isIncomeEditable ? View.INVISIBLE : View.VISIBLE;
        int editModeVisibility = isIncomeEditable ? View.VISIBLE : View.INVISIBLE;

        goBackButton.setVisibility(goBackVisibility);
        saveButton.setVisibility(editModeVisibility);
        cancelButton.setVisibility(editModeVisibility);
    }
}