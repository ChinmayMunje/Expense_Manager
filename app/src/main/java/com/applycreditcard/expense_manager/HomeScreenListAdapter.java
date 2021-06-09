package com.applycreditcard.expense_manager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applycreditcard.expense_manager.ui.CategoryListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Model.CategoryModel;
import Model.Data;

import static java.security.AccessController.getContext;

public class HomeScreenListAdapter extends RecyclerView.Adapter<HomeScreenListAdapter.ViewHolder> {
    private ArrayList<Data> listData;




    public HomeScreenListAdapter(ArrayList<Data> listData) {
        this.listData = listData;
    }
    private LinearLayout bottomsheetll;
    @NonNull
    @Override
    public HomeScreenListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.homescreen_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(listItem.getContext(), "clicked",Toast.LENGTH_SHORT).show();
                clicked();
            }
            private LinearLayout bootm;
            private ArrayList<CategoryModel> categoryModels;
            private CategoryListAdapter categoryListAdapter;
             private TextView dateTV,catupdate;
             EditText amountEdt;
             ImageView categoryIV;
            private String courseName, courseDuration, courseDescription;
            private  int categoryIVs;
//            ExpenseManager courses = (Courses) getIntent().getSerializableExtra("course");
            private FirebaseFirestore db;
            private void clicked() {
                final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(listItem.getContext(), R.style.BottomSheetDialogTheme);
                View layout = LayoutInflater.from(listItem.getContext()).inflate(R.layout.update,bootm);
                bottomSheetTeachersDialog.setContentView(layout);
                        bottomSheetTeachersDialog.setCancelable(false);
                        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
                        bottomSheetTeachersDialog.show();
                 dateTV = layout.findViewById(R.id.idTVupdateDate);
                    categoryIV = layout.findViewById(R.id.idupdate);
                 amountEdt = layout.findViewById(R.id.updateAmount);
                 catupdate = layout.findViewById(R.id.updateCategory);
                Button deleteBtn = layout.findViewById(R.id.idBtndelete);
                Button updateBtn = layout.findViewById(R.id.idBtnupdate);
                db = FirebaseFirestore.getInstance();
                    catupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                                categoryModels.clear();

                                Dialog dialog = new Dialog(listItem.getContext());
                                View layout = LayoutInflater.from(listItem.getContext()).inflate(R.layout.category_alert_dialogbox, null);
                                dialog.setContentView(layout);
                                RecyclerView simpleLists = layout.findViewById(R.id.simpleListView);

                                categoryModels.add(new CategoryModel(R.drawable.ic_clothing, "Clothing"));
                                categoryModels.add(new CategoryModel(R.drawable.ic_food, "Food"));
                                categoryModels.add(new CategoryModel(R.drawable.ic_gas_pump, "Fuel"));
                                categoryModels.add(new CategoryModel(R.drawable.ic_mobile_recharge, "Recharge"));
                                categoryModels.add(new CategoryModel(R.drawable.ic_lightning, "Electricity"));
                                categoryModels.add(new CategoryModel(R.drawable.ic_health, "Health"));
                                categoryModels.add(new CategoryModel(R.drawable.ic_wallet, "Salary"));
                                categoryListAdapter = new CategoryListAdapter(categoryModels, listItem.getContext(), HomeScreenListAdapter.this::onClick);
                                simpleLists.setLayoutManager(new LinearLayoutManager(listItem.getContext()));
                                simpleLists.setAdapter(categoryListAdapter);

                                dialog.show();



                        }
                    });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(listItem.getContext(),"deleted",Toast.LENGTH_SHORT).show();
                        delete();
                    }

                    private void delete() {
                        db.collection("userTranscation").document("userId").delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(listItem.getContext(), "Course has been deleted from Databse.", Toast.LENGTH_SHORT).show();
                                    bottomSheetTeachersDialog.dismiss();
//                                    Intent i = new Intent(UpdateCourse.this, MainActivity.class);
//                                    startActivity(i);
                                } else {

                                    Toast.makeText(listItem.getContext(), "Fail to delete the course. ", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                });
                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        courseName=amountEdt.getText().toString().trim();
//                        courseDuration=dateTV.getText().toString().trim();
                        courseDescription=catupdate.getText().toString().trim();
                        String mdate= DateFormat.getDateInstance().format(new Date());
//                        Data data=new Data(courseName,mdate,courseDescription,)

//                        db.collection("ExpenseManager").document("userId").set(data).addOnSuccessListener()



                        Toast.makeText(listItem.getContext(),"updated",Toast.LENGTH_SHORT).show();
                    }


                });
                dateTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar mCalender = Calendar.getInstance();
                        int year = mCalender.get(Calendar.YEAR);
                        int month = mCalender.get(Calendar.MONTH);
                        int dayOfMonth = mCalender.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(listItem.getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dateTV.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                                            }
                                        }, year, month, dayOfMonth);
                                        datePickerDialog.show();
                    }
                });

            }
        });
        return viewHolder;
    }

    private void onClick(int i) {
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
