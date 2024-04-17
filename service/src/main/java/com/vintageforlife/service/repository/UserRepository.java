package com.vintageforlife.service.repository;

import com.vintageforlife.service.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
}
