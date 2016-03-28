package com.zhw.dao;

import com.zhw.UserEntity;

/**
 * Created by zhw on 16/3/24.
 */
public interface IUserDAO {

    public int save(UserEntity userEntity) throws Exception;
}
