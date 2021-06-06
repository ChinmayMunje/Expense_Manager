package com.applycreditcard.expense_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import Model.Data;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
        private List<Data> listData;

        public Adapter(List<Data> listData) {
            this.listData = listData;
        }
        @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Data ld=listData.get(position);
        holder.txtid.setText(ld.getAmount());
        holder.txtname.setText(ld.getCategory());
        //            holder.txtmovie.setText(ld.getMovie());
    }
    public Adapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.homescreen_list_item,parent,false);
        return new ViewHolder(view);

    }
    @Override
        public int getItemCount() {
            return listData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView txtid,txtname,txtmovie;
            public ViewHolder(View itemView) {
                super(itemView);
                txtid=(TextView)itemView.findViewById(R.id.expenseitemName);
                txtname=(TextView)itemView.findViewById(R.id.expenseItemAmount);
//                txtmovie=(TextView)itemView.findViewById(R.id.movietxt);
            }
        }

    }

