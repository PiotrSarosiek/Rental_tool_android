package com.rental_tool;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rental_tool.dto.tenant.TenantResponse;
import com.rental_tool.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class TenantsAdapter extends RecyclerView.Adapter<TenantsAdapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private final Context context;

    private final List<TenantResponse> tenants;

    public TenantsAdapter(List<TenantResponse> tenants, RecyclerViewInterface recyclerViewInterface, Context context) {
        this.tenants = tenants;
        this.recyclerViewInterface = recyclerViewInterface;
        this.context=context;
    }

    @NonNull
    @Override
    public TenantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tenant_item, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull TenantsAdapter.ViewHolder holder, int position) {
        TenantResponse tenantResponse = tenants.get(position);
        holder.setData(tenantResponse, context);
    }

    @Override
    public int getItemCount() {
        return tenants.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewUser;
        private final TextView rentValue;
        private final ListView listViewExtraCosts;
        private final Button inviteUserButton;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            textViewUser = itemView.findViewById(R.id.text_view_user);
            rentValue = itemView.findViewById(R.id.rent_value);
            listViewExtraCosts = itemView.findViewById(R.id.list_view_extra_costs);
            inviteUserButton = itemView.findViewById(R.id.invite_user_button);


            inviteUserButton.setOnClickListener(new View.OnClickListener() {
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

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void setData(TenantResponse tenantResponse, Context context) {
            if(tenantResponse.getUser()!=null){
                textViewUser.setText(tenantResponse.getUser().getName()+" "+tenantResponse.getUser().getSurname());
                inviteUserButton.setVisibility(View.INVISIBLE);
            }
            else {
                textViewUser.setText("No user assigned");
            }

            rentValue.setText(String.valueOf(tenantResponse.getRent()));
            List<String> sth = new ArrayList<>();
            tenantResponse.getExtraCosts().forEach(extraCostResponse -> sth.add(extraCostResponse.getName()+": "+extraCostResponse.getAmount()));
            listViewExtraCosts.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_1, sth));
            UIUtils.setListViewHeightBasedOnItems(listViewExtraCosts);
        }

    }




}
