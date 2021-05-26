package com.applycreditcard.expense_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class CustomeAdapter extends BaseAdapter {
    Context context;
    String categoryList[];
    int categoryImg [];

    public CustomeAdapter(Context context, String[] categoryList, int[] categoryImg) {
        this.context = context;
        this.categoryList = categoryList;
        this.categoryImg = categoryImg;
    }



    @Override
    public int getCount() {
        return categoryList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, null);
        TextView categoryName = (TextView) convertView.findViewById(R.id.idTVCategory);
        ImageView categoryIcon = (ImageView) convertView.findViewById(R.id.categoryIcon);
        categoryName.setText(categoryList[position]);
        categoryIcon.setImageResource(categoryImg[position]);
        return convertView;
    }
}
