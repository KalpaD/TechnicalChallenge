package com.kds.serviceabilitycal.handler;

import com.kds.serviceabilitycal.mapper.ServiceabilityResponseMapper;
import com.kds.serviceabilitycal.model.ServiceabilityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.function.Function;

public class ErrorHandler {

    public static final Function<WebExchangeBindException, ResponseEntity<ServiceabilityResponse>> handleWebExchangeEx =
            ex -> ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ServiceabilityResponseMapper.fromWebExchangeBindException(ex));


    public static final Function<ServerWebInputException, ResponseEntity<ServiceabilityResponse>> handleServerWebInputEx =
            ex -> ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ServiceabilityResponseMapper.fromServerWebInputException(ex));

    public static final Function<Throwable, ResponseEntity<ServiceabilityResponse>> handleGenericError =
            error -> ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ServiceabilityResponseMapper.fromGenericError(error));
}
