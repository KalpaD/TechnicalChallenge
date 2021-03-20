package com.kds.serviceabilitycal.calculator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kds.serviceabilitycal.exception.ApplicationException;
import com.kds.serviceabilitycal.model.Application;
import com.kds.serviceabilitycal.util.FileUtil;
import com.kds.serviceabilitycal.validator.ApplicationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FactorBasedServiceabilityCalculatorTest {

    private FactorBasedServiceabilityCalculator factorBasedServiceabilityCalculator;
    private ApplicationValidator applicationValidator;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        applicationValidator = new ApplicationValidator();
        // set the factor
        BigDecimal factor = BigDecimal.valueOf(1);
        factorBasedServiceabilityCalculator = new FactorBasedServiceabilityCalculator(factor, applicationValidator);
    }

    @Test
    void shouldCalculateFactorBasedServiceability_WhenFactorIs_2() throws JsonProcessingException {
        // load load application
        String stringFromFile = FileUtil.getStringFromFile("application-with-full-data.json");
        Application application = objectMapper.readValue(stringFromFile, Application.class);

        applicationValidator = new ApplicationValidator();
        // set the factor
        BigDecimal factor = BigDecimal.valueOf(2);
        factorBasedServiceabilityCalculator = new FactorBasedServiceabilityCalculator(factor, applicationValidator);

        Optional<BigDecimal> result = factorBasedServiceabilityCalculator.calculate(application);
        assertTrue(result.isPresent());
        assertEquals(0, BigDecimal.valueOf(2374.00).compareTo(result.get()));
    }

    @Test
    void shouldCalculateFactorBasedServiceability_WhenFactorIs_1() throws JsonProcessingException {
        // load load application
        String stringFromFile = FileUtil.getStringFromFile("application-with-full-data.json");
        Application application = objectMapper.readValue(stringFromFile, Application.class);

        Optional<BigDecimal> result = factorBasedServiceabilityCalculator.calculate(application);
        assertTrue(result.isPresent());
        assertEquals(0, BigDecimal.valueOf(1187.00).compareTo(result.get()));
    }

    @Test
    void shouldThrowException_WhenIncomeListIsEmpty() throws JsonProcessingException {
        // load load application
        String stringFromFile = FileUtil.getStringFromFile("application-without-income-list.json");
        Application application = objectMapper.readValue(stringFromFile, Application.class);

        ApplicationException exception = assertThrows(ApplicationException.class, () -> factorBasedServiceabilityCalculator.calculate(application));
        assertEquals("Invalid application : incomes list must contain at least one item", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenExpensesListIsEmpty() throws JsonProcessingException {
        // load load application
        String stringFromFile = FileUtil.getStringFromFile("application-without-expenses-list.json");
        Application application = objectMapper.readValue(stringFromFile, Application.class);

        ApplicationException exception = assertThrows(ApplicationException.class, () -> factorBasedServiceabilityCalculator.calculate(application));
        assertEquals("Invalid application : expenses list must contain at least one item", exception.getMessage());
    }

    @Test
    void shouldThrowException_WhenIncompleteItemDetected() throws JsonProcessingException {
        // load load application
        String stringFromFile = FileUtil.getStringFromFile("application-with-incomplete-income-item.json");
        Application application = objectMapper.readValue(stringFromFile, Application.class);

        ApplicationException exception = assertThrows(ApplicationException.class, () -> factorBasedServiceabilityCalculator.calculate(application));
        assertEquals("Invalid application : value cannot be null", exception.getMessage());
    }
}