package com.rental_tool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rental_tool.api.ApiClient;
import com.rental_tool.dto.register.RegisterRequest;
import com.rental_tool.dto.user.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentsActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        button = findViewById(R.id.generate_summary);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateSummary();
            }
        });

    }

    public void generateSummary(){
        Call<Void> generateSummaryCall = ApiClient.getApiService().generateSummary();
        generateSummaryCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    String message = "Summary successfully send to your mailbox";
                    Toast.makeText(DocumentsActivity.this, message, Toast.LENGTH_LONG).show();
                }
                else{
                    String message = "An error occurred please try again";
                    Toast.makeText(DocumentsActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(DocumentsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}
