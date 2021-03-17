package com.kds.serviceabilitycal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Item {

    @JsonProperty("description")
    private String description;
    @JsonProperty("frequency")
    private Frequency frequency;
    @JsonProperty("value")
    private BigDecimal value;

    public Item() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
