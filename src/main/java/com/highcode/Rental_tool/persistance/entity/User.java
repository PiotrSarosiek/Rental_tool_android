package com.highcode.Rental_tool.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.highcode.Rental_tool.dto.UserRegisterDTO;
import com.highcode.Rental_tool.persistance.enumerated.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@SequenceGenerator(initialValue = 2, name = "id_gen", sequenceName = "user_seq")
public class User extends AbstractEntity {

    @Column(unique = true, nullable = false)
    @Email
    String email;

    @Column(name = "password_hash")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String passwordHash;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "surname", nullable = false)
    String surname;

    @Column(name = "phone")
    String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    UserRole role;

    @Column(name = "enabled", nullable = false, columnDefinition="boolean default 'false'")
    boolean enabled;

    @Column(name = "last_usage_date")
    OffsetDateTime lastUsageDate;

    @JsonIgnore
    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL)
    List<Apartment> apartments = new LinkedList<>();

    public static User from(UserRegisterDTO userRegisterDTO, PasswordEncoder passwordEncoder){
        return User.builder()
                .email(userRegisterDTO.getEmail())
                .passwordHash(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .name(userRegisterDTO.getName())
                .surname(userRegisterDTO.getSurname())
                .phone(userRegisterDTO.getPhone())
                .role(userRegisterDTO.getRole())
                .enabled(true)
                .build();
    }


}
