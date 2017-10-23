package ru.masharan.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.masharan.model.entity.Role;
import ru.masharan.model.entity.User;
import ru.masharan.model.repo.RoleRepository;
import ru.masharan.model.repo.UserRepository;

import static java.util.Collections.singleton;

@Component
public class ApplicationTestDataIniter implements ApplicationRunner {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;



    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        User user = new User();
        user.setFirstName("user");
        user.setLastName("user");
        user.setEmail("sdf@gmail.com");
        String pass = "user";
        String encoded = encoder.encode(pass);
        user.setPassword(encoded);
        Role role = new Role();
        role.setName("user");
        roleRepository.save(role);
        user.setRoles(singleton(role));
        userRepository.save(user);
    }
}
