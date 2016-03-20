package com.example.user.phonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 20.3.2016.
 */
public class CustomAdapter2 extends BaseAdapter{
    private final Context context;
    private final String[] values;
    ArrayList<String> contact_name_list;
    ArrayList<String> contact_phone_list;
    List<ContactInformations> a;

    public CustomAdapter2(Context context, String[] values) {

        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return 0;
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
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.contact_name);
        TextView textView2 = (TextView) rowView.findViewById(R.id.phone_number);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);
        textView2.setText("Heloooooo");
        imageView.setImageResource(R.mipmap.ic_launcher);

        return rowView;
    }
}
