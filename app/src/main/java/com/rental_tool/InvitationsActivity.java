package com.rental_tool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rental_tool.api.ApiClient;
import com.rental_tool.dto.tenantInvitation.TenantInvitationListResponse;
import com.rental_tool.dto.tenantInvitation.TenantInvitationResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvitationsActivity extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<TenantInvitationResponse> tenantInvitations;
    InvitationsAdapter invitationsAdapter;
    TenantInvitationListResponse tenantInvitationListResponse;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);

        recyclerView = findViewById(R.id.recycler_view_invitations);

        sharedPreferences = getSharedPreferences("UserInfo", 0);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            tenantInvitationListResponse = (TenantInvitationListResponse) intent.getSerializableExtra("data");
            Log.e("TAG", tenantInvitationListResponse.toString());
            initData();
            initRecyclerView();
        }
    }

    private void initData() {
        tenantInvitations = new ArrayList<>();
        tenantInvitations.addAll(tenantInvitationListResponse.getTenantInvitationResponseList());
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        invitationsAdapter = new InvitationsAdapter(tenantInvitations, this, this);
        invitationsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(invitationsAdapter);
    }

    @Override
    public void onItemCLick(int position) {
        markInvitation(tenantInvitations.get(position).getId(), true);
    }

    public void markInvitation(Long tenantInvitationId, Boolean accepted) {
        Call<TenantInvitationResponse> tenantInvitationResponseCall = ApiClient.getApiService().updateInvitationStatus(tenantInvitationId, accepted);
        tenantInvitationResponseCall.enqueue(new Callback<TenantInvitationResponse>() {
            @Override
            public void onResponse(Call<TenantInvitationResponse> call, Response<TenantInvitationResponse> response) {
                if (response.isSuccessful()) {
                    TenantInvitationResponse tenantInvitationResponse = response.body();
                    String message = "Invitation from " + tenantInvitationResponse.getLandlord().getName() + " " + tenantInvitationResponse.getLandlord().getSurname() + " accepted";
                    Toast.makeText(InvitationsActivity.this, message, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    String message = "Something went wrong";
                    Toast.makeText(InvitationsActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TenantInvitationResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(InvitationsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(InvitationsActivity.this, PulpitTenantActivity.class));
        finish();
    }
}
