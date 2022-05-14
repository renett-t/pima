package ru.renett.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SameFieldsValidator implements ConstraintValidator<SameFields, Object> {

    private String fieldOne;
    private String fieldTwo;

    @Override
    public void initialize(SameFields constraintAnnotation) {
        fieldOne = constraintAnnotation.one();
        fieldTwo = constraintAnnotation.two();
    }


    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        Object one = new BeanWrapperImpl(obj).getPropertyValue(fieldOne);
        Object two = new BeanWrapperImpl(obj).getPropertyValue(fieldTwo);
        return one != null && one.equals(two);
    }
}
