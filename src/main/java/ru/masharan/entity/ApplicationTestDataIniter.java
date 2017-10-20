package ru.masharan.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.masharan.web.repo.RoleRepository;
import ru.masharan.web.repo.UserRepository;

import static java.util.Collections.singleton;

@Component
public class ApplicationTestDataIniter implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        User user = new User();
        user.setName("user");
        user.setPassword("user");

        Role role = new Role();
        role.setName("user");
        roleRepository.save(role);
        user.setRoles(singleton(role));
        userRepository.save(user);
    }
}
