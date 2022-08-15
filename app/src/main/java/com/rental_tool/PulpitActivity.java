package com.rental_tool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PulpitActivity extends AppCompatActivity {

    LoginResponse loginResponse;

    TextView editTextUsername;
    ImageView imageViewApartments;
    ImageView imageViewFiles;
    ImageView imageViewMessages;
    ImageView imageViewSettings;
    ImageView imageViewExit;

    SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulpit);

        editTextUsername = findViewById(R.id.user_name);
        imageViewApartments = findViewById(R.id.apartments_icon);
        imageViewFiles = findViewById(R.id.files_icon);
        imageViewMessages = findViewById(R.id.messages_icon);
        imageViewSettings = findViewById(R.id.settings_icon);
        imageViewExit = findViewById(R.id.exit_icon);

        sharedPreferences = getSharedPreferences("UserInfo", 0);

        editTextUsername.setText(sharedPreferences.getString("userName", "userName")+" "+sharedPreferences.getString("userSurname", "userSurname"));

        imageViewApartments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApartments();
            }
        });

        imageViewFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PulpitActivity.this, MainActivity.class).putExtra("data", loginResponse));
            }
        });

        imageViewMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PulpitActivity.this, MainActivity.class).putExtra("data", loginResponse));
            }
        });

        imageViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PulpitActivity.this, MainActivity.class).putExtra("data", loginResponse));
            }
        });

        imageViewExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
                startActivity(new Intent(PulpitActivity.this, MainActivity.class));
            }
        });
    }

    public void logoutUser(){
        ApiClient.getUserService().logoutUser();
    }


    public void getApartments(){
        Call<List<ApartmentResponse>> apartmentsResponseCall = ApiClient.getUserService().getApartments();
        apartmentsResponseCall.enqueue(new Callback<List<ApartmentResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<ApartmentResponse>> call, @NotNull Response<List<ApartmentResponse>> response) {
                if(response.isSuccessful()){
                    List<ApartmentResponse> apartmentResponseList = response.body();
                    startActivity(new Intent(PulpitActivity.this, ApartmentsActivity.class).putExtra("data", new ApartmentListResponse(apartmentResponseList)));
                    finish();
                }
                else{
                    String message = "An error occurred please try again";
                    Toast.makeText(PulpitActivity.this, message, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(@NotNull Call<List<ApartmentResponse>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PulpitActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}
