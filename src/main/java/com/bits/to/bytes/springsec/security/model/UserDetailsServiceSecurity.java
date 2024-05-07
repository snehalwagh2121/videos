package com.bits.to.bytes.springsec.security.model;

import com.bits.to.bytes.springsec.mapper.UserDataToUserDetailsMapper;
import com.bits.to.bytes.springsec.model.Userdata;
import com.bits.to.bytes.springsec.repository.UserDataRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceSecurity implements UserDetailsService {

    UserDataRepository userDataRepository;

    public UserDetailsServiceSecurity(UserDataRepository userDataRepository){
        this.userDataRepository= userDataRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new UserDetailsSecurity();
        Optional<Userdata> userdata= userDataRepository.findById(username);
        if(userdata.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return UserDataToUserDetailsMapper.convertToUserDetails(userdata.get());
    }
}
