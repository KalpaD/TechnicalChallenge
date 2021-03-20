package com.kds.serviceabilitycal.validator;

import com.kds.serviceabilitycal.model.Application;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ApplicationValidator {

    public Set<ConstraintViolation<Application>> validate(Application application) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(application);
    }

    public String toErrorMessage(Set<ConstraintViolation<Application>> violations) {
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
    }
}
