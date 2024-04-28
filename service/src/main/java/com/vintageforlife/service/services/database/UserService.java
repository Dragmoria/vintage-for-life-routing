package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Integer id);

    UserDTO getUserByEmail(String email);

    UserDTO createUser(UserDTO user);
}
