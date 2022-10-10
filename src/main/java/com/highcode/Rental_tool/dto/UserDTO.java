package com.highcode.Rental_tool.dto;

import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.persistance.enumerated.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 15)
    private String name;

    @NotNull
    @Size(min = 3, max = 15)
    private String surname;

    @NotNull
    @Size(min = 6, max = 15)
    private String phone;

    @NotNull
    @Size(min = 4)
    private String email;

    @NotNull
    private UserRole role;

    public static UserDTO from(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
