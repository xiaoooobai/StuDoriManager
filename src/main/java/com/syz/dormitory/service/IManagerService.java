package com.syz.dormitory.service;


import com.syz.dormitory.pojo.Manager;
import com.syz.dormitory.pojo.query.ManagerQuery;
import com.syz.dormitory.util.LayUITableResult;

import java.util.List;

//接口里面列出来代表Service能为Controller层提供的功能
public interface IManagerService {

    int[] queryGender();
    LayUITableResult selectByPage(ManagerQuery managerQuery);

    boolean deleteById(int id);

    boolean add(Manager manager);

    Manager selectById(int id);

    boolean update(Manager manager);

    boolean deleteAll(String[] ids);

    List<Manager> selectAll();

}

