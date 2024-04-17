package com.vintageforlife.service.service;

import com.vintageforlife.service.entity.UserEntity;
import com.vintageforlife.service.exception.EmailNotFoundException;
import com.vintageforlife.service.mapper.UserMapper;
import com.vintageforlife.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByEmail(String email) throws EmailNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("User with email " + email + " does not exist"));

        GrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole().name());

        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), Collections.singletonList(authority));
    }
}
