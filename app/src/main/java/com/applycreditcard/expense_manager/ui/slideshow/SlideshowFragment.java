package com.applycreditcard.expense_manager.ui.slideshow;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.applycreditcard.expense_manager.R;
import com.applycreditcard.expense_manager.SMSAdapter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.SMSReceiver;

public class SlideshowFragment extends Fragment {

    private String stringNumber = "0000";
    ArrayList<SMSReceiver> listSms = new ArrayList<SMSReceiver>();
    private SMSAdapter smsAdapter;
    private ListView smsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        smsList = root.findViewById(R.id.SMSList);
        listSms = new ArrayList<>();
        Cursor cursor = getContext().getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int totalSum = cursor.getCount();
        if (cursor.moveToFirst()) {
            // must check the result to prevent exception
            for (int i = 0; i < totalSum; i++) {
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body")).toLowerCase();
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String regex = "[rR][sS]\\.?\\s[,\\d]+\\.?\\d{0,2}|[iI][nN][rR]\\.?\\s*[,\\d]+\\.?\\d{0,2}\n";
//                Pattern regEx
//                        = Pattern.compile("(?:inr|rs)+[\\s]*[0-9+[\\,]*+[0-9]*]+[\\.]*[0-9]+");
//                // Find instance of pattern matches
//                Matcher m = regEx.matcher(body);
//                if (m.find()) {
//                    try {
//                        Log.e("amount_value= ", "" + m.group(0));
//                        String amount = (m.group(0).replaceAll("inr", ""));
//                        amount = amount.replaceAll("rs", "");
//                        amount = amount.replaceAll("inr", "");
//                        amount = amount.replaceAll(" ", "");
//                        amount = amount.replaceAll(",", "");
//                        // smsDto.setAmount(Double.valueOf(amount));
//                        if (body.contains("debited") ||
//                                body.contains("purchasing") || body.contains("purchase") || body.contains("dr")) {
//                            //here amount debited.
//                            //smsDto.setTransactionType("0");
//                            body = "Debited : " + Double.valueOf(amount);
//                        } else if (body.contains("credited") || body.contains("cr")) {
//                            //here amount credited.
//                            //smsDto.setTransactionType("1");
//                            body = "Credited : " + Double.parseDouble(amount);
//                        }
//                        //smsDto.setParsed("1");
//                        Log.e("matchedValue= ", "" + amount);
////                        if (!Character.isDigit(smsDto.getSenderid().charAt(0)))
////                            resSms.add(smsDto);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }

                if (address.equals("VM-KTKBNK") || address.equals("VK-MAHABK")) {
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(body);
                    String amountFound = "";
                    if (body.contains("credit") || body.contains("credited") || body.contains("cr")) {
                        if (matcher.find()) {
                            //extracted amount
                            amountFound = "Credited " + body.substring(matcher.start(), matcher.end());
                            Log.e("TAG", "AMOUNT FOUND IS " + amountFound);
                        }
                    }
                    if (body.contains("debited")) {
                        // while (matcher.find()) {
                        //extracted amount
                        if (matcher.find()) {
                            amountFound = "Debited " + body.substring(matcher.start(), matcher.end());
                            Log.e("TAG", "AMOUNT FOUND IS " + amountFound);
                        }

                    }

                    // Log.e("TAG", "SMS IS " + address + "\n" + body + "\n\n");
                    //body = body.substring(61,69);


                    listSms.add(new SMSReceiver(address, body, date));
                }


                cursor.moveToNext();
            }
        }
        cursor.close();
        smsAdapter = new

                SMSAdapter(getContext(), listSms);
        smsList.setAdapter(smsAdapter);
        return root;
    }

//    private static ArrayList<SmsDto> parsevalues(ArrayList<SmsDto> body_val) {
//        ArrayList<SmsDto> resSms = new ArrayList<>();
//        for (int i = 0; i < body_val.size(); i++) {
//            SmsDto smsDto = body_val.get(i);
//            Pattern regEx
//                    = Pattern.compile("(?:inr|rs)+[\\s]*[0-9+[\\,]*+[0-9]*]+[\\.]*[0-9]+");
//            // Find instance of pattern matches
//            Matcher m = regEx.matcher(smsDto.getBody());
//            if (m.find()) {
//                try {
//                    Log.e("amount_value= ", "" + m.group(0));
//                    String amount = (m.group(0).replaceAll("inr", ""));
//                    amount = amount.replaceAll("rs", "");
//                    amount = amount.replaceAll("inr", "");
//                    amount = amount.replaceAll(" ", "");
//                    amount = amount.replaceAll(",", "");
//                    smsDto.setAmount(Double.valueOf(amount));
//                    if (smsDto.getBody().contains("debited") ||
//                            smsDto.getBody().contains("purchasing") || smsDto.getBody().contains("purchase") || smsDto.getBody().contains("dr")) {
//                        smsDto.setTransactionType("0");
//                    } else if (smsDto.getBody().contains("credited") || smsDto.getBody().contains("cr")) {
//                        smsDto.setTransactionType("1");
//                    }
//                    smsDto.setParsed("1");
//                    Log.e("matchedValue= ", "" + amount);
//                    if (!Character.isDigit(smsDto.getSenderid().charAt(0)))
//                        resSms.add(smsDto);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Log.e("No_matchedValue ", "No_matchedValue ");
//            }
//        }
//        return resSms;
//    }
}