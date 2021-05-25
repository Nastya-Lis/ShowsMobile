package com.example.shows.model.database.contract;

public class DbTriggers {

    public static String getInsertRole(RoleType role) {
        return "insert into role (id, name) values (" + role.getId() + ", '" + role.name() + "')";
    }
}
