package com.rental_tool.dto.tenant;

import com.rental_tool.dto.apartment.ApartmentResponse;

import java.io.Serializable;
import java.util.List;

public class TenantListResponse implements Serializable {

    private List<TenantResponse> tenantResponseList;

    public TenantListResponse(List<TenantResponse> tenantResponseList) {
        this.tenantResponseList = tenantResponseList;
    }

    public List<TenantResponse> getTenantResponseList() {
        return tenantResponseList;
    }

    public void setTenantResponseList(List<TenantResponse> tenantResponseList) {
        this.tenantResponseList = tenantResponseList;
    }
}
