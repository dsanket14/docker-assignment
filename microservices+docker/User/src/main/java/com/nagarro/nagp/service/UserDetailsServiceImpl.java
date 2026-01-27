package com.nagarro.nagp.service;

import com.nagarro.nagp.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService service;


    public UserDetailsServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = service.getUser(username);
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),new BCryptPasswordEncoder().encode(user.getPassword()), Collections.emptyList());
    }

}