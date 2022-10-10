package com.highcode.Rental_tool.dto;

import com.highcode.Rental_tool.persistance.entity.StableBill;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class StableBillDTO {

    Long id;
    @NotNull
    public Double administrativeRent;
    @Valid
    public Set<ExtraCostDTO> extraCosts;

    public static StableBillDTO from(StableBill stableBill){
        return StableBillDTO.builder()
                .id(stableBill.getId())
                .administrativeRent(stableBill.getAdministrativeRent())
                .extraCosts(stableBill.getExtraCosts().stream()
                        .map(ExtraCostDTO::from)
                        .collect(Collectors.toSet()))
                .build();
    }
}
