package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO getUserById(Integer id);

    UserDTO getUserByEmail(String email);

    UserDTO createUser(UserDTO user);

    List<UserDTO> getAllUsers();

    void deleteUser(Integer id);

    UserDTO updateUser(Integer id, UserDTO user);


}
