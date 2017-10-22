package ru.masharan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.StaticResourceRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import ru.masharan.integration.adapter.SocialSignInAdapter;

@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {


    private AuthenticationProvider provider;

    @Autowired
    public ApplicationSecurity(AuthenticationProvider provider) {
        this.provider = provider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.
                authorizeRequests()
                .requestMatchers(StaticResourceRequest.toCommonLocations()).permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/login/**","/signin/**","/signup/**", "/user/registration/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                .logout().permitAll()
                .and()
                /*For h2-console authorisation */
                .headers().frameOptions().disable()
                .and()
                .csrf().disable();
        // @formatter:on
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

    /**
     * {@link ProviderSignInController} uses information from the request to determine the protocol,
     * host name, and port number to use when creating a callback URL.
     * @param factoryLocator
     * @param usersConnectionRepository
     * @return {@link ProviderSignInController}
     */
    @Bean
    public ProviderSignInController providerSignInController(
            ConnectionFactoryLocator factoryLocator,
            UsersConnectionRepository usersConnectionRepository
    ) {
        ProviderSignInController controller = new ProviderSignInController(
                factoryLocator,
                usersConnectionRepository,
                new SocialSignInAdapter()
        );
        controller.setSignUpUrl("/user/registration");

        return controller;
    }
}