package com.rental_tool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rental_tool.dto.extraCosts.ExtraCostResponse;

import java.util.List;

public class ExtraCostsAdapter extends RecyclerView.Adapter<ExtraCostsAdapter.ViewHolder> {

    private final List<ExtraCostResponse> extraCostResponseList;

    public ExtraCostsAdapter(List<ExtraCostResponse> extraCostResponseList) {
        this.extraCostResponseList = extraCostResponseList;
    }

    @NonNull
    @Override
    public ExtraCostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.extra_cost_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtraCostsAdapter.ViewHolder holder, int position) {
        ExtraCostResponse extraCostResponse = extraCostResponseList.get(position);
        holder.setData(extraCostResponse);
    }

    @Override
    public int getItemCount() {
        return extraCostResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewName;
        private final TextView textViewAmount;
        //private final RecyclerView extraCostsRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int position = getBindingAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemCLick(position);
                        }
                    }
                }
            });*/
        }

        public void setData(ExtraCostResponse extraCostResponse) {
            textViewName.setText(extraCostResponse.getName());
            textViewAmount.setText(String.valueOf(extraCostResponse.getAmount()));
        }
    }


}
