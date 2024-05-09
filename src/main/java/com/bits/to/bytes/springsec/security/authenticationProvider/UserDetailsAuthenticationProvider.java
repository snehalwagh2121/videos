package com.bits.to.bytes.springsec.security.authenticationProvider;

import com.bits.to.bytes.springsec.model.Otpdetails;
import com.bits.to.bytes.springsec.repository.OtpdetailsRepository;
import com.bits.to.bytes.springsec.security.authentication.UserDetailsAuthentication;
import com.bits.to.bytes.springsec.security.model.UserDetailsServiceSecurity;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${phone.number}")
    String mobileNumber;
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
//            UserDetailsAuthentication userDetailsAuthentication= (UserDetailsAuthentication) authentication;
//            userDetailsAuthentication.setOtp(otp);

            //send otp to user's mobile number
            Message.creator(new PhoneNumber(mobileNumber),
                    new PhoneNumber("+14582910713"), "Hi, the otp for signin is : "+otp).create();
            otpdetailsRepository.save(new Otpdetails(authentication.getName(), otp));
            return authentication;
        }
        throw new RuntimeException("Invalid Username/password ");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UserDetailsAuthentication.class);
    }
}
