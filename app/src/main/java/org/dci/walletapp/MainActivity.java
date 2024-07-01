package org.dci.walletapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button addIncomeButton;
    private Button addExpenseButton;
    private Button historyButton;
    private Button profileButton;
    private Button categoryManagementButton;
    private Button supportButton;

    private TextView welcomeTextView;
    private TextView currentBalanceText;
    private TextView currentBalance;

    private final String userName = "WalletUser";
    private double currentBalanceEuro = 111.11;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addIncomeButton = findViewById(R.id.addIncomeButton);
        addExpenseButton = findViewById(R.id.addExpenseButton);
        profileButton = findViewById(R.id.profileButton);
        categoryManagementButton = findViewById(R.id.categoryManagementButton);
        supportButton = findViewById(R.id.supportButton);
        historyButton = findViewById(R.id.historyButton);

        welcomeTextView = findViewById(R.id.welcomeText);
        currentBalanceText = findViewById(R.id.currentBalanceText);
        currentBalance = findViewById(R.id.currentBalance);

        welcomeTextView.setText("Welcome back, " + userName +"!");
        currentBalanceText.setText("Your current balance is: ");
        currentBalance.setText(currentBalanceEuro + "€");



        addIncomeButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, DummyActivity.class);
            intent.putExtra("DummyText", "Income Screen!");
            startActivity(intent);


        });

        addExpenseButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, DummyActivity.class);
            intent.putExtra("DummyText", "Expense Screen!");
            startActivity(intent);


        });
        historyButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, DummyActivity.class);
            intent.putExtra("DummyText", "History Screen!");
            startActivity(intent);


        });
        profileButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, DummyActivity.class);
            intent.putExtra("DummyText", "Profile Screen!");
            startActivity(intent);


        });
        categoryManagementButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, DummyActivity.class);
            intent.putExtra("DummyText", "Category Management Screen!");
            startActivity(intent);


        });
        supportButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, DummyActivity.class);
            intent.putExtra("DummyText", "Suport Screen!");
            startActivity(intent);


        });






    }
}