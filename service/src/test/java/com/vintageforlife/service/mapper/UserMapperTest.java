package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.UserDTO;
import com.vintageforlife.service.entity.UserEntity;
import com.vintageforlife.service.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserMapperTest {
    private final UserMapper userMapper;

    @Autowired
    public UserMapperTest(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    void testToEntity() {
        UserDTO userDTO = UserDTO.builder()
                .id(1)
                .name("Test Name")
                .password("Test Password")
                .role(Role.ADMIN)
                .email("test email")
                .build();

        UserEntity userEntity = userMapper.toEntity(userDTO);

        assertEquals(userDTO.getId(), userEntity.getId());
        assertEquals(userDTO.getName(), userEntity.getName());
        assertEquals(userDTO.getPassword(), userEntity.getPassword());
        assertEquals(userDTO.getRole(), userEntity.getRole());
        assertEquals(userDTO.getEmail(), userEntity.getEmail());
    }

    @Test
    void testWithoutId() {
        UserDTO userDTO = UserDTO.builder()
                .name("Test Name")
                .password("Test Password")
                .role(Role.ADMIN)
                .email("test email")
                .build();

        UserEntity userEntity = userMapper.toEntity(userDTO);

        assertNull(userEntity.getId());
        assertEquals(userDTO.getName(), userEntity.getName());
        assertEquals(userDTO.getPassword(), userEntity.getPassword());
        assertEquals(userDTO.getRole(), userEntity.getRole());
        assertEquals(userDTO.getEmail(), userEntity.getEmail());
    }

    @Test
    void testToDTO() {
        UserEntity userEntity = UserEntity.builder()
                .id(1)
                .name("Test Name")
                .password("Test Password")
                .role(Role.ADMIN)
                .email("test email")
                .build();

        UserDTO userDTO = userMapper.toDTO(userEntity);

        assertEquals(userEntity.getId(), userDTO.getId());
        assertEquals(userEntity.getName(), userDTO.getName());
        assertNull(userDTO.getPassword());
        assertEquals(userEntity.getRole(), userDTO.getRole());
        assertEquals(userEntity.getEmail(), userDTO.getEmail());
    }
}
