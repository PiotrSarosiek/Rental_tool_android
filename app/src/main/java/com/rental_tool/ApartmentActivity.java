package com.rental_tool;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rental_tool.api.ApiClient;
import com.rental_tool.dto.apartment.ApartmentListResponse;
import com.rental_tool.dto.apartment.ApartmentResponse;
import com.rental_tool.dto.tenant.TenantListResponse;
import com.rental_tool.dto.tenant.TenantResponse;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment);

        apartmentAddress = findViewById(R.id.apartment_address);
        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            tenantListResponse = (TenantListResponse) intent.getSerializableExtra("data");
            apartmentAddress.setText((String) intent.getSerializableExtra("address"));
            Log.e("TAG", tenantListResponse.toString());
            initData();
            initRecyclerView();
        }
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

    }
}
