package com.applycreditcard.expense_manager.ui.home;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applycreditcard.expense_manager.CustomeAdapter;
import com.applycreditcard.expense_manager.HomeScreenListAdapter;
import com.applycreditcard.expense_manager.HomeScreenListData;
import com.applycreditcard.expense_manager.MainActivity;
import com.applycreditcard.expense_manager.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;

public class HomeFragment extends Fragment {
//    final Context context = this;
    // Button button;
    ListView simpleList;
    String categoryList[] = {"Clothing", "Food", "Fuel", "Health", "Recharge", "Electricity Bill","Salary"};
    int flags[] = {R.drawable.ic_clothing, R.drawable.ic_food, R.drawable.ic_gas_pump, R.drawable.ic_health, R.drawable.ic_mobile_recharge, R.drawable.ic_lightning, R.drawable.ic_wallet};
    private LinearLayout bottomSheetLL;

    HomeScreenListData[] homeScreenListData = new HomeScreenListData[]{
     new HomeScreenListData(R.drawable.ic_clothing,"Clothing","500"),
            new HomeScreenListData(R.drawable.ic_clothing,"Clothing","500"),
            new HomeScreenListData(R.drawable.ic_clothing,"Clothing","500"),
            new HomeScreenListData(R.drawable.ic_clothing,"Clothing","500"),
            new HomeScreenListData(R.drawable.ic_clothing,"Clothing","500"),
            new HomeScreenListData(R.drawable.ic_clothing,"Clothing","500"),
            new HomeScreenListData(R.drawable.ic_clothing,"Clothing","500"),
            new HomeScreenListData(R.drawable.ic_clothing,"Clothing","500"),
            new HomeScreenListData(R.drawable.ic_clothing,"Clothing","500"),
            new HomeScreenListData(R.drawable.ic_clothing,"Clothing","500"),
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button button  = root.findViewById(R.id.addTransaction);
                button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayBottomSheet();
            }
        });
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        HomeScreenListAdapter homeScreenListAdapter = new HomeScreenListAdapter(homeScreenListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(homeScreenListAdapter);
        return root;
    }

    private void displayBottomSheet() {
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet, bottomSheetLL);
        bottomSheetTeachersDialog.setContentView(layout);
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        bottomSheetTeachersDialog.show();
        TextView dateTV = layout.findViewById(R.id.idTVDate);
        EditText amountEdt = layout.findViewById(R.id.idEdtAmount);
        TextView categoryTV = layout.findViewById(R.id.idTVCategory);
        Button cancelBtn = layout.findViewById(R.id.idBtnCancel);
        Button addBtn = layout.findViewById(R.id.idBtnAdd);

        categoryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                View layout = LayoutInflater.from(getContext()).inflate(R.layout.category_alert_dialogbox, null);
                dialog.setContentView(layout);
                simpleList = layout.findViewById(R.id.simpleListView);
                CustomeAdapter customeAdapter = new CustomeAdapter(getContext(), categoryList, flags);
                simpleList.setAdapter(customeAdapter);

                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        categoryTV.setText(categoryList[position]);
                        categoryTV.setCompoundDrawables(getResources().getDrawable(flags[position]), null, null, null);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetTeachersDialog.dismiss();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCalender = Calendar.getInstance();
                int year = mCalender.get(Calendar.YEAR);
                int month = mCalender.get(Calendar.MONTH);
                int dayOfMonth = mCalender.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateTV.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();


            }
        });


    }

}