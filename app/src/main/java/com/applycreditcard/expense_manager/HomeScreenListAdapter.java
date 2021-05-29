package com.applycreditcard.expense_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeScreenListAdapter extends RecyclerView.Adapter<HomeScreenListAdapter.ViewHolder> {
    private HomeScreenListData[] listData;

    public HomeScreenListAdapter(HomeScreenListData[] listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public HomeScreenListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.homescreen_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeScreenListAdapter.ViewHolder holder, int position) {
        final HomeScreenListData homeScreenListData = listData[position];
        holder.imageView.setImageResource(listData[position].getImage());
        holder.textView.setText(listData[position].getTitle());
        holder.textView2.setText(listData[position].getAmount());

    }

    @Override
    public int getItemCount() {
        return listData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.expenseIcon);
            this.textView = (TextView) itemView.findViewById(R.id.expenseitemName);
            this.textView2 = (TextView) itemView.findViewById(R.id.expenseItemAmount);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
