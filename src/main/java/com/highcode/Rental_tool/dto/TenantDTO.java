package com.highcode.Rental_tool.dto;

import com.highcode.Rental_tool.persistance.entity.Tenant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class TenantDTO {

    Long id;

    @NotNull
    Double rent;

    @Valid
    Set<ExtraCostDTO> extraCosts;

    UserDTO user;

    public static TenantDTO from(Tenant tenant){
        return TenantDTO.builder()
                .id(tenant.getId())
                .rent(tenant.getRent())
                .extraCosts(tenant.getExtraCosts().stream().map(ExtraCostDTO::from).collect(Collectors.toSet()))
                .user(tenant.getUser() != null ? UserDTO.from(tenant.getUser()) : null)
                .build();
    }

}
