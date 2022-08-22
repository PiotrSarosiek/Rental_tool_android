package com.rental_tool.dto.apartment;

import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rental_tool.dto.stableBill.StableBillResponse;
import com.rental_tool.dto.tenant.TenantResponse;
import com.rental_tool.dto.unstableBill.UnstableBillResponse;
import com.rental_tool.dto.user.UserResponse;

import java.io.Serializable;
import java.util.List;

public class ApartmentResponse implements Serializable{

    private long id;
    private String creationDate;
    private String modificationDate;
    private String address;
    private UserResponse landlord;
    private StableBillResponse stableBill;
    private List<UnstableBillResponse> unstableBills;
    private List<TenantResponse> tenants;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserResponse getLandlord() {
        return landlord;
    }

    public void setLandlord(UserResponse landlord) {
        this.landlord = landlord;
    }

    public StableBillResponse getStableBill() {
        return stableBill;
    }

    public void setStableBill(StableBillResponse stableBill) {
        this.stableBill = stableBill;
    }

    public List<UnstableBillResponse> getUnstableBills() {
        return unstableBills;
    }

    public void setUnstableBills(List<UnstableBillResponse> unstableBills) {
        this.unstableBills = unstableBills;
    }

    public List<TenantResponse> getTenants() {
        return tenants;
    }

    public void setTenants(List<TenantResponse> tenants) {
        this.tenants = tenants;
    }
}
