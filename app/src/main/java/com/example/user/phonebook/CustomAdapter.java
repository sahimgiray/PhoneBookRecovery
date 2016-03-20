package com.example.user.phonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 20.3.2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;
    ArrayList<String> contact_name_list;
    ArrayList<String> contact_phone_list;
    List<ContactInformations> a;

    public CustomAdapter(Context context, List<String> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.contact_name);
        TextView textView2 = (TextView) rowView.findViewById(R.id.phone_number);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        String[] parts = values.get(position).split("-");
        textView.setText(parts[0]);
        textView2.setText(parts[1]);
        imageView.setImageResource(R.mipmap.ic_launcher);


        return rowView;
    }
}
