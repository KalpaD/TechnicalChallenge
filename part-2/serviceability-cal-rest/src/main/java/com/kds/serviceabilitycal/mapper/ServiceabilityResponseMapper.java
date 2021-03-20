package com.kds.serviceabilitycal.mapper;

import com.kds.serviceabilitycal.model.Error;
import com.kds.serviceabilitycal.model.ServiceabilityResponse;

import java.math.BigDecimal;
import java.util.List;

public class ServiceabilityResponseMapper {

    private ServiceabilityResponseMapper () {

    }

    public static ServiceabilityResponse fromServiceability(BigDecimal serviceability) {
        ServiceabilityResponse serviceabilityResponse = new ServiceabilityResponse();
        serviceabilityResponse.setServiceability(serviceability);
        return serviceabilityResponse;
    }

    public static ServiceabilityResponse fromGenericError(Throwable throwable) {
        ServiceabilityResponse serviceabilityResponse = new ServiceabilityResponse();
        Error error = new Error("ERROR", "Error while serviceability calculation");
        serviceabilityResponse.setErrors(List.of(error));
        return serviceabilityResponse;
    }

}
