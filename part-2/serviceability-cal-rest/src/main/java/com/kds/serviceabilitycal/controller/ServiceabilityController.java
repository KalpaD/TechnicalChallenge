package com.kds.serviceabilitycal.controller;

import com.kds.serviceabilitycal.calculator.ServiceabilityCalculator;
import com.kds.serviceabilitycal.mapper.ServiceabilityResponseMapper;
import com.kds.serviceabilitycal.model.Application;
import com.kds.serviceabilitycal.model.ServiceabilityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/serviceability")
public class ServiceabilityController {

    private ServiceabilityCalculator serviceabilityCalculator;

    @Autowired
    public ServiceabilityController(ServiceabilityCalculator serviceabilityCalculator) {
        this.serviceabilityCalculator = serviceabilityCalculator;
    }

    @PostMapping(value = "/calculate")
    public Mono<ResponseEntity<ServiceabilityResponse>> calculate(
            @RequestBody Mono<Application> application) {
        return serviceabilityCalculator.calculate(application)
        .map(serviceability ->
                ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ServiceabilityResponseMapper.fromServiceability(serviceability)))
        .onErrorResume(error -> Mono.just(
                ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ServiceabilityResponseMapper.fromGenericError(error))));

    }
}
