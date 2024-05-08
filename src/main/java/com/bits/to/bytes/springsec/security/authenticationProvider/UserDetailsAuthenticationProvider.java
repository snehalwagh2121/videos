package com.bits.to.bytes.springsec.security.authenticationProvider;

import com.bits.to.bytes.springsec.security.authentication.UserDetailsAuthentication;
import com.bits.to.bytes.springsec.security.model.UserDetailsServiceSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDetailsAuthenticationProvider implements AuthenticationProvider {
    UserDetailsServiceSecurity userDetailsServiceSecurity;

    public UserDetailsAuthenticationProvider(UserDetailsServiceSecurity userDetailsServiceSecurity){
        this.userDetailsServiceSecurity= userDetailsServiceSecurity;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Authenticating user details");
        UserDetails userDetails= userDetailsServiceSecurity.loadUserByUsername(authentication.getName());
        if(authentication.getCredentials().equals(userDetails.getPassword())){
            return authentication;
        }
        throw new RuntimeException("Invalid Username/password ");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UserDetailsAuthentication.class);
    }
}
