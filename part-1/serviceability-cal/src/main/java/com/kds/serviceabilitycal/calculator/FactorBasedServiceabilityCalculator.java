package com.kds.serviceabilitycal.calculator;

import com.kds.serviceabilitycal.exception.ApplicationException;
import com.kds.serviceabilitycal.model.Application;
import com.kds.serviceabilitycal.model.Frequency;
import com.kds.serviceabilitycal.model.Item;
import com.kds.serviceabilitycal.validator.ApplicationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Set;

@Service
public class FactorBasedServiceabilityCalculator implements ServiceabilityCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FactorBasedServiceabilityCalculator.class);
    private ApplicationValidator validator;
    private BigDecimal factor;

    @Autowired
    public FactorBasedServiceabilityCalculator(@Value("${factor}") BigDecimal factor,
                                               ApplicationValidator validator) {
        this.validator = validator;
        this.factor = factor;
    }

    /**
     * Calculate the serviceability of a given loan application based on
     * pre defined factor.
     *
     * At the moment this method assumes there are only two frequencies for income and expenses.
     *
     * @param application A loan application.
     * @return Serviceability of the given application, for invalid Application empty data will be emitted.
     */
    @Override
    public BigDecimal calculate(Application application) {
        Application validateApplication = validateApplication(application);
        return calculateMonthlyIncome(validateApplication)
                        .subtract(calculateMonthlyExpenses(validateApplication))
                        .multiply(factor);
    }

    private Application validateApplication(Application application) {
        Set<ConstraintViolation<Application>> violations = validator.validate(application);
        if (!violations.isEmpty()) {
            String errorMessage = validator.toErrorMessage(violations);
            LOGGER.error("Invalid loan application, The following list of violation detected:\n {}", errorMessage);
            throw new ApplicationException(String.format("Invalid application : %s", errorMessage));
        }
        return application;
    }

    private BigDecimal calculateMonthlyIncome(Application application) {
        BigDecimal monthlyIncomeFromFrequencyM = application.getIncomes().stream()
                .filter(item -> item.getFrequency() == Frequency.M)
                .map(Item::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal monthlyIncomeFromFrequencyY = application.getIncomes().stream()
                .filter(item -> item.getFrequency() == Frequency.Y)
                .map(item -> item.getValue().divide(BigDecimal.valueOf(12), RoundingMode.HALF_EVEN))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return monthlyIncomeFromFrequencyM.add(monthlyIncomeFromFrequencyY);
    }

    private BigDecimal calculateMonthlyExpenses(Application application) {
        BigDecimal monthlyExpensesFromFrequencyM = application.getExpenses().stream()
                .filter(item -> item.getFrequency() == Frequency.M)
                .map(Item::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal monthlyExpensesFromFrequencyY = application.getExpenses().stream()
                .filter(item -> item.getFrequency() == Frequency.Y)
                .map(item -> item.getValue().divide(BigDecimal.valueOf(12), RoundingMode.HALF_EVEN))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return monthlyExpensesFromFrequencyM.add(monthlyExpensesFromFrequencyY);
    }
}
