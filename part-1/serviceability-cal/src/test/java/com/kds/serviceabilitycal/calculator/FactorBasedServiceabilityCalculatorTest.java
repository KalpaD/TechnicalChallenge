package com.kds.serviceabilitycal.calculator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kds.serviceabilitycal.model.Application;
import com.kds.serviceabilitycal.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FactorBasedServiceabilityCalculatorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FactorBasedServiceabilityCalculatorTest.class);

    private FactorBasedServiceabilityCalculator factorBasedServiceabilityCalculator;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void shouldCalculateFactorBasedServiceability_WhenFactorIs_2() throws JsonProcessingException {
        // load load application
        String stringFromFile = FileUtil.getStringFromFile("application-with-full-data.json");
        Application application = objectMapper.readValue(stringFromFile, Application.class);

        // set the factor
        BigDecimal factor = BigDecimal.valueOf(2);
        factorBasedServiceabilityCalculator = new FactorBasedServiceabilityCalculator(factor);

        LOGGER.info("result {}",factorBasedServiceabilityCalculator.calculate(application));
        Optional<BigDecimal> result = factorBasedServiceabilityCalculator.calculate(application);
        assertTrue(result.isPresent());
        assertEquals(0, BigDecimal.valueOf(2374.00).compareTo(result.get()));
    }

    @Test
    void shouldCalculateFactorBasedServiceability_WhenFactorIs_1() throws JsonProcessingException {
        // load load application
        String stringFromFile = FileUtil.getStringFromFile("application-with-full-data.json");
        Application application = objectMapper.readValue(stringFromFile, Application.class);

        // set the factor
        BigDecimal factor = BigDecimal.valueOf(1);
        factorBasedServiceabilityCalculator = new FactorBasedServiceabilityCalculator(factor);

        LOGGER.info("result {}", factorBasedServiceabilityCalculator.calculate(application));
        Optional<BigDecimal> result = factorBasedServiceabilityCalculator.calculate(application);
        assertTrue(result.isPresent());
        assertEquals(0, BigDecimal.valueOf(1187.00).compareTo(result.get()));
    }

    @Test
    void shouldReturnEmptyData_WhenIncomeListIsEmpty() throws JsonProcessingException {
        // load load application
        String stringFromFile = FileUtil.getStringFromFile("application-without-income-list.json");
        Application application = objectMapper.readValue(stringFromFile, Application.class);

        // set the factor
        BigDecimal factor = BigDecimal.valueOf(1);
        factorBasedServiceabilityCalculator = new FactorBasedServiceabilityCalculator(factor);

        LOGGER.info("result {}",factorBasedServiceabilityCalculator.calculate(application));
        Optional<BigDecimal> result = factorBasedServiceabilityCalculator.calculate(application);
        assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnEmptyData_WhenExpensesListIsEmpty() throws JsonProcessingException {
        // load load application
        String stringFromFile = FileUtil.getStringFromFile("application-without-expenses-list.json");
        Application application = objectMapper.readValue(stringFromFile, Application.class);

        // set the factor
        BigDecimal factor = BigDecimal.valueOf(1);
        factorBasedServiceabilityCalculator = new FactorBasedServiceabilityCalculator(factor);

        LOGGER.info("result {}",factorBasedServiceabilityCalculator.calculate(application));
        Optional<BigDecimal> result = factorBasedServiceabilityCalculator.calculate(application);
        assertFalse(result.isPresent());
    }
}