package com.bits.to.bytes.springsec.security.authenticationProvider;

import com.bits.to.bytes.springsec.security.authentication.JwtAuthentication;
import com.bits.to.bytes.springsec.security.model.UserDetailsServiceSecurity;
import com.bits.to.bytes.springsec.security.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    UserDetailsServiceSecurity userDetailsServiceSecurity;
    JwtUtil jwtUtil;

    public JwtAuthenticationProvider(UserDetailsServiceSecurity userDetailsServiceSecurity, JwtUtil jwtUtil){
        this.userDetailsServiceSecurity= userDetailsServiceSecurity;
        this.jwtUtil= jwtUtil;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails= userDetailsServiceSecurity.loadUserByUsername(authentication.getName());
        if(jwtUtil.validateJwtToken((String) authentication.getCredentials(), userDetails)){
            return authentication;
        }
        throw new RuntimeException("Token is invalid");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthentication.class);
    }
}
