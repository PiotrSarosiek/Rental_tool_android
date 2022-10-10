package com.highcode.Rental_tool.dto;

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
public class UserRegisterDTO {

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
    @Size(min = 6, max = 15)
    private String password;

    @NotNull
    private UserRole role;

}
