package org.dci.walletapp;

import static org.dci.walletapp.StaticMethods.setSystemBarAppearance;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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


    private static List<String> incomesCategorieslist;
    private static List<String> expensesCategorieslist;

    public static List<String> getExpensesCategorieslist() {
        return expensesCategorieslist;
    }

    public static List<String> getIncomesCategorieslist() {
        return incomesCategorieslist;
    }


    public static void setExpensesCategorieslist(List<String> expencesCategorieslist) {
        MainActivity.expensesCategorieslist = expencesCategorieslist;
    }

    public static void setIncomesCategorieslist(List<String> incomesCategorieslist) {
        MainActivity.incomesCategorieslist = incomesCategorieslist;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setSystemBarAppearance(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        JsonFilesOperations filesOperations = JsonFilesOperations.getInstance();

        filesOperations.readCategories(this, true);
        filesOperations.readCategories(this, false);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        addIncomeButton = findViewById(R.id.addIncomeButton);
        addExpenseButton = findViewById(R.id.addExpenseButton);
        profileButton = findViewById(R.id.profileButton);
        categoryManagementButton = findViewById(R.id.categoryManagementButton);
        supportButton = findViewById(R.id.supportButton);
        historyButton = findViewById(R.id.historyButton);

        welcomeTextView = findViewById(R.id.welcomeText);
        currentBalanceText = findViewById(R.id.currentBalanceText);
        currentBalance = findViewById(R.id.currentBalance);

        // Load the username from JSON and update the welcome message
        Profile profile = JsonFilesOperations.getInstance().readProfileFromJSON(this, new Profile());
        if (profile != null && profile.getName() != null) {
            welcomeTextView.setText("Welcome back, " + profile.getName() + "!");
        } else {
            welcomeTextView.setText("Welcome to your Wallet!"); // Fallback
        }


        currentBalanceText.setText("You have a balance of:");


        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("de", "DE"));
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        String formattedBalance = numberFormat.format(currentBalanceEuro);

        currentBalance.setText(formattedBalance);

        addIncomeButton.setBackgroundTintList(null);
        addExpenseButton.setBackgroundTintList(null);

        addIncomeButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, IncomeActivity.class);
            startActivity(intent);

        });

        addExpenseButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
            startActivity(intent);

        });
        historyButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, TransactionHistoryActivity.class);
            intent.putExtra("DummyText", "History Screen!");
            startActivity(intent);

        });
        profileButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);

        });
        categoryManagementButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, CategoriesManagerActivity.class);
            startActivity(intent);

        });
        supportButton.setOnClickListener((view) -> {

            Intent intent = new Intent(MainActivity.this, HelpAndSupportActivity.class);
            startActivity(intent);

        });
    }

}