package ru.masharan.web.validators;

import ru.masharan.web.UserForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatcher, UserForm> {


    @Override
    public boolean isValid(UserForm value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getMatchingPassword());
    }

}
