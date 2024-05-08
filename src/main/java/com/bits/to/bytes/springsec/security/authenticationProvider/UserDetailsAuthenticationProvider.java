package com.bits.to.bytes.springsec.security.authenticationProvider;

import com.bits.to.bytes.springsec.model.Otpdetails;
import com.bits.to.bytes.springsec.repository.OtpdetailsRepository;
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
    OtpdetailsRepository otpdetailsRepository;
    public UserDetailsAuthenticationProvider(UserDetailsServiceSecurity userDetailsServiceSecurity, OtpdetailsRepository otpdetailsRepository){
        this.userDetailsServiceSecurity= userDetailsServiceSecurity;
        this.otpdetailsRepository= otpdetailsRepository;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Authenticating user details");
        UserDetails userDetails= userDetailsServiceSecurity.loadUserByUsername(authentication.getName());
        if(authentication.getCredentials().equals(userDetails.getPassword())){
            String otp= String.valueOf(Math.random()).substring(2, 8);
            UserDetailsAuthentication userDetailsAuthentication= (UserDetailsAuthentication) authentication;
            userDetailsAuthentication.setOtp(otp);
            otpdetailsRepository.save(new Otpdetails(authentication.getName(), otp));
            return userDetailsAuthentication;
        }
        throw new RuntimeException("Invalid Username/password ");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UserDetailsAuthentication.class);
    }
}
