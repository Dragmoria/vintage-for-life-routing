package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.UserDTO;
import com.vintageforlife.service.entity.UserEntity;

public class UserMapper {
    public static UserDTO makeUserDTO(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isActive(),
                userEntity.getActivationLink(),
                userEntity.getRole()
                );
    }

    public static UserEntity makeUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDTO.getName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setActive(userDTO.getActive());
        userEntity.setActivationLink(userDTO.getActivationLink());
        userEntity.setRole(userDTO.getRole());

        return userEntity;
    }
}
