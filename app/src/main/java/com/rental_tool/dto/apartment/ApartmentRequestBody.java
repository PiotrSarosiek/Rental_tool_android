package com.rental_tool.dto.apartment;

public class ApartmentRequestBody {

    private String address;

    public ApartmentRequestBody(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
