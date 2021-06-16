package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.applycreditcard.expense_manager.R;

import java.util.ArrayList;

import Model.SMSReceiver;

public class SMSAdapter extends BaseAdapter {
    Context context;
    //    String SMSheading;
//    String SMSdescription;
//    String date;
    ArrayList<SMSReceiver> SMSlist;

    public SMSAdapter(Context context, ArrayList<SMSReceiver> SMSlist) {
        this.context = context;
//        this.SMSheading = SMSheading;
//        this.SMSdescription = SMSdescription;
//        this.date = date;
        this.SMSlist = SMSlist;
    }

    @Override
    public int getCount() {
        return SMSlist.size();
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
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.smslist_item, null);
        TextView smsheading = (TextView) convertView.findViewById(R.id.SMSTitle);
        TextView smsdescription = (TextView) convertView.findViewById(R.id.SMSDescription);
        TextView smsdate = (TextView) convertView.findViewById(R.id.SMSDate);
        smsheading.setText(SMSlist.get(position).get_address());
        smsdescription.setText(SMSlist.get(position).get_msg());
        smsdate.setText(SMSlist.get(position).get_time());
        return convertView;
    }
}
