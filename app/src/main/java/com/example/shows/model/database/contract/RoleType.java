package com.example.shows.model.database.contract;

import android.content.Intent;

public enum RoleType {

    ROLE_ADMIN(1, "ROLE_ADMIN"),
    ROLE_COURIER(2, "ROLE_USER");

    private final Integer id;
    private final String formattedName;

    RoleType(Integer id, String formattedName) {
            this.id = id;
            this.formattedName = formattedName;
    }

    public Integer getId() {
        return id;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
