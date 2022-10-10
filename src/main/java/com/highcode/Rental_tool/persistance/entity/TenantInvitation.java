package com.highcode.Rental_tool.persistance.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "tenant_invitation")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@SequenceGenerator(initialValue = 2, name = "id_gen", sequenceName = "tenant_invitation_seq")
public class TenantInvitation extends AbstractEntity {

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "landlord_id")
    User landlord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_tenant_id"))
    Tenant tenant;

    @Column(name = "accepted")
    Boolean accepted;

    public static TenantInvitation from(User user, User landlord, Tenant tenant){
        return TenantInvitation.builder()
                .user(user)
                .landlord(landlord)
                .tenant(tenant)
                .build();
    }

}
