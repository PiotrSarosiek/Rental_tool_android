package com.highcode.Rental_tool.persistance.entity;

import com.highcode.Rental_tool.dto.TenantDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tenant")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@SequenceGenerator(initialValue = 2, name = "id_gen", sequenceName = "tenant_seq")
public class Tenant extends AbstractEntity{

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="fk_apartment_id"))
    Apartment apartment;

    @Column
    Double rent;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="tenant", orphanRemoval = true)
    Set<ExtraCost> extraCosts = new HashSet<>();

    public static Tenant from(TenantDTO tenantDTO, Apartment apartment){
        Tenant tenant = Tenant.builder()
                .user(null)
                .apartment(apartment)
                .rent(tenantDTO.getRent())
                .extraCosts(tenantDTO.getExtraCosts().stream().map(ExtraCost::from).collect(Collectors.toSet()))
                .build();
        tenant.getExtraCosts().forEach(extraCost -> extraCost.setTenant(tenant));
        return tenant;
    }

}
