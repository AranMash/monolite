package ru.masharan.integration.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import ru.masharan.entity.User;
import ru.masharan.integration.social.connection.SimpleConnectionSignUp;
import ru.masharan.security.UserSecurityContext;

import javax.sql.DataSource;
import javax.xml.crypto.Data;


@Configuration
public class SocialConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private DataSource dataSource;

    /**
     * Add a ConnectionFactoryLocator that has registered the providers your app connects to
     *
     * @return ConnectionFactoryLocator
     */
    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(
                new FacebookConnectionFactory(
                        environment.getProperty("facebook.clientId"),
                        environment.getProperty("facebook.clientSecret")
                )
        );

        return registry;
    }

    /**
     * Add a UsersConnectionRepository for persisting Connection data across all users
     *
     * @return UsersConnectionRepository
     */
    @Bean
    public UsersConnectionRepository usersConnectionRepository() {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
                dataSource,
                connectionFactoryLocator(),
                Encryptors.noOpText()
        );

        repository.setConnectionSignUp(new SimpleConnectionSignUp());
        return repository;
    }

    /**
     * Add a ConnectionRepository for managing the current user's connections
     *
     * @return ConnectionRepository
     */
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public ConnectionRepository connectionRepository() {
        User user = UserSecurityContext.getCurrentUser();
        return usersConnectionRepository().createConnectionRepository(String.valueOf(user.getId()));
    }

    /**
     * Request-scoped beans representing current user API bindings. Facebook is shown here
     *
     * @return Facebook
     */
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public Facebook facebook() {
        return connectionRepository().getPrimaryConnection(Facebook.class).getApi();
    }

//    @Bean
//    public ProviderSignInController providerSignInController() {
//        return new ProviderSignInController(
//                connectionFactoryLocator(),
//                usersConnectionRepository(),
//
//                );
//    }
}
