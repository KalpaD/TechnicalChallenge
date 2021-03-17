package com.kds.serviceabilitycal.model;

public enum Frequency {

    M("Monthly"),
    Y("Yearly");

    private String description;

    Frequency(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
