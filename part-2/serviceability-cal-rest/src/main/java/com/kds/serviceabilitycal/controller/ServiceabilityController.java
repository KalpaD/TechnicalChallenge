package com.kds.serviceabilitycal.controller;

import com.kds.serviceabilitycal.calculator.ServiceabilityCalculator;
import com.kds.serviceabilitycal.handler.ErrorHandler;
import com.kds.serviceabilitycal.mapper.ServiceabilityResponseMapper;
import com.kds.serviceabilitycal.model.Application;
import com.kds.serviceabilitycal.model.ServiceabilityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/serviceability")
public class ServiceabilityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceabilityController.class);
    private ServiceabilityCalculator serviceabilityCalculator;

    @Autowired
    public ServiceabilityController(ServiceabilityCalculator serviceabilityCalculator) {
        this.serviceabilityCalculator = serviceabilityCalculator;
    }

    @PostMapping(value = "/calculate")
    public Mono<ResponseEntity<ServiceabilityResponse>> calculate(@RequestBody @Valid Mono<Application> application) {
        return serviceabilityCalculator.calculate(application)
                .map(serviceability -> ResponseEntity.status(HttpStatus.OK).body(ServiceabilityResponseMapper.fromServiceability(serviceability)))
                .doOnError(error -> LOGGER.error("Error while serviceability calculation", error))
                .onErrorResume(WebExchangeBindException.class, ex -> Mono.just(ErrorHandler.handleWebExchangeEx.apply(ex)))
                .onErrorResume(ServerWebInputException.class, ex -> Mono.just(ErrorHandler.handleServerWebInputEx.apply(ex)))
                .onErrorResume(error -> Mono.just(ErrorHandler.handleGenericError.apply(error)));
    }
}
