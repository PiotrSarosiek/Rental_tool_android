package com.rental_tool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rental_tool.api.ApiClient;
import com.rental_tool.dto.login.LoginRequest;
import com.rental_tool.dto.login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        sharedPreferences = getSharedPreferences("UserInfo", 0);
    }

    @Override
    public void onBackPressed() {
        String userRole = sharedPreferences.getString("userRole", "userRole");
        switch (userRole){
            case "LANDLORD":
                startActivity(new Intent(UserSettingsActivity.this, PulpitActivity.class));
                break;
            case "TENANT":
                startActivity(new Intent(UserSettingsActivity.this, PulpitTenantActivity.class));
                break;
        }
        finish();
    }
}
