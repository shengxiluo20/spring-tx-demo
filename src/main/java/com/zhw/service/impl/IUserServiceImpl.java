package com.zhw.service.impl;

import com.zhw.dao.IUserDAO;
import com.zhw.UserEntity;
import com.zhw.service.IUserService;
import com.zhw.service.IUserService2;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by zhw on 16/3/24.
 */
@Service
public class IUserServiceImpl implements IUserService {

    @Resource
    IUserDAO userDAO;

    @Resource
    IUserService2 userService2;

    @Override
    @Transactional
    public void fun1() throws Exception {

        //数据库操作
        funNone();
        //调用另一个service的方法
        userService2.funNest();

        //当调用另一个Service的method的时候,想要将他的事务加到现在这个事务中,很可能自然而然想到了嵌套
        //这么想就错了,Required的定义里已经说明了如果没有，就新建一个事务；如果有，就加入当前事务。
        //那么直接使用Required就满足需求
        //这样在方法中任何地方发生unchecked异常将触发整个方法的回滚
        //而Nested的使用场景下面再介绍

    }
//        ((IUserService) AopContext.currentProxy()).funRequireException();

    @Override
    @Transactional
    public void fun2() throws Exception {
        //嵌套事务的使用场景
        funNone();

        try {
            //当所调用的方法为NESTED事务,该事务的回滚可以不影响到调用者的事务
            //当然如果没有catch exception,异常冒泡而出,就将触发调用者事务的回滚
            userService2.funNestException();
        } catch (Exception e) {

            //do something
        }

        userService2.funRequire();


    }

    @Override
    @Transactional
    public void fun3() throws Exception {


        //嵌套事务的使用场景
        funNone();
        try {
            //调用的事务为NESTED事务的方法
            userService2.funNest();
        } catch (Exception e) {

            //do something
        }

        userService2.funRequire();

        //此时在调用者处,触发一个unchecked异常
        throwExcp();


        //此时会发现包括调用的userService2.funNest()也被回滚了
        //也就是说,当调用的方法是NESTED事务,该方法抛出异常如果得到了处理(try-catch),那么该方法发生异常不会触发整个方法的回滚
        //而调用者出现unchecked异常,却能触发所调用的nested事务的回滚.
    }

    @Override
    @Transactional
    public void fun4() throws Exception {
        //而REQUIRES_NEW,当被调用时,就相当于暂停(挂起)当前事务,先开一个新的事务去执行REQUIRES_NEW的方法,如果REQUIRES_NEW中的异常得到了处理
        //那么他将不影响调用者的事务,同时,调用者之后出现了异常,同样也不会影响之前调用的REQUIRES_NEW方法的事务.


        //不会回滚
        funNone();
        try {
            //当异常得到处理,外部不会触发回滚
            userService2.funRequireNewException();
        } catch (Exception e) {

        }
    }

    @Override
    @Transactional
    public void fun5() throws Exception {


        //数据库操作
        funNone();
        //调用RequireNew类型事务的方法,调用者的异常回滚不会影响到它
        userService2.funRequireNew();
        //数据库操作
        funNone();

        //抛出unchecked异常,触发回滚
        throwExcp();


    }

    @Override
    @Transactional
    public void fun6() throws Exception {

        funNone();

        try {
            //当调用的是Required时,就算异常被处理了,整个方法也将会回滚
            userService2.funRequireException();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    @Transactional
    public void fun7() throws Exception {

        funRequire();

        try {
            funNestException();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        funRequire();

    }

    @Override
    @Transactional
    public void fun8() throws Exception {
        ((IUserService) AopContext.currentProxy()).funRequire();

        try {
            ((IUserService) AopContext.currentProxy()).funNestException();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        ((IUserService) AopContext.currentProxy()).funRequire();
    }


    //不带事务的方法
    public void funNone() throws Exception {
        save(new UserEntity("zhw"));

    }

    @Override
    public void funNoneException() throws Exception {
        save(new UserEntity("doge"));
        throwExcp();
    }


    //启动默认事务的方法
    @Transactional(propagation = Propagation.REQUIRED)
    public void funRequire() throws Exception {
        save(new UserEntity("wlj"));

    }

    //启动默认事务的方法
    @Transactional(propagation = Propagation.REQUIRED)
    public void funRequire2() throws Exception {
        save(new UserEntity("shifang"));

    }

    //启动默认事务的方法,抛出RuntimeException
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void funRequireException() throws Exception {
        save(new UserEntity("max"));

        throwExcp();

    }

    //启动嵌套事务的方法
    @Transactional(propagation = Propagation.NESTED)
    public void funNest() throws Exception {
        save(new UserEntity("yunxinghe"));

    }


    //启动嵌套事务的方法,但会抛出异常
    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void funNestException() throws Exception {
        save(new UserEntity("edward"));
        throwExcp();
    }

    //REQUIRES_NEW事务的方法
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void funRequireNew() throws Exception {
        save(new UserEntity("kb"));

    }

    //REQUIRES_NEW事务的方法,但会抛出异常
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void funRequireNewException() throws Exception {
        save(new UserEntity("laura"));
        throwExcp();
    }


    //抛出异常
    private void throwExcp() throws Exception {
        throw new RuntimeException("boom");
    }

    //保存数据
    public int save(UserEntity userEntity) throws Exception {
        userDAO.save(userEntity);
        return userEntity.getId();
    }
}
