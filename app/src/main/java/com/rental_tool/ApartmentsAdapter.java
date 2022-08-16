package com.rental_tool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApartmentsAdapter extends RecyclerView.Adapter<ApartmentsAdapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    private final List<ApartmentItem> apartments;

    public ApartmentsAdapter(List<ApartmentItem> apartments, RecyclerViewInterface recyclerViewInterface) {
        this.apartments = apartments;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ApartmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apartment_item, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ApartmentsAdapter.ViewHolder holder, int position) {
        String address = apartments.get(position).getAddress();
        holder.setData(address);
    }

    @Override
    public int getItemCount() {
        return apartments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int position = getBindingAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemCLick(position);
                        }
                    }
                }
            });
        }

        public void setData(String address) {
            textView.setText(address);
        }
    }


}
