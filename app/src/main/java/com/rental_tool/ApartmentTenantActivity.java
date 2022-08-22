package com.rental_tool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.rental_tool.dto.apartment.ApartmentResponse;
import com.rental_tool.dto.extraCosts.ExtraCostDTO;
import com.rental_tool.dto.tenant.TenantListResponse;
import com.rental_tool.dto.tenant.TenantRequest;
import com.rental_tool.dto.tenant.TenantResponse;
import com.rental_tool.dto.tenantInvitation.TenantInvitationRequest;
import com.rental_tool.dto.tenantInvitation.TenantInvitationResponse;
import com.rental_tool.utils.UIUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApartmentTenantActivity extends AppCompatActivity {

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;

    TextView apartmentAddress;
    TextView rentValue;
    ListView listViewExtraCosts;
    TextView administrativeRentValue;
    ListView listViewExtraCostsStable;
    LinearLayout linearLayout;
    TextView checkSumValue;

    ApartmentResponse apartmentResponse;

    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_tenant);

        apartmentAddress = findViewById(R.id.apartment_address);
        rentValue = findViewById(R.id.rent_value);
        listViewExtraCosts = findViewById(R.id.list_view_extra_costs);
        administrativeRentValue = findViewById(R.id.administrative_rent_value);
        listViewExtraCostsStable = findViewById(R.id.list_view_extra_costs_stable);
        linearLayout = findViewById(R.id.linear_layout_check_landlord_details);
        checkSumValue = findViewById(R.id.check_sum_value);

        sharedPreferences = getSharedPreferences("UserInfo", 0);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            apartmentResponse = (ApartmentResponse) intent.getSerializableExtra("data");
            Log.e("TAG", apartmentResponse.toString());
            initData();
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {
        apartmentAddress.setText(apartmentResponse.getAddress());
        rentValue.setText(String.valueOf(apartmentResponse.getTenants().get(0).getRent()));

        List<String> tenantExtraCosts = new ArrayList<>();
        apartmentResponse.getTenants().get(0).getExtraCosts().forEach(extraCostResponse -> tenantExtraCosts.add(extraCostResponse.getName()+": "+extraCostResponse.getAmount()));
        listViewExtraCosts.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, tenantExtraCosts));
        UIUtils.setListViewHeightBasedOnItems(listViewExtraCosts);

        administrativeRentValue.setText(apartmentResponse.getStableBill().getAdministrativeRent().toString());
        List<String> extraCosts = new ArrayList<>();
        apartmentResponse.getStableBill().getExtraCosts().forEach(extraCostResponse -> extraCosts.add(extraCostResponse.getName()+": "+extraCostResponse.getAmount()));
        listViewExtraCostsStable.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, extraCosts));
        UIUtils.setListViewHeightBasedOnItems(listViewExtraCostsStable);

        checkSumValue.setText(String.valueOf(calculateMonthlySum()));
    }

    private double calculateMonthlySum(){
        double sum = 0.0;

        sum += apartmentResponse.getStableBill().getAdministrativeRent();

        for (int i=0;i<apartmentResponse.getStableBill().getExtraCosts().size();i++){
            sum+=apartmentResponse.getStableBill().getExtraCosts().get(i).getAmount();
        }

        sum += apartmentResponse.getTenants().get(0).getRent();

        for (int i=0;i<apartmentResponse.getTenants().get(0).getExtraCosts().size();i++){
            sum+=apartmentResponse.getTenants().get(0).getExtraCosts().get(i).getAmount();
        }

        return sum;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ApartmentTenantActivity.this, PulpitTenantActivity.class));
        finish();
    }

}
