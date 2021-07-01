package com.guohl.innermanage.dao;

import com.guohl.innermanage.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    UserEntity getUser(String userName);
    List<UserEntity> getAllUser();
}
