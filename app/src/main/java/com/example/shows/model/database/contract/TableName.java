package com.example.shows.model.database.contract;

public enum TableName {
    USER("user"),
    BOOKING("booking"),
    ACTOR("actor"),
    SCENARIST("scenarist"),
    ROLE("role"),
    GENER("gener"),
    PERFORMANCE("performance");

    private final String name;

    TableName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}