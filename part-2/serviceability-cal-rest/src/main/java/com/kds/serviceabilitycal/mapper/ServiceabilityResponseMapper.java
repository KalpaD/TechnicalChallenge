package com.kds.serviceabilitycal.mapper;

import com.kds.serviceabilitycal.model.Error;
import com.kds.serviceabilitycal.model.ServiceabilityResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceabilityResponseMapper {

    private ServiceabilityResponseMapper () {

    }

    public static ServiceabilityResponse fromServiceability(BigDecimal serviceability) {
        ServiceabilityResponse serviceabilityResponse = new ServiceabilityResponse();
        serviceabilityResponse.setServiceability(serviceability);
        return serviceabilityResponse;
    }

    public static ServiceabilityResponse fromWebExchangeBindException(WebExchangeBindException ex) {
        ServiceabilityResponse serviceabilityResponse = new ServiceabilityResponse();
        List<Error> errors = ex.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .map(message -> new Error("INVALID_REQUEST", message))
                .collect(Collectors.toList());
        serviceabilityResponse.setErrors(errors);
        return serviceabilityResponse;
    }

    public static ServiceabilityResponse fromServerWebInputException(ServerWebInputException ex) {
        ServiceabilityResponse serviceabilityResponse = new ServiceabilityResponse();
        Error error = new Error("INVALID_REQUEST", "Invalid request parameter detected, Please send a valid loan application");
        serviceabilityResponse.setErrors(List.of(error));
        return serviceabilityResponse;
    }

    public static ServiceabilityResponse fromGenericError(Throwable throwable) {
        ServiceabilityResponse serviceabilityResponse = new ServiceabilityResponse();
        Error error = new Error("ERROR", "Error while serviceability calculation");
        serviceabilityResponse.setErrors(List.of(error));
        return serviceabilityResponse;
    }

}
