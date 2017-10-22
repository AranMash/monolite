package ru.masharan.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {

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
    private String email;
}
