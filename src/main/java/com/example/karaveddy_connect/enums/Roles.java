package com.example.karaveddy_connect.enums;


import java.util.EnumSet;

public enum Roles{
            SUPPER_ADMIN,
            ADMIN,
            SUPPER_USER,
            PUBLIC;


    public static boolean isValidRole(Roles role) {
        return role != null && EnumSet.allOf(Roles.class).contains(role);
    }
}