package ru.masharan.integration.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.api.Twitter;
import ru.masharan.integration.social.connection.SimpleConnectionSignUp;

import javax.sql.DataSource;

@Configuration
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * Add a {@link ConnectionFactoryLocator} that has registered the providers your app connects to.
     * This creates a registry of connection factories {@link org.springframework.social.connect.ConnectionFactory}
     * that other objects can use to lookup connection factories dynamically.
     * The connection factory registry implements the {@link ConnectionFactoryLocator} interface.
     * Spring Boot automatically searches {@docRoot application.properties}, than take default settings for
     * the app-id and app-secret with a special prefix like this one, for example: "spring.social.facebook".
     *
     */
    @Autowired
    private ConnectionFactoryLocator factoryLocator;

    /**
     * Add a UsersConnectionRepository for persisting Connection data across all users
     *
     * @return {@link UsersConnectionRepository}
     */
    @Bean
    public UsersConnectionRepository usersConnectionRepository() {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
                dataSource,
                factoryLocator,
                Encryptors.noOpText()
        );
        repository.setConnectionSignUp(new SimpleConnectionSignUp());
        return repository;
    }

    /**
     * Request-scoped beans representing current user API bindings. Facebook is shown here
     *
     * @return {@link Facebook}
     */
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    @Autowired
    public Facebook facebook(ConnectionRepository connectionRepository) {
        return connectionRepository.getPrimaryConnection(Facebook.class).getApi();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    @Autowired
    public Twitter twitter(ConnectionRepository connectionRepository) {
        return connectionRepository.getPrimaryConnection(Twitter.class).getApi();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    @Autowired
    public LinkedIn linkedIn(ConnectionRepository connectionRepository) {
        return connectionRepository.getPrimaryConnection(LinkedIn.class).getApi();
    }

    /**
     * Internally, Spring Social’s configuration support will use the {@link UsersConnectionRepository}
     * to create a request-scoped {@link ConnectionRepository} bean.
     * In doing so, it must identify the current user. Therefore, we must also override
     * the {@link SocialConfigurer#getUserIdSource()} to return an instance of a UserIdSource.
     *
     * @return {@link UserIdSource}
     */
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }
}
