package com.vintageforlife.service.repository;

import com.vintageforlife.service.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByName(String userName);

    Optional<UserEntity> findByEmail(String email);
}
