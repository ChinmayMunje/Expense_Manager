package com.applycreditcard.expense_manager.ui.home;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applycreditcard.expense_manager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.CategoryListAdapter;
import Adapter.HomeScreenListAdapter;
import Model.CategoryModel;
import Model.Data;


public class HomeFragment extends Fragment implements CategoryListAdapter.OnclickInterface, HomeScreenListAdapter.OnclickInterface {
    //    final Context context = this;
    // Button button;
    RecyclerView simpleList;
    private CategoryListAdapter categoryListAdapter;
    private ArrayList<CategoryModel> categoryModels;
    private LinearLayout bottomSheetLL;
    private FirebaseFirestore db;
    private ImageView categoryIV;
    private HomeScreenListAdapter homeScreenListAdapter;
    private ArrayList<Data> homeScreenListData;
    private String date, amount, category;
    private ProgressBar loadingPB;
    private CardView balanceCV, incomeCV, expenseCV;
    private TextView incomeTv, expenseTv, balanceTv, categoryTV, messageTV, setBalanceTv, balanceTV;
    int income = 0, expense = 0, balance = 0;
    Dialog dialog;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String AMOUNT_KEY = "amt";
    private FirebaseAuth mAuth;

    SharedPreferences sharedpreferences;
    int setAmount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        loadingPB = root.findViewById(R.id.idPBLoading);
        db = FirebaseFirestore.getInstance();
        sharedpreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        setAmount = sharedpreferences.getInt(AMOUNT_KEY, 0);
        mAuth = FirebaseAuth.getInstance();
        categoryModels = new ArrayList<>();
        messageTV = root.findViewById(R.id.idTVMessage);
        balanceCV = root.findViewById(R.id.idCVBalance);
        expenseCV = root.findViewById(R.id.idCVExpense);
        incomeCV = root.findViewById(R.id.idCVIncome);
        setBalanceTv = root.findViewById(R.id.setBalance);
        balanceTV = root.findViewById(R.id.idTVBalance);
        incomeTv = root.findViewById(R.id.incomeAmount);
        balanceTv = root.findViewById(R.id.balanceAmount);
        expenseTv = root.findViewById(R.id.expenseAmount);
        homeScreenListData = new ArrayList<>();


