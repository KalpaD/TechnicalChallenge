package com.kds.serviceabilitycal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kds.serviceabilitycal.model.Application;
import com.kds.serviceabilitycal.model.ServiceabilityResponse;
import com.kds.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class ServiceabilityControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceabilityControllerTest.class);
    private static final String BASE_URL = "http://localhost:5000";
    private static final String SERVICEABILITY_URL = BASE_URL.concat("/api/v1/serviceability/calculate");

    @Autowired
    private WebTestClient webTestClient;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldRespond200OKWith_calculateFactorBasedServiceability_WhenFactorIs_1() throws JsonProcessingException {
        Application application = objectMapper.readValue(FileUtil.getStringFromFile("application.json"), Application.class);
        Flux<ServiceabilityResponse> responseFlux = webTestClient
                .post()
                .uri(UriComponentsBuilder.fromUriString(SERVICEABILITY_URL).build().toUri())
                .bodyValue(application)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ServiceabilityResponse.class)
                .getResponseBody();

        StepVerifier.create(responseFlux)
                .expectSubscription()
                .assertNext(response -> assertEquals(BigDecimal.valueOf(1187.00), response.getServiceability()))
                .verifyComplete();
    }

    @Test
    void shouldRespond400BadRequest_WhenIncomeListIsEmpty() throws JsonProcessingException {
        Application application = objectMapper.readValue(FileUtil.getStringFromFile("application-without-income-list.json"), Application.class);
        Flux<ServiceabilityResponse> responseFlux = webTestClient
                .post()
                .uri(UriComponentsBuilder.fromUriString(SERVICEABILITY_URL).build().toUri())
                .bodyValue(application)
                .exchange()
                .expectStatus().isBadRequest()
                .returnResult(ServiceabilityResponse.class)
                .getResponseBody();

        StepVerifier.create(responseFlux)
                .expectSubscription()
                .assertNext(response -> {
                    LOGGER.info("Response : {}", response);
                    assertTrue(response.getErrors()
                            .stream()
                            .anyMatch(error -> error.getCode().equals("INVALID_REQUEST")
                                    && error.getMessage().equals("incomes list must contain at least one item")));
                })
                .verifyComplete();
    }

    @Test
    void shouldRespond400BadRequest_WhenExpensesListIsEmpty() throws JsonProcessingException {
        Application application = objectMapper.readValue(FileUtil.getStringFromFile("application-without-expenses-list.json"), Application.class);
        Flux<ServiceabilityResponse> responseFlux = webTestClient
                .post()
                .uri(UriComponentsBuilder.fromUriString(SERVICEABILITY_URL).build().toUri())
                .bodyValue(application)
                .exchange()
                .expectStatus().isBadRequest()
                .returnResult(ServiceabilityResponse.class)
                .getResponseBody();

        StepVerifier.create(responseFlux)
                .expectSubscription()
                .assertNext(response -> {
                    LOGGER.info("Response : {}", response);
                    assertTrue(response.getErrors()
                            .stream()
                            .anyMatch(error -> error.getCode().equals("INVALID_REQUEST")
                                    && error.getMessage().equals("expenses list must contain at least one item")));
                })
                .verifyComplete();
    }


    @Test
    void shouldRespond400BadRequest_WhenMalformedApplicationDetected() throws JsonProcessingException {
        JsonNode application = objectMapper.readValue(FileUtil.getStringFromFile("application-with-malformed-frequency.json"), JsonNode.class);
        Flux<ServiceabilityResponse> responseFlux = webTestClient
                .post()
                .uri(UriComponentsBuilder.fromUriString(SERVICEABILITY_URL).build().toUri())
                .bodyValue(application)
                .exchange()
                .expectStatus().isBadRequest()
                .returnResult(ServiceabilityResponse.class)
                .getResponseBody();

        StepVerifier.create(responseFlux)
                .expectSubscription()
                .assertNext(response -> {
                    LOGGER.info("Response : {}", response);
                    assertTrue(response.getErrors()
                            .stream()
                            .anyMatch(error -> error.getCode().equals("INVALID_REQUEST")
                                    && error.getMessage().equals("Invalid request parameter detected, Please send a valid loan application")));
                })
                .verifyComplete();
    }

    @Test
    void shouldRespond400BadRequest_WhenValueAttributeIsMissingInApplication() throws JsonProcessingException {
        Application application = objectMapper.readValue(FileUtil.getStringFromFile("application-with-value-missing.json"), Application.class);
        Flux<ServiceabilityResponse> responseFlux = webTestClient
                .post()
                .uri(UriComponentsBuilder.fromUriString(SERVICEABILITY_URL).build().toUri())
                .bodyValue(application)
                .exchange()
                .expectStatus().isBadRequest()
                .returnResult(ServiceabilityResponse.class)
                .getResponseBody();

        StepVerifier.create(responseFlux)
                .expectSubscription()
                .assertNext(response -> {
                    LOGGER.info("Response : {}", response);
                    assertTrue(response.getErrors()
                            .stream()
                            .anyMatch(error -> error.getCode().equals("INVALID_REQUEST")
                                    && error.getMessage().equals("value cannot be null")));
                })
                .verifyComplete();
    }

}