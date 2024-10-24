package com.syz.dormitory.service;


import com.syz.dormitory.pojo.User;
import com.syz.dormitory.pojo.query.UserQuery;
import com.syz.dormitory.util.LayUITableResult;

//接口里面列出来代表Service能为Controller层提供的功能
public interface IUserService {

    User login(String name, String password);
    boolean register(String name,String password,String nickname);

    LayUITableResult selectByPage(UserQuery userQuery);

    boolean deleteById(int id);
    boolean deleteAll(String[] ids);
    boolean updateStatus(int id, int status);
    boolean changePassword(int userId,String oldPassword,String newPassword);
}

