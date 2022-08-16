package com.rental_tool;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rental_tool.dto.extraCosts.ExtraCostDTO;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<ExtraCostDTO> {

    ArrayList<ExtraCostDTO> list;
    Context context;

    public ListViewAdapter(@NonNull Context context, ArrayList<ExtraCostDTO> list) {
        super(context,R.layout.extra_costs_list_row, list);
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.extra_costs_list_row, null);

            TextView number = convertView.findViewById(R.id.number);
            number.setText(position + 1 + ".");

            TextView name = convertView.findViewById(R.id.name);
            name.setText(list.get(position).getName());

            TextView cost = convertView.findViewById(R.id.cost);
            cost.setText(list.get(position).getAmount().toString());

            ImageView delete = convertView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApartmentActivity.removeItem(position);
                }
            });
        }
        return convertView;
    }
}
