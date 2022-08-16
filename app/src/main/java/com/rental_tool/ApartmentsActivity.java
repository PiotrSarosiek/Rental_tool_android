package com.rental_tool;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rental_tool.api.ApiClient;
import com.rental_tool.dto.apartment.ApartmentListResponse;
import com.rental_tool.dto.apartment.ApartmentRequestBody;
import com.rental_tool.dto.apartment.ApartmentResponse;
import com.rental_tool.dto.tenant.TenantListResponse;
import com.rental_tool.dto.tenant.TenantResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApartmentsActivity extends AppCompatActivity implements RecyclerViewInterface{

    ApartmentListResponse apartmentListResponse;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ApartmentItem> apartments;
    ApartmentsAdapter apartmentsAdapter;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText editTextAddress;
    Button saveButton, cancelButton;
    FloatingActionButton fab;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments);

        fab = findViewById(R.id.fab);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            apartmentListResponse = (ApartmentListResponse) intent.getSerializableExtra("data");
            Log.e("TAG", apartmentListResponse.toString());
            initData();
            initRecyclerView();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creteNewApartmentPopup();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {

        apartments = new ArrayList<>();
        apartmentListResponse.getApartmentResponseList().forEach(apartmentResponse -> {
            apartments.add(new ApartmentItem(apartmentResponse.getAddress()));
        });

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        apartmentsAdapter = new ApartmentsAdapter(apartments, this);
        apartmentsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(apartmentsAdapter);
    }

    public void creteNewApartmentPopup(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View apartmentPopupView = getLayoutInflater().inflate(R.layout.create_apartment_popup, null);
        editTextAddress = (EditText) apartmentPopupView.findViewById(R.id.edit_text_address);
        saveButton = (Button) apartmentPopupView.findViewById(R.id.save_button);
        cancelButton = (Button) apartmentPopupView.findViewById(R.id.cancel_button);

        dialogBuilder.setView(apartmentPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createApartment(editTextAddress.getText().toString());
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void createApartment(String address){
        Call<ApartmentResponse> apartmentResponseCall = ApiClient.getApiService().createApartment(new ApartmentRequestBody(address));
        apartmentResponseCall.enqueue(new Callback<ApartmentResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Call<ApartmentResponse> call, @NotNull Response<ApartmentResponse> response) {
                if(response.isSuccessful()){
                    ApartmentResponse apartmentResponse = response.body();
                    apartmentListResponse.getApartmentResponseList().add(apartmentResponse);
                    apartments.add(new ApartmentItem(apartmentResponse.getAddress()));
                    initRecyclerView();
                }
                else{
                    String message = "An error occurred please try again";
                    Toast.makeText(ApartmentsActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ApartmentResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ApartmentsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ApartmentsActivity.this, PulpitActivity.class));
        finish();
    }

    @Override
    public void onItemCLick(int position) {
        getTenants(apartmentListResponse.getApartmentResponseList().get(position).getId(), apartmentListResponse.getApartmentResponseList().get(position).getAddress());
    }

    public void getTenants(Integer apartmentId, String address){
        Call<List<TenantResponse>> tenantsResponseCall = ApiClient.getApiService().getTenants(apartmentId);
        tenantsResponseCall.enqueue(new Callback<List<TenantResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<TenantResponse>> call, @NotNull Response<List<TenantResponse>> response) {
                if(response.isSuccessful()){
                    List<TenantResponse> tenantResponseList = response.body();
                    startActivity(new Intent(ApartmentsActivity.this, ApartmentActivity.class)
                            .putExtra("data", new TenantListResponse(tenantResponseList))
                            .putExtra("address", address));
                    //finish();
                }
                else{
                    String message = "An error occurred please try again";
                    Toast.makeText(ApartmentsActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<TenantResponse>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ApartmentsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}