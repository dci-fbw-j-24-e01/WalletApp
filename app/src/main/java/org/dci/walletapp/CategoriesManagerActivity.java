package org.dci.walletapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
    private static List<String> incomesCategories;
    private static List<String> expensesCategories;

    public static List<String> getExpensesCategories() {
        return expensesCategories;
    }

    public static List<String> getIncomesCategories() {
        return incomesCategories;
    }

    TabLayout tabLayout;
    RecyclerView categoriesList;
    EditText inputCategory;
    ImageView addCategoryImage;

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

        JsonFilesOperations filesOperations = JsonFilesOperations.getInstance();
        filesOperations.readCategories(this, true);
        filesOperations.readCategories(this, false);

        tabLayout = findViewById(R.id.tabLayout);
        categoriesList = findViewById(R.id.categoriesList);
        addCategoryImage = findViewById(R.id.addCategoryImage);
        inputCategory = findViewById(R.id.inputCategory);


        categoriesList.setAdapter(new CategoriesListAdapter(this, expensesCategories));

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
        setRecyclerViewAdapter();


    }

    public void setRecyclerViewAdapter() {
        if (tabLayout.getSelectedTabPosition() == 0) {
            categoriesList.setAdapter(new CategoriesListAdapter(this, expensesCategories));
        } else {
            categoriesList.setAdapter(new CategoriesListAdapter(this, incomesCategories));
        }

    }
}
