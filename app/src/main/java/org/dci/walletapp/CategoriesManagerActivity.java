package org.dci.walletapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CategoriesManagerActivity extends AppCompatActivity {

    TabLayout tabLayout;
    RecyclerView categoriesList;
    EditText inputCategory;
    ImageView addCategoryImage;
    JsonFilesOperations filesOperations;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categories_manager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        filesOperations = JsonFilesOperations.getInstance();
        filesOperations.readCategories(this, true);
        filesOperations.readCategories(this, false);

        tabLayout = findViewById(R.id.tabLayout);
        categoriesList = findViewById(R.id.categoriesList);
        addCategoryImage = findViewById(R.id.addCategoryImage);
        inputCategory = findViewById(R.id.inputCategory);

        categoriesList.setLayoutManager(new LinearLayoutManager(this));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setRecyclerViewAdapter();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        addCategoryImage.setOnClickListener(v -> addCategory());

        setRecyclerViewAdapter();

    }

    public void setRecyclerViewAdapter() {
        if (tabLayout.getSelectedTabPosition() == 0) {
            categoriesList.setAdapter(new CategoriesListAdapter(this, MainActivity.getExpensesCategorieslist()));
        } else {
            categoriesList.setAdapter(new CategoriesListAdapter(this, MainActivity.getIncomesCategorieslist()));
        }

    }

    private void addCategory() {
        String input = String.valueOf(inputCategory.getText());
        if (TextUtils.isEmpty(input)) {
            inputCategory.setError("Enter the valid category name");
            return;
        }
        if (tabLayout.getSelectedTabPosition() == 0) {
            if (MainActivity.getExpensesCategorieslist().contains(input)) {
                inputCategory.setError("This category already exists");
                return;
            }
            MainActivity.getExpensesCategorieslist().add(String.valueOf(inputCategory.getText()));

        } else {
            if (MainActivity.getIncomesCategorieslist().contains(input)) {
                inputCategory.setError("This category already exists");
                return;
            }
            MainActivity.getIncomesCategorieslist().add(String.valueOf(inputCategory.getText()));
        }
        filesOperations.writeCategories(this);
        setRecyclerViewAdapter();

    }
}
