package com.kds.serviceabilitycal.calculator;

import com.kds.serviceabilitycal.model.Application;
import com.kds.serviceabilitycal.model.Frequency;
import com.kds.serviceabilitycal.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class FactorBasedServiceabilityCalculator implements ServiceabilityCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FactorBasedServiceabilityCalculator.class);
    private BigDecimal factor;

    @Autowired
    public FactorBasedServiceabilityCalculator(@Value("${factor}") BigDecimal factor) {
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
    public Optional<BigDecimal> calculate(Application application) {
        return Optional.ofNullable(application)
                .filter(this::validateApplication)
                .map(app -> calculateMonthlyIncome(application)
                        .subtract(calculateMonthlyExpenses(application))
                        .multiply(factor));
    }

    private boolean validateApplication(Application application) {
        boolean isValid = !application.getIncomes().isEmpty() && !application.getExpenses().isEmpty();
        LOGGER.warn("Loan application validity status : {}", isValid);
        return isValid;
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