        if (setAmount != 0) {
            balanceTV.setText("₹ " + setAmount);
        }
        Button button = root.findViewById(R.id.addTransaction);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayBottomSheet();
            }
        });
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        homeScreenListAdapter = new HomeScreenListAdapter(homeScreenListData, this::onExpenseClick);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(homeScreenListAdapter);
        readData();

        incomeCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeScreenListData.clear();
                readFilteredData("Income");
            }
        });
        expenseCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeScreenListData.clear();
                readFilteredData("Expense");
            }
        });
        balanceCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                income = 0;
                balance = 0;
                expense = 0;
                homeScreenListData.clear();
                readData();
            }
        });

        setBalanceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getContext());
                View layout = LayoutInflater.from(getContext()).inflate(R.layout.setbalance_alert_dialogbox, null);
                dialog.setContentView(layout);
                EditText setLimitEditText = layout.findViewById(R.id.setLimitET);
                Button setLimitBtn = layout.findViewById(R.id.idBtnSetLimit);
                setLimitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(setLimitEditText.getText().toString())) {
                            Toast.makeText(getContext(), "Please Enter Amount", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            int amt = Integer.parseInt(setLimitEditText.getText().toString());
                            editor.putInt(AMOUNT_KEY, amt);
                            editor.apply();
                            dialog.dismiss();
                            balanceTV.setText("₹ " + sharedpreferences.getInt(AMOUNT_KEY, 0));
                        }
                    }
                });
                dialog.show();
            }
        });

        return root;
    }

    private void readData() {
        loadingPB.setVisibility(View.VISIBLE);
        db.collection("ExpenseManager").document(mAuth.getCurrentUser().getUid()).collection("userTranscation").orderBy("timestamp").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                loadingPB.setVisibility(View.GONE);
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        Data c = d.toObject(Data.class);
                        c.setDocId(d.getId());
                        // created for recycler view
                        homeScreenListData.add(c);
                        homeScreenListAdapter.notifyDataSetChanged();
                    }
                }
                if (homeScreenListData.size() == 0) {
                    messageTV.setText("Please enter transcations");
                    messageTV.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < homeScreenListData.size(); i++) {
                    Data c = homeScreenListData.get(i);
                    if (c.getCategory().toLowerCase().equals("clothing")) {
                        c.setImgid(R.drawable.ic_clothing);
                    } else if (c.getCategory().toLowerCase().equals("food")) {
                        c.setImgid(R.drawable.ic_food);
                    } else if (c.getCategory().toLowerCase().equals("fuel")) {
                        c.setImgid(R.drawable.ic_gas_pump);
                    } else if (c.getCategory().toLowerCase().equals("health")) {
                        c.setImgid(R.drawable.ic_health);
                    } else if (c.getCategory().toLowerCase().equals("recharge")) {
                        c.setImgid(R.drawable.ic_mobile_recharge);
                    } else if (c.getCategory().toLowerCase().equals("electricity")) {
                        c.setImgid(R.drawable.ic_lightning);
                    } else {
                        c.setImgid(R.drawable.ic_wallet);
                    }

                    if (homeScreenListData.get(i).getParentCategory().equals("Income")) {
                        income = income + Integer.parseInt(c.getAmount());
                    } else {
                        expense = expense + Integer.parseInt(c.getAmount());
                    }
                    // double prevBal = balance;
                    balance = income - expense;
                    //if (prevBal == 0) {
                    balanceTv.setText("₹ " + balance);
                    incomeTv.setText("₹ " + income);
                    expenseTv.setText("₹ " + expense);
                    //}


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingPB.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void readFilteredData(String filter) {

        loadingPB.setVisibility(View.VISIBLE);
        db.collection("ExpenseManager").document(mAuth.getCurrentUser().getUid()).collection("userTranscation").whereEqualTo("parentCategory", filter).orderBy("timestamp").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                loadingPB.setVisibility(View.GONE);
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        Data c = d.toObject(Data.class);

                        if (c.getCategory().toLowerCase().equals("clothing")) {
                            c.setImgid(R.drawable.ic_clothing);
                        } else if (c.getCategory().toLowerCase().equals("food")) {
                            c.setImgid(R.drawable.ic_food);
                        } else if (c.getCategory().toLowerCase().equals("fuel")) {
                            c.setImgid(R.drawable.ic_gas_pump);
                        } else if (c.getCategory().toLowerCase().equals("health")) {
                            c.setImgid(R.drawable.ic_health);
                        } else if (c.getCategory().toLowerCase().equals("recharge")) {
                            c.setImgid(R.drawable.ic_mobile_recharge);
                        } else if (c.getCategory().toLowerCase().equals("electricity")) {
                            c.setImgid(R.drawable.ic_lightning);
                        } else {
                            c.setImgid(R.drawable.ic_wallet);
                        }


                        // created for recycler view.
                        homeScreenListData.add(c);
                        homeScreenListAdapter.notifyDataSetChanged();
                    }
                    if (homeScreenListData.size() == 0) {
                        messageTV.setText("Please enter transcations");
                        messageTV.setVisibility(View.VISIBLE);
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingPB.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void addData(String amount, String date, String category, BottomSheetDialog bottomSheetDialog) {
        CollectionReference collectionReference = db.collection("ExpenseManager").document(mAuth.getCurrentUser().getUid()).collection("userTranscation");
        String parentCat = "";
        if (category == "Salary") {
            parentCat = "Income";
        } else {
            parentCat = "Expense";
        }
        Date currentDate = Calendar.getInstance().getTime();
        Map map = new HashMap();
        map.put("amount", amount);
        map.put("category", category);
        map.put("date", date);
        map.put("parentCategory", parentCat);
        map.put("timestamp", currentDate);

        collectionReference.add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                homeScreenListData.clear();
                readData();
                bottomSheetDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                bottomSheetDialog.dismiss();
                Toast.makeText(getContext(), "Fail to add Data \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void displayBottomSheet() {
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet, bottomSheetLL);
        bottomSheetTeachersDialog.setContentView(layout);
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        bottomSheetTeachersDialog.show();
        TextView dateTV = layout.findViewById(R.id.idTVDate);
        categoryIV = layout.findViewById(R.id.idIVCategory);
        EditText amountEdt = layout.findViewById(R.id.idEdtAmount);
        categoryTV = layout.findViewById(R.id.idTVCategory);
        Button cancelBtn = layout.findViewById(R.id.idBtnCancel);
        Button addBtn = layout.findViewById(R.id.idBtnAdd);


        categoryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryModels.clear();
                dialog = new Dialog(getContext());
                View layout = LayoutInflater.from(getContext()).inflate(R.layout.category_alert_dialogbox, null);
                dialog.setContentView(layout);
                simpleList = layout.findViewById(R.id.simpleListView);

                categoryModels.add(new CategoryModel(R.drawable.ic_clothing, "Clothing"));
                categoryModels.add(new CategoryModel(R.drawable.ic_food, "Food"));
                categoryModels.add(new CategoryModel(R.drawable.ic_gas_pump, "Fuel"));
                categoryModels.add(new CategoryModel(R.drawable.ic_mobile_recharge, "Recharge"));
                categoryModels.add(new CategoryModel(R.drawable.ic_lightning, "Electricity"));
                categoryModels.add(new CategoryModel(R.drawable.ic_health, "Health"));
                categoryModels.add(new CategoryModel(R.drawable.ic_wallet, "Salary"));
                categoryListAdapter = new CategoryListAdapter(categoryModels, getContext(), HomeFragment.this::onClick);
                simpleList.setLayoutManager(new LinearLayoutManager(getContext()));
                simpleList.setAdapter(categoryListAdapter);

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

                if (balance == setAmount) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Message").setMessage("Your are spending more than your set Limit !!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    dialog.show();


                } else {
                    amount = amountEdt.getText().toString();
                    date = dateTV.getText().toString();
                    category = categoryTV.getText().toString();
                    addData(amount, date, category, bottomSheetTeachersDialog);
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

    //Delete and Update Bottom Sheet
    private void displayUpdateBottomSheet(int position) {
        String date = homeScreenListData.get(position).getDate();
        String amount = homeScreenListData.get(position).getAmount();
        String parentCategory = homeScreenListData.get(position).getParentCategory();
        String category = homeScreenListData.get(position).getCategory();
        int imgId = homeScreenListData.get(position).getImgid();
        String docId = homeScreenListData.get(position).getDocId();


        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_update, bottomSheetLL);
        bottomSheetTeachersDialog.setContentView(layout);
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        bottomSheetTeachersDialog.show();
        TextView dateTV = layout.findViewById(R.id.idTVDate);
        categoryIV = layout.findViewById(R.id.idIVCategory);
        EditText amountEdt = layout.findViewById(R.id.idEdtAmount);
        categoryTV = layout.findViewById(R.id.idTVCategory);
        Button updateBtn = layout.findViewById(R.id.idBtnUpdate);
        Button deleteBtn = layout.findViewById(R.id.idBtnDelete);
        dateTV.setText(date);
        amountEdt.setText(amount);
        categoryTV.setText(category);
        categoryIV.setImageResource(imgId);


        categoryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryModels.clear();
                dialog = new Dialog(getContext());
                View layout = LayoutInflater.from(getContext()).inflate(R.layout.category_alert_dialogbox, null);
                dialog.setContentView(layout);
                simpleList = layout.findViewById(R.id.simpleListView);

                categoryModels.add(new CategoryModel(R.drawable.ic_clothing, "Clothing"));
                categoryModels.add(new CategoryModel(R.drawable.ic_food, "Food"));
                categoryModels.add(new CategoryModel(R.drawable.ic_gas_pump, "Fuel"));
                categoryModels.add(new CategoryModel(R.drawable.ic_mobile_recharge, "Recharge"));
                categoryModels.add(new CategoryModel(R.drawable.ic_lightning, "Electricity"));
                categoryModels.add(new CategoryModel(R.drawable.ic_health, "Health"));
                categoryModels.add(new CategoryModel(R.drawable.ic_wallet, "Salary"));
                categoryListAdapter = new CategoryListAdapter(categoryModels, getContext(), HomeFragment.this::onClick);
                simpleList.setLayoutManager(new LinearLayoutManager(getContext()));
                simpleList.setAdapter(categoryListAdapter);

                dialog.show();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update data here
                updatedata(amount, date, category, parentCategory, docId);
                bottomSheetTeachersDialog.dismiss();
                readData();
            }

        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete data here
//                homeScreenListData.clear();
                delete(docId);
//                readData();
                bottomSheetTeachersDialog.dismiss();

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

    private void delete(String docId) {
        homeScreenListData.clear();
        readData();
        db.collection("ExpenseManager").document(mAuth.getCurrentUser().getUid()).collection("userTranscation").document(docId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(), "failure to deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int position) {
        String cate = categoryModels.get(position).getCategoryName();
        categoryTV.setText(cate);
        categoryIV.setImageResource(categoryModels.get(position).getImageUrl());
        Drawable drawable = getResources().getDrawable(categoryModels.get(position).getImageUrl());
        categoryTV.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_clothing), null, null, null);
        dialog.dismiss();
    }

    private void updatedata(String amount, String date, String category, String parentCategory, String docId) {
        Data updateData = new Data(amount, date, category, parentCategory);
        db.collection("ExpenseManager").document(mAuth.getCurrentUser().getUid()).collection("userTranscation").document(docId).set(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Data has been updated..", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to update the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onExpenseClick(int position) {
        displayUpdateBottomSheet(position);

    }
}