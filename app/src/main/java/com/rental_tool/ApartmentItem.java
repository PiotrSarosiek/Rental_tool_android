package com.rental_tool;

public class ApartmentItem {
    private String address;
    private String tenantCount;

    public ApartmentItem(String address, String tenantCount) {
        this.address = address;
        this.tenantCount = tenantCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTenantCount() {
        return tenantCount;
    }

    public void setTenantCount(String tenantCount) {
        this.tenantCount = tenantCount;
    }
}
