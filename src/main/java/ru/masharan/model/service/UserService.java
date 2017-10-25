package ru.masharan.model.service;

import ru.masharan.model.UserAlreadyExistException;
import ru.masharan.model.entity.User;
import ru.masharan.web.UserForm;

public interface UserService {

    User createUserAccount(UserForm dto) throws UserAlreadyExistException;

    void createVerificationToken(User user, String token);
}
