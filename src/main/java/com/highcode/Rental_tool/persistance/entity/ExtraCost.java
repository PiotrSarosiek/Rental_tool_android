package com.highcode.Rental_tool.persistance.entity;

import com.highcode.Rental_tool.dto.ExtraCostDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "extra_cost")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@SequenceGenerator(initialValue = 2, name = "id_gen", sequenceName = "extra_cost_seq")
public class ExtraCost extends AbstractEntity{

    @Column
    String name;

    @Column
    Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    StableBill stableBill;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn()
    Tenant tenant;

    public static ExtraCost from(ExtraCostDTO extraCostDTO){
        return ExtraCost.builder()
                .name(extraCostDTO.getName())
                .amount(extraCostDTO.getAmount())
                .build();
    }

}
