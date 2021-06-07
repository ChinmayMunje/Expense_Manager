package com.applycreditcard.expense_manager.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applycreditcard.expense_manager.R;

import java.util.ArrayList;

import Model.CategoryModel;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {


    private ArrayList<CategoryModel> categoryModels;
    private Context context;
    private OnclickInterface onclickInterface;

    public CategoryListAdapter(ArrayList<CategoryModel> categoryModels, Context context, OnclickInterface onclickInterface) {
        this.categoryModels = categoryModels;
        this.context = context;
        this.onclickInterface = onclickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.category_list_item, parent, false);
        CategoryListAdapter.ViewHolder viewHolder = new CategoryListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel modal = categoryModels.get(position);
        holder.textView.setText(modal.getCategoryName());
        holder.imageView.setImageResource(modal.getImageUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickInterface.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.categoryIcon);
            this.textView = (TextView) itemView.findViewById(R.id.idTVCategory);
        }
    }

    public interface OnclickInterface {
        void onClick(int position);
    }

}
