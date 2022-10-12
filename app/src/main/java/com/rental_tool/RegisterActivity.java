package com.rental_tool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rental_tool.api.ApiClient;
import com.rental_tool.dto.login.LoginRequest;
import com.rental_tool.dto.login.LoginResponse;
import com.rental_tool.dto.register.RegisterRequest;
import com.rental_tool.dto.user.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Spinner dropdown;
    EditText editTextEmail;
    EditText editTextName;
    EditText editTextSurname;
    EditText editTextPhone;
    EditText editTextPassword;
    EditText editTextPassword2;
    Button buttonConfirm;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"TENANT", "LANDLORD"};
        editTextEmail = findViewById(R.id.emaill);
        editTextName = findViewById(R.id.namee);
        editTextSurname = findViewById(R.id.surnamee);
        editTextPhone = findViewById(R.id.phonee);
        editTextPassword = findViewById(R.id.passwordd);
        editTextPassword2 = findViewById(R.id.confirm_passwordd);
        buttonConfirm = findViewById(R.id.register);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        sharedPreferences = getSharedPreferences("UserInfo", 0);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editTextEmail.getText().toString()) || TextUtils.isEmpty(editTextPassword.getText().toString())){
                    String message = "All input required";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }
                else {
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setEmail(editTextEmail.getText().toString());
                    registerRequest.setName(editTextName.getText().toString());
                    registerRequest.setSurname(editTextSurname.getText().toString());
                    registerRequest.setPhone(editTextPhone.getText().toString());
                    registerRequest.setPassword(editTextPassword.getText().toString());
                    registerRequest.setRole(adapter.getItem(dropdown.getSelectedItemPosition()));
                    registerUser(registerRequest);

                }
            }
        });
    }

    public void registerUser(RegisterRequest registerRequest){
        Call<UserResponse> registerResponseCall = ApiClient.getApiService().register(registerRequest);
        registerResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setUsername(editTextEmail.getText().toString());
                    loginRequest.setPassword(editTextPassword.getText().toString());
                    loginUser(loginRequest);
                }
                else{
                    String message = "An error occurred please try again";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
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
                        switch (loginResponse.getRole()){
                            case "LANDLORD":
                                startActivity(new Intent(RegisterActivity.this, PulpitActivity.class));
                                break;
                            case "TENANT":
                                startActivity(new Intent(RegisterActivity.this, PulpitTenantActivity.class));
                                break;
                        }
                        finish();
                    }
                }
                else{
                    String message = "An error occurred please try again";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
