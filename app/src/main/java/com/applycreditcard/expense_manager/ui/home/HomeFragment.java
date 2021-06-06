package com.applycreditcard.expense_manager.ui.home;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applycreditcard.expense_manager.CustomeAdapter;
import com.applycreditcard.expense_manager.HomeScreenListAdapter;
import com.applycreditcard.expense_manager.HomeScreenListData;
import com.applycreditcard.expense_manager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment<listData> extends Fragment {
//        final HomeFragment context = this;
    private FirebaseAuth mauth;
    private List<Data>listData;

    private DatabaseReference incomedata;
   private FirebaseDatabase adapter;
   private HomeScreenListAdapter adapters;
    private RecyclerView recyclerView;
//     Button button;
    TextView text,amount;
    ListView simpleList;
    String categoryList[] = {"Clothing", "Food", "Fuel", "Health", "Recharge", "Electricity Bill", "Salary"};
    int flags[] = {R.drawable.ic_clothing, R.drawable.ic_food, R.drawable.ic_gas_pump, R.drawable.ic_health, R.drawable.ic_mobile_recharge, R.drawable.ic_lightning, R.drawable.ic_wallet};
    private LinearLayout bottomSheetLL;

    HomeScreenListData homeScreenListData[] = new HomeScreenListData[]
        {
                new HomeScreenListData(R.drawable.ic_clothing, "Clothing", "500"),
                new HomeScreenListData(R.drawable.ic_clothing, "Clothing", "500"),
                new HomeScreenListData(R.drawable.ic_clothing, "Clothing", "500"),
                new HomeScreenListData(R.drawable.ic_clothing, "Clothing", "500"),
                new HomeScreenListData(R.drawable.ic_clothing, "Clothing", "500"),
                new HomeScreenListData(R.drawable.ic_clothing, "Clothing", "500"),
                new HomeScreenListData(R.drawable.ic_clothing, "Clothing", "500"),
                new HomeScreenListData(R.drawable.ic_clothing, "Clothing", "500"),
                new HomeScreenListData(R.drawable.ic_clothing, "Clothing", "500"),
                new HomeScreenListData(R.drawable.ic_clothing, "Clothing", "500"),

    };



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        adapter= FirebaseDatabase.getInstance();
        incomedata = adapter.getReference("income");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button button = root.findViewById(R.id.addTransaction);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayBottomSheet();
            }

        });
        recyclerView = root.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        HomeScreenListAdapter homeScreenListAdapter = new HomeScreenListAdapter(homeScreenListData);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(homeScreenListAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);


        return root;


    }


    public void fetch()
    {

        listData=new ArrayList<Data>();
                final DatabaseReference nm= FirebaseDatabase.getInstance().getReference("income");
                nm.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot npsnapshot : snapshot.getChildren()){
                                Data l=npsnapshot.getValue(Data.class);
                                listData.add(l);

                            }

                           // adapters=new Adapter(listData);
                            recyclerView.setAdapter(adapters);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }

//
//    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
//        private List<Data> listData;
//
//        public MyAdapter(List<Data> listData) {
//            this.listData = listData;
//        }
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.homescreen_list_item,parent,false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            Data ld=listData.get(position);
//            holder.txtid.setText(ld.getAmount());
//            holder.txtname.setText(ld.getCategory());
////            holder.txtmovie.setText(ld.getMovie());
//        }
//
//        @Override
//        public int getItemCount() {
//            return listData.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder{
//            private TextView txtid,txtname,txtmovie;
//            public ViewHolder(View itemView) {
//                super(itemView);
//                txtid=(TextView)itemView.findViewById(R.id.expenseitemName);
//                txtname=(TextView)itemView.findViewById(R.id.expenseItemAmount);
////                txtmovie=(TextView)itemView.findViewById(R.id.movietxt);
//            }
//        }
//    }




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

               Map<String,Object>map=new HashMap<>();
              int amount= Integer.parseInt(amountEdt.getText().toString().trim());
              String category=categoryTV.getText().toString().trim();
              String time=dateTV.getText().toString().trim();
               map.put("amount",amount);
               map.put("category",category);
               map.put("date",time);
               mauth = FirebaseAuth.getInstance();
               FirebaseUser muser = mauth.getCurrentUser();
               String uid = "";
               try {
                   uid = muser.getUid();
               }
               catch (NullPointerException ignored) {
                   incomedata = FirebaseDatabase.getInstance().getReference().child("income").child(uid);
                   String id = incomedata.push().getKey();
                   Data data = new Data(amount, category, time);
                   incomedata.child(id).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {


                           amountEdt.setText("");
                           categoryTV.setText("");
                           dateTV.setText("");

                           Toast.makeText(getContext(), "Data Added", Toast.LENGTH_SHORT).show();
                           bottomSheetTeachersDialog.dismiss();
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull @NotNull Exception e) {


                           Toast.makeText(getActivity(), "Data failure", Toast.LENGTH_SHORT).show();

                       }
                   });
               }
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