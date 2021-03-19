package com.kds.serviceabilitycal.calculator;

import com.kds.serviceabilitycal.model.Application;

import java.math.BigDecimal;
import java.util.Optional;

public interface ServiceabilityCalculator {

    /**
     * Calculate the serviceability of a given loan application based on
     * pre defined factor.
     *
     * At the moment this method assumes there are only two frequencies for income and expenses.
     *
     * @param application A loan application.
     * @return Serviceability of the given application, for invalid Application empty data will be emitted.
     */
    Optional<BigDecimal> calculate(Application application);
}
