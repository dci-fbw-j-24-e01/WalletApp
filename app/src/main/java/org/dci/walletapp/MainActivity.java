package org.dci.walletapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

test();
    }

    private void test() {
        Transaction transaction = new Transaction(155.1, "Hello", true, "Goodbye");
        Transaction transaction1 = new Transaction(546.1, "New Description", false, "Test Source");

        JsonFilesOperations filesOperations = JsonFilesOperations.getInstance();
        filesOperations.writeTransaction(this, transaction);
        filesOperations.writeTransaction(this, transaction1);



//        filesOperations.writeTransaction(this, transaction1);
    }
}