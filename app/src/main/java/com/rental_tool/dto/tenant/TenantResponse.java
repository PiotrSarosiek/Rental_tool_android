package com.rental_tool.dto.tenant;

import com.rental_tool.dto.apartment.ApartmentResponse;

import com.rental_tool.dto.extraCosts.ExtraCostResponse;
import com.rental_tool.dto.user.UserResponse;

import java.io.Serializable;
import java.util.List;

public class TenantResponse implements Serializable {

    private long id;
    private String creationDate;
    private String modificationDate;
    private UserResponse user;
    private ApartmentResponse apartment;
    private double rent;
    private List<ExtraCostResponse> extraCosts;

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

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public ApartmentResponse getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentResponse apartment) {
        this.apartment = apartment;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public List<ExtraCostResponse> getExtraCosts() {
        return extraCosts;
    }

    public void setExtraCosts(List<ExtraCostResponse> extraCosts) {
        this.extraCosts = extraCosts;
    }
}
