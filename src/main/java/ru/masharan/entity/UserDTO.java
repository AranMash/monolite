package ru.masharan.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    @NotNull
    @NotEmpty
    private String name;

    @NotEmpty
    @NotNull
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    private String email;
}
