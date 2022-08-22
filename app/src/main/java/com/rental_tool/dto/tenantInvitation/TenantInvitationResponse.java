package com.rental_tool.dto.tenantInvitation;

import com.rental_tool.dto.tenant.TenantResponse;
import com.rental_tool.dto.user.UserResponse;

public class TenantInvitationResponse {

    private long id;
    private UserResponse user;
    private UserResponse landlord;
    private TenantResponse tenant;
    private Boolean accepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public UserResponse getLandlord() {
        return landlord;
    }

    public void setLandlord(UserResponse landlord) {
        this.landlord = landlord;
    }

    public TenantResponse getTenant() {
        return tenant;
    }

    public void setTenant(TenantResponse tenant) {
        this.tenant = tenant;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
