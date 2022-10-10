package com.highcode.Rental_tool.dto;

import com.highcode.Rental_tool.persistance.entity.Apartment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ApartmentDTO {

    Long id;
    String address;
    UserDTO landlord;
    Set<TenantDTO> tenants;
    StableBillDTO stableBill;

    public static ApartmentDTO from(Apartment apartment){
        return ApartmentDTO.builder()
                .id(apartment.getId())
                .address(apartment.getAddress())
                .landlord(UserDTO.from(apartment.getLandlord()))
                .tenants(apartment.getTenants().stream()
                        .map(TenantDTO::from)
                        .collect(Collectors.toSet()))
                .stableBill(StableBillDTO.from(apartment.getStableBill()))
                .build();
    }
}
