package com.rental_tool.dto.apartment;

public class ApartmentRequest {

    private String address;

    public ApartmentRequest(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
