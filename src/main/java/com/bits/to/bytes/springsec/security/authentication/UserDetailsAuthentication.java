package com.bits.to.bytes.springsec.security.authentication;

import com.bits.to.bytes.springsec.model.Userdata;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
public class UserDetailsAuthentication implements Authentication {

    Userdata userdata;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return userdata.getPassword();
    }

    @Override
    public Object getDetails() {
        return userdata.getRole();
    }

    @Override
    public Object getPrincipal() {
        return userdata.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return userdata.getUsername();
    }
}
