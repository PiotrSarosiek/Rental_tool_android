package com.highcode.Rental_tool.persistance.enumerated;

public enum UserRole {
    ADMIN("ADMIN"),
    LANDLORD("LANDLORD"),
    TENANT("TENANT");

    private final String roleName;

    UserRole(String role) {
        roleName = role;
    }

    public String toString() {
        return this.roleName;
    }
}
