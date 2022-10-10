package com.highcode.Rental_tool.persistance.entity;

import com.highcode.Rental_tool.dto.CreateApartmentDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "apartment")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@SequenceGenerator(initialValue = 2, name = "id_gen", sequenceName = "apartment_seq")
public class Apartment extends AbstractEntity{

    @Column(name = "address")
    String address;

    @ManyToOne
    @JoinColumn(name = "landlord_id", nullable = false)
    User landlord;

    @OneToMany(mappedBy = "apartment", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Tenant> tenants = new HashSet<>();

    @OneToOne(mappedBy = "apartment", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    StableBill stableBill;

    public static Apartment from(CreateApartmentDTO createApartmentDTO, User landlord){
        return Apartment.builder()
                .address(createApartmentDTO.getAddress())
                .landlord(landlord)
                .tenants(new HashSet<>())
                .build();
    }



}
