package com.highcode.Rental_tool.persistance.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stable_bill")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@SequenceGenerator(initialValue = 2, name = "id_gen", sequenceName = "stable_bill_seq")
public class StableBill extends AbstractEntity{

    @Column
    Double administrativeRent;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="stableBill")
    Set<ExtraCost> extraCosts = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "apartment_id")
    Apartment apartment;

}
