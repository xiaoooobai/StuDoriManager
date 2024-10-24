package com.syz.dormitory.service.impl;


import com.syz.dormitory.dao.IUserDao;
import com.syz.dormitory.dao.impl.UserDaoImpl;
import com.syz.dormitory.pojo.User;
import com.syz.dormitory.pojo.query.UserQuery;
import com.syz.dormitory.service.IUserService;
import com.syz.dormitory.util.LayUITableResult;

import java.util.List;

public class UserServiceImpl implements IUserService {
    private IUserDao userDao = new UserDaoImpl();

    //业务：为了封装PageInfo，查询两次数据库

    @Override
    public User login(String name, String password) {
        return userDao.login(name, password);
    }

    @Override
    public boolean register(String name, String password, String nickname) {
        return userDao.register(name,password,nickname)>0?true:false;
    }
    @Override
    public LayUITableResult selectByPage(UserQuery userQuery) {
        List<User> list = userDao.selectByPage(userQuery);
        Integer totalCount = userDao.selectTotalCount(userQuery);
        return LayUITableResult.ok(totalCount, list);
    }
    @Override
    public boolean deleteById(int id) {
        int count = userDao.deleteById(id);
        return count == 1 ? true : false;
    }
    @Override
    public boolean deleteAll(String[] ids) {
        // delete from user where id in();
        int count = 0;
        for (String id : ids) {
            count += userDao.deleteById(Integer.parseInt(id));
        }
        return count == ids.length ? true : false;
    }
    @Override
    public boolean updateStatus(int id, int status) {
        int count = userDao.updateStatus(id, status);
        return count == 1 ? true : false;
    }

    @Override
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        return userDao.changePassword(userId,oldPassword,newPassword)>0?true:false;
    }
}
