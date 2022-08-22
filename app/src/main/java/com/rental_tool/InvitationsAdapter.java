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
import androidx.recyclerview.widget.RecyclerView;

import com.rental_tool.dto.tenant.TenantResponse;
import com.rental_tool.dto.tenantInvitation.TenantInvitationResponse;
import com.rental_tool.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class InvitationsAdapter extends RecyclerView.Adapter<InvitationsAdapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private final Context context;

    private final List<TenantInvitationResponse> tenantInvitations;

    public InvitationsAdapter(List<TenantInvitationResponse> tenantInvitations, RecyclerViewInterface recyclerViewInterface, Context context) {
        this.tenantInvitations = tenantInvitations;
        this.recyclerViewInterface = recyclerViewInterface;
        this.context=context;
    }

    @NonNull
    @Override
    public InvitationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invitation_item, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull InvitationsAdapter.ViewHolder holder, int position) {
        TenantInvitationResponse tenantInvitationResponse = tenantInvitations.get(position);
        holder.setData(tenantInvitationResponse, context);
    }

    @Override
    public int getItemCount() {
        return tenantInvitations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewUser;
        private final TextView rentValue;
        private final ListView listViewExtraCosts;
        private final Button acceptInvitationButton;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            textViewUser = itemView.findViewById(R.id.text_view_user);
            rentValue = itemView.findViewById(R.id.rent_value);
            listViewExtraCosts = itemView.findViewById(R.id.list_view_extra_costs);
            acceptInvitationButton = itemView.findViewById(R.id.accept_invitation_button);

            acceptInvitationButton.setOnClickListener(new View.OnClickListener() {
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
        public void setData(TenantInvitationResponse tenantInvitationResponse, Context context) {
            textViewUser.setText(tenantInvitationResponse.getLandlord().getName()+" "+tenantInvitationResponse.getLandlord().getSurname());
            rentValue.setText(String.valueOf(tenantInvitationResponse.getTenant().getRent()));
            List<String> sth = new ArrayList<>();
            tenantInvitationResponse.getTenant().getExtraCosts().forEach(extraCostResponse -> sth.add(extraCostResponse.getName()+": "+extraCostResponse.getAmount()));
            listViewExtraCosts.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_1, sth));
            UIUtils.setListViewHeightBasedOnItems(listViewExtraCosts);
        }
    }

}
