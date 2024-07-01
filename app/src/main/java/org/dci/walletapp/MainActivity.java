package org.dci.walletapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button addIncomeButton;
    Button addExpenseButton;
    Button historyButton;
    Button profileButton;
    Button categoryManagementButton;
    Button supportButton;





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

        addIncomeButton.findViewById(R.id.addIncomeButton);
        addExpenseButton.findViewById(R.id.addExpenseButton);
        profileButton.findViewById(R.id.profileButton);
        categoryManagementButton.findViewById(R.id.categoryManagementButton);
        supportButton.findViewById(R.id.supportButton);






    }
}