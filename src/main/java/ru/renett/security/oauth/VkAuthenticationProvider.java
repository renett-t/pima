package ru.renett.security.oauth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class VkAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public VkAuthenticationProvider(@Qualifier("VkOauthUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        VkOauthAuthentication auth = (VkOauthAuthentication) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName());
        auth.setAuthenticated(true);
        auth.setUserDetails(userDetails);

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return VkOauthAuthentication.class.isAssignableFrom(authentication);
    }
}
