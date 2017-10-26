package ru.masharan.integration.social.adapter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Uses to sign a user into the application when a matching connection is found.
 */
@Service
public class SocialSignInAdapter implements SignInAdapter {

    /**
     * The {@link SignInAdapter#signIn(String, Connection, NativeWebRequest)} method takes the local application
     * user’s user ID normalized as a String. No other credentials are necessary here because by the time this
     * method is called the user will have signed into the provider and their connection with that provider has
     * been used to prove the user’s identity. Implementations of this interface should use this user ID to
     * authenticate the user to the application.
     * @param userId
     * @param connection
     * @param request
     * @return null
     */
    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));
        return null;
    }
}
