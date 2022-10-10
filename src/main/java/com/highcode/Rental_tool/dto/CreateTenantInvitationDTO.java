package com.highcode.Rental_tool.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class CreateTenantInvitationDTO {

    String email;

    Long landlordId;

    Long tenantId;

}
