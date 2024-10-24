package com.syz.dormitory.dao;


import com.syz.dormitory.pojo.User;
import com.syz.dormitory.pojo.query.UserQuery;

import java.util.List;

public interface IUserDao {

    User login(String name, String password);
    int register(String name,String password,String nickname);

    List<User> selectByPage(UserQuery userQuery);
    Integer selectTotalCount(UserQuery userQuery);
    int deleteById(int id);
    int updateStatus(int id, int status);

    int changePassword(int userId,String oldPassword,String newPassword);
}
