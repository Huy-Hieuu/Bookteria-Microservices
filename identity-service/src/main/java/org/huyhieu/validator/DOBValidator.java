package org.huyhieu.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DOBValidator implements ConstraintValidator<DOBConstraint, LocalDate> {

    int minAge;

    /*
    * We can get value define to method validator here
    * ex: min = 18 => we can get 18 of min()
    * */
    @Override
    public void initialize(DOBConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        minAge = constraintAnnotation.minAge();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        // Only validate if the DOB value is not null
        if (value == null) {
            return true;
        }

        return ChronoUnit.YEARS.between(value, LocalDate.now()) >= minAge;
    }
}
