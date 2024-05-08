package com.bits.to.bytes.springsec.security.filter;

import com.bits.to.bytes.springsec.model.Otpdetails;
import com.bits.to.bytes.springsec.model.Userdata;
import com.bits.to.bytes.springsec.security.authentication.OtpdetailsAuthentication;
import com.bits.to.bytes.springsec.security.authentication.UserDetailsAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    AuthenticationManager authenticationManager;

    public UserAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager= authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username= request.getHeader("username");
        String password= request.getHeader("password");
        String otp= request.getHeader("otp");

        if(otp != null){
            Authentication authentication= authenticationManager.authenticate(new OtpdetailsAuthentication(new Otpdetails(username, otp)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            Authentication authentication= authenticationManager.authenticate(new UserDetailsAuthentication(new Userdata(username, password, null)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.setHeader("otp", ((UserDetailsAuthentication)authentication).getOtp());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
