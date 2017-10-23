package ru.masharan.model.service;

import ru.masharan.model.UserAlreadyExistException;
import ru.masharan.web.UserForm;

public interface UserService {

    void registerUserAccount(UserForm dto) throws UserAlreadyExistException;
}
