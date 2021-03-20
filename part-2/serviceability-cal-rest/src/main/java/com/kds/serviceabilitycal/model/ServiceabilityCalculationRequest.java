package com.kds.serviceabilitycal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceabilityCalculationRequest {

    @JsonProperty("application")
    private Application application;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
