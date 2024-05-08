package com.bits.to.bytes.springsec.security.authenticationProvider;

import com.bits.to.bytes.springsec.model.Otpdetails;
import com.bits.to.bytes.springsec.repository.OtpdetailsRepository;
import com.bits.to.bytes.springsec.security.authentication.OtpdetailsAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OtpdetailsAuthenticationProvider implements AuthenticationProvider {

    OtpdetailsRepository otpdetailsRepository;

    public OtpdetailsAuthenticationProvider(OtpdetailsRepository otpdetailsRepository){
        this.otpdetailsRepository= otpdetailsRepository;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Otpdetails otpdetails= otpdetailsRepository.findById(authentication.getName()).orElseThrow();
        if(otpdetails.getOtp().equals(authentication.getCredentials())){
            return authentication;
        }
        throw new RuntimeException("Otp in invalid");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(OtpdetailsAuthentication.class);
    }
}
