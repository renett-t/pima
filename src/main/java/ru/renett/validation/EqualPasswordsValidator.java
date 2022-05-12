package ru.renett.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualPasswordsValidator implements ConstraintValidator<EqualPasswords, Object> {

    private String passwordField;
    private String passwordRepeatField;

    @Override
    public void initialize(EqualPasswords constraintAnnotation) {
        passwordField = constraintAnnotation.password();
        passwordRepeatField = constraintAnnotation.passwordRepeat();
    }


    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object password = new BeanWrapperImpl(o).getPropertyValue(passwordField);
        Object passwordRepeat = new BeanWrapperImpl(o).getPropertyValue(passwordRepeatField);
        return password != null && password.equals(passwordRepeat);
    }
}
