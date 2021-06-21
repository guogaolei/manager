package com.guohl.innermanage.dao;

import com.guohl.innermanage.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    UserEntity getUser(String userName);
}
