package org.example.day2.core.annotation.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    private String startDateField;
    private String endDateField;
    private String message;

    @Override
    public void initialize(DateRange constraintAnnotation) {
        this.startDateField = constraintAnnotation.startDateField();
        this.endDateField = constraintAnnotation.endDateField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            var startDateField = value.getClass().getDeclaredField(this.startDateField);
            startDateField.setAccessible(true);

            var endDateField = value.getClass().getDeclaredField(this.endDateField);
            endDateField.setAccessible(true);

            var startDate = (LocalDateTime) startDateField.get(value);
            var endDate = (LocalDateTime) endDateField.get(value);
            if (startDate == null || endDate == null || !endDate.isAfter(startDate)) {
                if (message != null) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(this.message)
                            .addPropertyNode(this.startDateField)
                            .addPropertyNode(this.endDateField)
                            .addConstraintViolation();
                }
                return false;
            }
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
