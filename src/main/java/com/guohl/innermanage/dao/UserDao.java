package com.guohl.innermanage.dao;

import com.guohl.innermanage.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    UserEntity getUser(String userName);
}
