package com.vintageforlife.service.service;

import com.vintageforlife.service.dto.UserDTO;
import com.vintageforlife.service.entity.UserEntity;
import com.vintageforlife.service.mapper.UserMapper;
import com.vintageforlife.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getUserById(Integer id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist"));
        return UserMapper.makeUserDTO(userEntity);
    }

    @Override
    public UserDTO createUser(UserDTO user) {
        UserEntity userEntity = UserMapper.makeUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return UserMapper.makeUserDTO(savedUserEntity);
    }
}
