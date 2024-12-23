package com.bci.sermaluc.service.impl;

import com.bci.sermaluc.entity.UserEntity;
import com.bci.sermaluc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username) .orElseThrow(() ->
                new UsernameNotFoundException("User not exists by Email"));
        Set<GrantedAuthority> authorities = Collections.emptySet();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(), authorities);
    }
}
