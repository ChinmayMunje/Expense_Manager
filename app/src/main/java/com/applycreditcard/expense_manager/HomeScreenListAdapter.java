package com.applycreditcard.expense_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Model.Data;

public class HomeScreenListAdapter extends RecyclerView.Adapter<HomeScreenListAdapter.ViewHolder> {
    private ArrayList<Data> listData;

    public HomeScreenListAdapter(ArrayList<Data> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public HomeScreenListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.homescreen_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeScreenListAdapter.ViewHolder holder, int position) {
        final Data homeScreenListData = listData.get(position);
        holder.imageView.setImageResource(listData.get(position).getImgid());
        holder.textView.setText(listData.get(position).getCategory());
        holder.textView2.setText("₹ " + listData.get(position).getAmount());
        String date = listData.get(position).getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yy");
        String newFormat = formatter.format(testDate);
        holder.dateTV.setText(newFormat);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public RelativeLayout relativeLayout;
        //        private CardView dateCV;
        private TextView dateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //dateCV = itemView.findViewById(R.id.idCVDate);
            dateTV = itemView.findViewById(R.id.idTVDate);
            this.imageView = (ImageView) itemView.findViewById(R.id.expenseIcon);
            this.textView = (TextView) itemView.findViewById(R.id.expenseitemName);
            this.textView2 = (TextView) itemView.findViewById(R.id.expenseItemAmount);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }

}
