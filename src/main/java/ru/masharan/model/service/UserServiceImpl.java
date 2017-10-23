package ru.masharan.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.masharan.model.UserAlreadyExistException;
import ru.masharan.model.entity.User;
import ru.masharan.model.repo.UserRepository;
import ru.masharan.web.UserForm;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    @Transactional
    public void registerUserAccount(UserForm form) throws UserAlreadyExistException {
        if (emailExist(form.getEmail())) {
            throw new UserAlreadyExistException("User with email" + form.getEmail() + " is already exist");
        }

        User user = User.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .password(decryptPassword(form)).build();

        userRepo.save(user);
    }

    private String decryptPassword(UserForm form) {
        return encoder.encode(form.getPassword());
    }


    private boolean emailExist(String email) {
        return userRepo.findByEmail(email) != null;
    }

}
