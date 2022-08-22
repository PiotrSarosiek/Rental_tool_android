package com.rental_tool.dto.tenantInvitation;

import com.rental_tool.dto.tenant.TenantResponse;

import java.io.Serializable;
import java.util.List;

public class TenantInvitationListResponse implements Serializable {

    private List<TenantInvitationResponse> tenantInvitationResponseList;

    public List<TenantInvitationResponse> getTenantInvitationResponseList() {
        return tenantInvitationResponseList;
    }

    public void setTenantInvitationResponseList(List<TenantInvitationResponse> tenantInvitationResponseList) {
        this.tenantInvitationResponseList = tenantInvitationResponseList;
    }

    public TenantInvitationListResponse(List<TenantInvitationResponse> tenantInvitationResponseList) {
        this.tenantInvitationResponseList = tenantInvitationResponseList;
    }
}
