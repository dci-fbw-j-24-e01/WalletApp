package org.dci.walletapp;

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

public class IncomeActivity extends AppCompatActivity {

    private TextView titleTextView;
    private EditText amountEditText;
    private Spinner sourceSpinner;
    private EditText dateEditText;

    private EditText descriptionEditText;
    private Button saveButton;
    private Button cancelButton;
    private Button goBackButton;

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

        goBackButton.setOnClickListener(view -> {
            updateButtonVisibility(true);
            finish();
        });

        cancelButton.setOnClickListener(view -> finish());

        saveButton.setOnClickListener(view -> {
                // TODO: save income
                updateButtonVisibility(false);

        });

    }
    private void updateButtonVisibility(boolean isProfileEditable) {
        int goBackVisibility = isProfileEditable ? View.INVISIBLE : View.VISIBLE;
        int editModeVisibility = isProfileEditable ? View.VISIBLE : View.INVISIBLE;

        goBackButton.setVisibility(goBackVisibility);
        saveButton.setVisibility(editModeVisibility);
        cancelButton.setVisibility(editModeVisibility);
    }
}