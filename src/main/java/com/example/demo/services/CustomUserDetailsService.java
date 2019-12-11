package com.example.demo.services;


import com.example.demo.domain.User;
import com.example.demo.services.User.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User loggedInUser = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");

        return new org.springframework.security.core.userdetails.User(loggedInUser.getId().toString(), loggedInUser.getPassword(), Arrays.asList(authority));
    }
}