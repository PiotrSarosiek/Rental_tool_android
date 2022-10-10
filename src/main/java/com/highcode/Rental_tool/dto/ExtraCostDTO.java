package com.highcode.Rental_tool.dto;

import com.highcode.Rental_tool.persistance.entity.ExtraCost;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class ExtraCostDTO {

    Long id;

    @NotNull
    String name;

    @NotNull
    Double amount;

    public static ExtraCostDTO from(ExtraCost extraCost){
        return ExtraCostDTO.builder()
                .id(extraCost.getId())
                .name(extraCost.getName())
                .amount(extraCost.getAmount())
                .build();
    }
}
