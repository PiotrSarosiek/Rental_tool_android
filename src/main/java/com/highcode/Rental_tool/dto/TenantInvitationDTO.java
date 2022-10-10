package com.highcode.Rental_tool.dto;

import com.highcode.Rental_tool.persistance.entity.TenantInvitation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class TenantInvitationDTO {

    Long id;

    UserDTO user;

    UserDTO landlord;

    TenantDTO tenant;

    Boolean accepted;

    public static TenantInvitationDTO from(TenantInvitation tenantInvitation){
        return TenantInvitationDTO.builder()
                .id(tenantInvitation.getId())
                .user(UserDTO.from(tenantInvitation.getUser()))
                .landlord(UserDTO.from(tenantInvitation.getLandlord()))
                .tenant(TenantDTO.from(tenantInvitation.getTenant()))
                .accepted(tenantInvitation.getAccepted())
                .build();
    }
}
