package com.vintageforlife.service.service;

import com.vintageforlife.service.exception.EmailNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    UserDetails loadUserByEmail(String email) throws EmailNotFoundException;
}
