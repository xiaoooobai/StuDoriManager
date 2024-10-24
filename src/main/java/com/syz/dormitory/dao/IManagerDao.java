package com.syz.dormitory.dao;


import com.syz.dormitory.pojo.Manager;
import com.syz.dormitory.pojo.query.ManagerQuery;

import java.util.List;

public interface IManagerDao {

    int[] queryGender();
    List<Manager> selectByPage(ManagerQuery managerQuery);

    Integer selectTotalCount(ManagerQuery  managerQuery);


    int deleteById(int id);

    int add(Manager  manager);

    Manager selectById(int id);

    int update(Manager  manager);

    List<Manager> selectAll();
}
