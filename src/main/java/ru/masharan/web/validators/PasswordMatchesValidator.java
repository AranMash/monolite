package ru.masharan.web.validators;

import ru.masharan.model.entity.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatcher, UserDto> {


    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getMatchingPassword());
    }

}
