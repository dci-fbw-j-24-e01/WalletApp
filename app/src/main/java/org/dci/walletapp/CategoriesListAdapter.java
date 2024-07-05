package org.dci.walletapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategory(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    private void deleteCategory(int position) {
        categoriesList.remove(position);
        JsonFilesOperations filesOperations = JsonFilesOperations.getInstance();
        filesOperations.writeCategories(context, CategoriesManagerActivity.getIncomesCategories(),
                CategoriesManagerActivity.getExpensesCategories());
        context.setRecyclerViewAdapter();
    }
}
