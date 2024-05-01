package com.vintageforlife.service.service;

import com.vintageforlife.service.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Integer id);

    UserDTO getUserByEmail(String email);

    UserDTO createUser(UserDTO user);
}
