package com.zhw.service.impl;

import com.zhw.UserEntity;
import com.zhw.dao.IUserDAO;
import com.zhw.service.IUserService2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by zhw on 16/3/24.
 */
@Service
public class IUserService2Impl implements IUserService2 {

    @Resource
    IUserDAO userDAO;

    public void funNone() throws Exception {
        save(new UserEntity("zhw"));

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void funRequire() throws Exception {
        save(new UserEntity("wlj"));

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void funRequire2() throws Exception {
        save(new UserEntity("shifang"));

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void funRequireException() throws Exception {
        save(new UserEntity("max"));

        throwExcp();

    }

    @Transactional(propagation = Propagation.NESTED)
    public void funNest() throws Exception {
        save(new UserEntity("yunxinghe"));

    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void funNestException() throws Exception {
        save(new UserEntity("edward"));
        throwExcp();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void funRequireNew() throws Exception {
        save(new UserEntity("kb"));

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void funRequireNewException() throws Exception {
        save(new UserEntity("laura"));
        throwExcp();


    }


    private void throwExcp() throws Exception {
        throw new RuntimeException("boom");
    }

    public int save(UserEntity userEntity) throws Exception {
        userDAO.save(userEntity);
        return userEntity.getId();
    }
}
