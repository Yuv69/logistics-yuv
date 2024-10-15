package com.example.firebasetask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class VehicleListAdapter extends ArrayAdapter<String> {
    public VehicleListAdapter(Context context, ArrayList<String> vehicles) {
        super(context, 0, vehicles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String vehicle = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView vehicleTextView = convertView.findViewById(android.R.id.text1);
        vehicleTextView.setText(vehicle);
        return convertView;
    }
}
