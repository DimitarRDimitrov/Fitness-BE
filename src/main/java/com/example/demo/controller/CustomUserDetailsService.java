package com.example.demo.controller;


import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    LoginController loginController;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = passwordEncoder();
        User user = loadSystemUserByUsername(userName);
        Collection<? extends GrantedAuthority> authorities;
        if (user.isAdmin()) {
            authorities = AuthorityUtils.createAuthorityList("ADMIN_ROLE");
        } else {
            authorities = AuthorityUtils.NO_AUTHORITIES;
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), encoder.encode(user.getPassword()), authorities);
    }

    public User loadSystemUserByUsername(String userName) {
        return loginController.getUserByUserName(userName);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
