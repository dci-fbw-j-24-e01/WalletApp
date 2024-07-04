package org.dci.walletapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

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
    private double currentBalanceEuro = 16180.33;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      
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
        currentBalanceText.setText("You have a balance of:");


        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("de", "DE"));
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        String formattedBalance = numberFormat.format(currentBalanceEuro);

        currentBalance.setText(formattedBalance);

        addIncomeButton.setBackgroundTintList(null);
        addExpenseButton.setBackgroundTintList(null);

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


        test();
    }

    private void test() {
        JsonFilesOperations filesOperations = JsonFilesOperations.getInstance();

        List<String> incomesCategorieslist = filesOperations.readCategories(this, true);
        List<String> expencesCategorieslist = filesOperations.readCategories(this, false);

        incomesCategorieslist.add("Salary");
        incomesCategorieslist.add("Bonus");
        incomesCategorieslist.add("Others");

        expencesCategorieslist.add("Food");
        expencesCategorieslist.add("Transport");
        expencesCategorieslist.add("Entertainment");
        expencesCategorieslist.add("House");
        expencesCategorieslist.add("Children");
        expencesCategorieslist.add("Others");

        filesOperations.writeCategories(this, incomesCategorieslist, expencesCategorieslist);
    }
}