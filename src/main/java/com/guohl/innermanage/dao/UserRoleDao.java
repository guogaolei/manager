package com.guohl.innermanage.dao;


import com.guohl.innermanage.entity.UserRoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDao {

    List<UserRoleEntity> getRole(String userName) ;
}
