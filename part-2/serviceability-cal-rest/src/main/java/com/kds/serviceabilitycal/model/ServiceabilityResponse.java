package com.kds.serviceabilitycal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceabilityResponse {

    @JsonProperty("serviceability")
    private BigDecimal serviceability;

    private List<Error> errors;

    public BigDecimal getServiceability() {
        return serviceability;
    }

    public void setServiceability(BigDecimal serviceability) {
        this.serviceability = serviceability;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
