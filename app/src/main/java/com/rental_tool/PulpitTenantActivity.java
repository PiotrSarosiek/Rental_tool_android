package com.rental_tool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rental_tool.api.ApiClient;
import com.rental_tool.dto.apartment.ApartmentListResponse;
import com.rental_tool.dto.apartment.ApartmentResponse;
import com.rental_tool.dto.login.LoginResponse;
import com.rental_tool.dto.tenantInvitation.TenantInvitationListResponse;
import com.rental_tool.dto.tenantInvitation.TenantInvitationResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PulpitTenantActivity extends AppCompatActivity {

    LoginResponse loginResponse;

    TextView editTextUsername;
    ImageView imageViewApartment;
    ImageView imageViewInvitations;
    ImageView imageViewSettings;
    ImageView imageViewExit;

    SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulpit_tenant);

        editTextUsername = findViewById(R.id.user_name);
        imageViewApartment = findViewById(R.id.apartment_icon);
        imageViewInvitations = findViewById(R.id.invitations_icon);
        imageViewSettings = findViewById(R.id.tenant_settings_icon);
        imageViewExit = findViewById(R.id.exit_icon);

        sharedPreferences = getSharedPreferences("UserInfo", 0);

        editTextUsername.setText(sharedPreferences.getString("userName", "userName")+" "+sharedPreferences.getString("userSurname", "userSurname"));

        imageViewApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApartmentByTenantAssignedToUser();
            }
        });

        imageViewInvitations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInvitations();
            }
        });

        imageViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imageViewExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
                startActivity(new Intent(PulpitTenantActivity.this, MainActivity.class));
            }
        });
    }

    public void logoutUser(){
        ApiClient.getApiService().logoutUser();
    }


    public void getInvitations(){
        Call<List<TenantInvitationResponse>> invitationsResponseCall = ApiClient.getApiService().getUserInvitations();
        invitationsResponseCall.enqueue(new Callback<List<TenantInvitationResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<TenantInvitationResponse>> call, @NotNull Response<List<TenantInvitationResponse>> response) {
                if(response.isSuccessful()){
                    List<TenantInvitationResponse> tenantInvitationResponseList = response.body();
                    startActivity(new Intent(PulpitTenantActivity.this, InvitationsActivity.class).putExtra("data", new TenantInvitationListResponse(tenantInvitationResponseList)));
                    finish();
                }
                else{
                    String message = "An error occurred please try again";
                    Toast.makeText(PulpitTenantActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<TenantInvitationResponse>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PulpitTenantActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getApartmentByTenantAssignedToUser(){
        Call<ApartmentResponse> apartmentResponseCall = ApiClient.getApiService().getApartmentByTenantAssignedToUser();
        apartmentResponseCall.enqueue(new Callback<ApartmentResponse>() {
            @Override
            public void onResponse(Call<ApartmentResponse> call, Response<ApartmentResponse> response) {
                if(response.isSuccessful()){
                    ApartmentResponse apartmentResponse = response.body();
                    startActivity(new Intent(PulpitTenantActivity.this, ApartmentTenantActivity.class).putExtra("data", apartmentResponse));
                    finish();
                }
                else{
                    String message = "User not assigned to any apartment";
                    Toast.makeText(PulpitTenantActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApartmentResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PulpitTenantActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
