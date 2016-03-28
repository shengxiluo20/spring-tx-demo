package com.zhw.service;

import com.zhw.UserEntity;

/**
 * Created by zhw on 16/3/24.
 */
public interface IUserService2 {


    public void funNone() throws Exception;

    public void funRequire() throws Exception;

    public void funRequire2() throws Exception;

    public void funRequireException() throws Exception;

    public void funNest() throws Exception;

    public void funNestException() throws Exception;

    public void funRequireNew() throws Exception;

    public void funRequireNewException() throws Exception;

    public int save(UserEntity userEntity) throws Exception;
}
