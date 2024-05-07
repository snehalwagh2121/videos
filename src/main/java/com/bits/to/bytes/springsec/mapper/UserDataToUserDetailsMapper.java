package com.bits.to.bytes.springsec.mapper;

import com.bits.to.bytes.springsec.model.Userdata;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDataToUserDetailsMapper {

    public static UserDetails convertToUserDetails(Userdata userdata){
        return User.withUsername(userdata.getUsername())
                .password(userdata.getPassword())
                .roles(userdata.getRole())
                .build();
    }
}
