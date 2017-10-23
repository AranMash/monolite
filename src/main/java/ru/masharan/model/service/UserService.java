package ru.masharan.model.service;

import ru.masharan.model.UserAlreadyExistException;
import ru.masharan.model.entity.UserDto;

public interface UserService {

    void registerUserAccount(UserDto dto) throws UserAlreadyExistException;
}
