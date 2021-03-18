package com.kds.serviceabilitycal.calculator;

import com.kds.serviceabilitycal.model.Application;

import java.math.BigDecimal;

public interface ServiceabilityCalculator {

    BigDecimal calculate(Application application);
}
