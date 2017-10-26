package ru.masharan.integration.social.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;
import ru.masharan.model.entity.Role;
import ru.masharan.model.entity.User;
import ru.masharan.model.repo.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;


/**
 * Simple little {@link ConnectionSignUp} command that allocates new userIds in memory.
 * Doesn't bother storing a user record in any local database, since this quickstart just stores the user id in a cookie.
 */
@Service
public class SocialConnectionSignUp implements ConnectionSignUp {

    private UserRepository userRepository;

    @Autowired
    public SocialConnectionSignUp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String execute(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();

        User user = new User(
                userProfile.getFirstName(),
                userProfile.getLastName(),
                randomAlphabetic(8),
                userProfile.getEmail(),
                false,
                //TODO исправить на enum
                new HashSet<>(Collections.singletonList(new Role()))
        );
        return null;
    }
}
