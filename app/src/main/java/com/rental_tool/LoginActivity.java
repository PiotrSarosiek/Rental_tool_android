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

public class LoginActivity extends AppCompatActivity {
    Button buttonLogin;
    EditText editTextEmail;
    EditText editTextPassword;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.log_in);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        sharedPreferences = getSharedPreferences("UserInfo", 0);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editTextEmail.getText().toString()) || TextUtils.isEmpty(editTextPassword.getText().toString())){
                    String message = "All input required";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                }
                else {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setUsername(editTextEmail.getText().toString());
                    loginRequest.setPassword(editTextPassword.getText().toString());
                    loginUser(loginRequest);
                }
            }
        });
    }

    public void loginUser(LoginRequest loginRequest){
        Call<LoginResponse> loginResponseCall = ApiClient.getApiService().loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong("userId", loginResponse.getId());
                        editor.putString("userEmail", loginResponse.getEmail());
                        editor.putString("userName", loginResponse.getName());
                        editor.putString("userSurname", loginResponse.getSurname());
                        editor.putString("userRole", loginResponse.getRole());
                        editor.apply();
                    }
                    startActivity(new Intent(LoginActivity.this, PulpitActivity.class));
                    finish();
                }
                else{
                    String message = "An error occurred please try again";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
