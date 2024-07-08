package org.dci.walletapp;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.CategoriesListViewHolder> {

    public static class CategoriesListViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        ImageView imageEdit;
        ImageView imageDelete;


        public CategoriesListViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            imageDelete = itemView.findViewById(R.id.imageDelete);
            imageEdit = itemView.findViewById(R.id.imageEdit);
        }
    }

    List<String> categoriesList;
    CategoriesManagerActivity context;

    public CategoriesListAdapter(CategoriesManagerActivity context, List<String> categoriesList) {
        this.categoriesList = categoriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_recyclerview_element, parent, false);

        return new CategoriesListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesListViewHolder holder, int position) {
        holder.categoryTextView.setText(categoriesList.get(position));

        holder.imageDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirm Delete")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        deleteCategory(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, categoriesList.size());
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });

        holder.imageEdit.setOnClickListener(v -> {
            EditText input = new EditText(context);

            input.setHint(categoriesList.get(position));
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle("Edit Category name")
                    .setView(input)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        editCategory(position, input.getText().toString());
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, categoriesList.size());
                    })
                    .setNegativeButton(android.R.string.no, null).show();
            alertDialog.show();

        });

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    private void deleteCategory(int position) {
        categoriesList.remove(position);
        JsonFilesOperations filesOperations = JsonFilesOperations.getInstance();
        filesOperations.writeCategories(context);
        context.setRecyclerViewAdapter();
    }

    private void editCategory(int position, String newValue) {
        categoriesList.set(position, newValue);
        JsonFilesOperations filesOperations = JsonFilesOperations.getInstance();
        filesOperations.writeCategories(context);
        context.setRecyclerViewAdapter();
    }
}
