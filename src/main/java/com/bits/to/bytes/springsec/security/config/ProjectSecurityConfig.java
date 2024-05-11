package com.bits.to.bytes.springsec.security.config;

import com.bits.to.bytes.springsec.security.authenticationProvider.JwtAuthenticationProvider;
import com.bits.to.bytes.springsec.security.authenticationProvider.OtpdetailsAuthenticationProvider;
import com.bits.to.bytes.springsec.security.authenticationProvider.UserDetailsAuthenticationProvider;
import com.bits.to.bytes.springsec.security.filter.UserAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectSecurityConfig {
    UserDetailsAuthenticationProvider userDetailsAuthenticationProvider;
    OtpdetailsAuthenticationProvider otpdetailsAuthenticationProvider;
    JwtAuthenticationProvider jwtAuthenticationProvider;

    public ProjectSecurityConfig(UserDetailsAuthenticationProvider userDetailsAuthenticationProvider,
                                 OtpdetailsAuthenticationProvider otpdetailsAuthenticationProvider,
                                 JwtAuthenticationProvider jwt){
        this.userDetailsAuthenticationProvider= userDetailsAuthenticationProvider;
        this.otpdetailsAuthenticationProvider= otpdetailsAuthenticationProvider;
        this.jwtAuthenticationProvider= jwt;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder= httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(userDetailsAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(otpdetailsAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterAt(new UserAuthenticationFilter(authenticationManager(httpSecurity)), BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
