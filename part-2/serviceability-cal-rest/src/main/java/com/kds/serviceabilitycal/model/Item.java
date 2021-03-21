package com.kds.serviceabilitycal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Item {

    @JsonProperty("description")
    @NotBlank(message = "description cannot be empty or null")
    private String description;

    @JsonProperty("frequency")
    @NotNull(message = "frequency cannot null")
    private Frequency frequency;

    @JsonProperty("value")
    @NotNull(message = "value cannot be null")
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
