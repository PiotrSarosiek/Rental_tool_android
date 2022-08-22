package com.rental_tool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rental_tool.api.ApiClient;
import com.rental_tool.dto.extraCosts.ExtraCostDTO;
import com.rental_tool.dto.tenant.TenantListResponse;
import com.rental_tool.dto.tenant.TenantRequest;
import com.rental_tool.dto.tenant.TenantResponse;
import com.rental_tool.dto.tenantInvitation.TenantInvitationRequest;
import com.rental_tool.dto.tenantInvitation.TenantInvitationResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApartmentActivity extends AppCompatActivity implements RecyclerViewInterface {

    TextView apartmentAddress;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<TenantResponse> tenants;
    TenantsAdapter tenantsAdapter;
    TenantListResponse tenantListResponse;
    Button addTenantButton;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText editTextRent;
    EditText editTextName;
    EditText editTextCost;
    static ListView listView;
    Button addButton;
    Button cancelButton;
    Button saveButton;

    Long apartmentId;
    static ArrayList<ExtraCostDTO> extraCostDTOS = new ArrayList<>();
    static ListViewAdapter adapter;

    EditText editTextEmail;
    Button cancelButton2;
    Button sendButton;
    boolean invitationSent;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment);

        apartmentAddress = findViewById(R.id.apartment_address);
        recyclerView = findViewById(R.id.recyclerView);
        addTenantButton = findViewById(R.id.button_create_tenant);

        sharedPreferences = getSharedPreferences("UserInfo", 0);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            tenantListResponse = (TenantListResponse) intent.getSerializableExtra("data");
            apartmentAddress.setText((String) intent.getSerializableExtra("address"));
            apartmentId = ((Long) intent.getSerializableExtra("apartmentId"));
            Log.e("TAG", tenantListResponse.toString());
            initData();
            initRecyclerView();
        }

        addTenantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creteNewTenantPopup();
            }
        });
    }

    public void creteNewTenantPopup(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View tenantPopupView = getLayoutInflater().inflate(R.layout.create_tenant_popup, null);
        editTextRent = (EditText) tenantPopupView.findViewById(R.id.edit_text_rent);
        editTextName = (EditText) tenantPopupView.findViewById(R.id.edit_text_name);
        editTextCost = (EditText) tenantPopupView.findViewById(R.id.edit_text_cost);
        listView = (ListView) tenantPopupView.findViewById(R.id.list_view);
        addButton = (Button) tenantPopupView.findViewById(R.id.button_add) ;
        saveButton = (Button) tenantPopupView.findViewById(R.id.save_button);
        cancelButton = (Button) tenantPopupView.findViewById(R.id.cancel_button);
        dialogBuilder.setView(tenantPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        adapter = new ListViewAdapter(getApplicationContext(), extraCostDTOS);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String cost = editTextCost.getText().toString();
                if(name.length() == 0 || cost.length() == 0){
                    Toast.makeText(ApartmentActivity.this, "name and cost mus be filled", Toast.LENGTH_LONG).show();
                }
                else {
                    addItem(name, cost);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextRent != null){
                    TenantRequest tenantRequest = new TenantRequest(Double.parseDouble(editTextRent.getText().toString()), extraCostDTOS);
                    createTenant(apartmentId, tenantRequest);
                    dialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void inviteUserPopup(TenantResponse tenant){
        dialogBuilder = new AlertDialog.Builder(this);
        final View inviteUserPopupView = getLayoutInflater().inflate(R.layout.invite_user_popup, null);
        editTextEmail = (EditText) inviteUserPopupView.findViewById(R.id.edit_text_email);
        sendButton = (Button) inviteUserPopupView.findViewById(R.id.send_button);
        cancelButton = (Button) inviteUserPopupView.findViewById(R.id.cancel_button);
        dialogBuilder.setView(inviteUserPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail != null){
                    long landlordId = sharedPreferences.getLong("userId", 10000);
                    createInvitation(editTextEmail.getText().toString(), landlordId, tenant.getId());
                    if(invitationSent)
                        dialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void removeItem(int remove){
        extraCostDTOS.remove(remove);
        listView.setAdapter(adapter);
    }

    public static void addItem(String name, String cost){
        extraCostDTOS.add(new ExtraCostDTO(name, Double.parseDouble(cost)));
        listView.setAdapter(adapter);
    }

    private void initData() {
        tenants = new ArrayList<>();
        tenants.addAll(tenantListResponse.getTenantResponseList());
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        tenantsAdapter = new TenantsAdapter(tenants, this, this);
        tenantsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tenantsAdapter);
    }

    @Override
    public void onItemCLick(int position) {
        inviteUserPopup(tenants.get(position));
    }

    public void createTenant(long apartmentId, TenantRequest tenantRequest){
        Call<TenantResponse> tenantResponseCall = ApiClient.getApiService().createTenant(apartmentId, tenantRequest);
        tenantResponseCall.enqueue(new Callback<TenantResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Call<TenantResponse> call, @NotNull Response<TenantResponse> response) {
                if(response.isSuccessful()){
                    TenantResponse tenantResponse = response.body();
                    tenants.add(tenantResponse);
                    initRecyclerView();
                }
                else{
                    String message = "An error occurred please try again";
                    Toast.makeText(ApartmentActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<TenantResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ApartmentActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createInvitation(String email, long landlordId, long tenantId){
        Call<TenantInvitationResponse> tenantInvitationResponseCall = ApiClient.getApiService().createTenantInvitation(new TenantInvitationRequest(email, landlordId, tenantId));
        tenantInvitationResponseCall.enqueue(new Callback<TenantInvitationResponse>() {
            @Override
            public void onResponse(Call<TenantInvitationResponse> call, Response<TenantInvitationResponse> response) {
                if(response.isSuccessful()){
                    TenantInvitationResponse tenantInvitationResponse = response.body();
                    String message = tenantInvitationResponse.getUser().getName()+" "+tenantInvitationResponse.getUser().getSurname()+" invited";
                    Toast.makeText(ApartmentActivity.this, message, Toast.LENGTH_LONG).show();
                    invitationSent = true;
                }
                else{
                    String message = "User does not exists";
                    Toast.makeText(ApartmentActivity.this, message, Toast.LENGTH_LONG).show();
                    invitationSent = false;
                }
            }

            @Override
            public void onFailure(Call<TenantInvitationResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ApartmentActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
