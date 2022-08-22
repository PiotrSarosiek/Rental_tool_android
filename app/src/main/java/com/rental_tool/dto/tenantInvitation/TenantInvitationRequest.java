package com.rental_tool.dto.tenantInvitation;

public class TenantInvitationRequest {

    private String email;
    private long landlordId;
    private long tenantId;

    public TenantInvitationRequest(String email, long landlordId, long tenantId) {
        this.email = email;
        this.landlordId = landlordId;
        this.tenantId = tenantId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(long landlordId) {
        this.landlordId = landlordId;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }
}
