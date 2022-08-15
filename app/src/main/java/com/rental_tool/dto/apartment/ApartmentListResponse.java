package com.rental_tool.dto.apartment;

import java.io.Serializable;
import java.util.List;

public class ApartmentListResponse implements Serializable {

    private List<ApartmentResponse> apartmentResponseList;

    public ApartmentListResponse(List<ApartmentResponse> apartmentResponseList) {
        this.apartmentResponseList = apartmentResponseList;
    }

    public List<ApartmentResponse> getApartmentResponseList() {
        return apartmentResponseList;
    }

    public void setApartmentResponseList(List<ApartmentResponse> apartmentResponseList) {
        this.apartmentResponseList = apartmentResponseList;
    }
}
