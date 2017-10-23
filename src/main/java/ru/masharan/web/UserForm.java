package ru.masharan.web;

import lombok.Data;
import ru.masharan.web.validators.PasswordMatcher;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@PasswordMatcher
public class UserForm {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotEmpty
    @NotNull
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @Email
    private String email;
}
