package com.kds.serviceabilitycal.calculator;

import com.kds.serviceabilitycal.model.Application;
import com.kds.serviceabilitycal.model.Frequency;
import com.kds.serviceabilitycal.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class FactorBasedServiceabilityCalculator implements ServiceabilityCalculator {

    private BigDecimal factor;

    @Autowired
    public FactorBasedServiceabilityCalculator(@Value("${factor}") BigDecimal factor) {
        this.factor = factor;
    }

    @Override
    public BigDecimal calculate(Application application) {
        return calculateMonthlyIncome(application).subtract(calculateMonthlyExpenses(application)).multiply(factor);
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
