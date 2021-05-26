package com.applycreditcard.expense_manager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    Button button;
    ListView simpleList;
    String categoryList[] = {"Clothing", "Food", "Fuel", "Health", "Recharge", "Electricity Bill"};
    int flags[] = {R.drawable.ic_clothing, R.drawable.ic_food, R.drawable.ic_gas_pump, R.drawable.ic_health, R.drawable.ic_mobile_recharge, R.drawable.ic_lightning};
    private LinearLayout bottomSheetLL;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.addTransaction);
        bottomSheetLL = findViewById(R.id.idLLBottomSheet);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayBottomSheet();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void displayBottomSheet() {
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
        View layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_bottom_sheet, bottomSheetLL);
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
                final Dialog dialog = new Dialog(context);
                View layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.category_alert_dialogbox, null);
                dialog.setContentView(layout);
                simpleList = layout.findViewById(R.id.simpleListView);
                CustomeAdapter customeAdapter = new CustomeAdapter(getApplicationContext(), categoryList, flags);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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