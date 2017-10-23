package ru.masharan.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.masharan.model.UserAlreadyExistException;
import ru.masharan.model.entity.UserDto;
import ru.masharan.model.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepo;


    @Override
    public void registerUserAccount(UserDto dto) throws UserAlreadyExistException {
        if (emailExist(dto.getEmail())) {
            throw new UserAlreadyExistException("User with email" + dto.getEmail() + " is already exist");
        }
    }

    private boolean emailExist(String email) {
        return userRepo.findByEmail(email) != null;
    }

}
