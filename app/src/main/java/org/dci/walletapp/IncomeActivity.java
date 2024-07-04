package org.dci.walletapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class IncomeActivity extends AppCompatActivity {

    private TextView titleTextView;
    private EditText amountEditText;
    private Spinner sourceSpinner;
    private EditText dateEditText;

    private EditText descriptionEditText;
    private Button saveButton;
    private Button cancelButton;
    private Button goBackButton;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_income);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleTextView = findViewById(R.id.titleTextView);
        amountEditText = findViewById(R.id.amountEditText);
        sourceSpinner = findViewById(R.id.sourceSpinner);
        dateEditText = findViewById(R.id.dateEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        goBackButton = findViewById(R.id.backButton);
        calendar = Calendar.getInstance();

        goBackButton.setOnClickListener(view -> {
            updateButtonVisibility(true);
            finish();
        });

        cancelButton.setOnClickListener(view -> finish());

        saveButton.setOnClickListener(view -> {
                // TODO: save income
                updateButtonVisibility(false);

        });

        dateEditText.setOnClickListener(view -> showDatePicker() );



    }

    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(Calendar.YEAR, selectedYear);
                    calendar.set(Calendar.MONTH, selectedMonth);
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    updateDateEditText();
                }, year, month, day);
        datePickerDialog.show();
    }

    private void updateDateEditText() {
        String format = "MM/dd/yyyy"; // You can use any format you prefer
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format, java.util.Locale.US);
        dateEditText.setText(sdf.format(calendar.getTime()));
    }

    private void updateButtonVisibility(boolean isProfileEditable) {
        int goBackVisibility = isProfileEditable ? View.INVISIBLE : View.VISIBLE;
        int editModeVisibility = isProfileEditable ? View.VISIBLE : View.INVISIBLE;

        goBackButton.setVisibility(goBackVisibility);
        saveButton.setVisibility(editModeVisibility);
        cancelButton.setVisibility(editModeVisibility);
    }
}