package com.syz.dormitory.service.impl;



import com.syz.dormitory.dao.IManagerDao;
import com.syz.dormitory.dao.impl.ManagerDaoImpl;
import com.syz.dormitory.pojo.Manager;
import com.syz.dormitory.pojo.query.ManagerQuery;
import com.syz.dormitory.service.IManagerService;
import com.syz.dormitory.service.IManagerService;
import com.syz.dormitory.util.LayUITableResult;

import java.util.List;

public class ManagerServiceImpl implements IManagerService {
    IManagerDao managerDao=new ManagerDaoImpl();


    @Override
    public int[] queryGender() {
        return managerDao.queryGender();
    }

    @Override
    public LayUITableResult selectByPage(ManagerQuery managerQuery) {
        List<Manager> list = managerDao.selectByPage(managerQuery);
        Integer totalCount = managerDao.selectTotalCount(managerQuery);
        return LayUITableResult.ok(totalCount, list);
    }
    @Override
    public boolean deleteById(int id) {
        int count = managerDao.deleteById(id);
        return count == 1 ? true : false;
    }

    @Override
    public boolean add(Manager manager) {
        int count = managerDao.add(manager);
        return count == 1 ? true : false;
    }

    @Override
    public Manager selectById(int id) {
        return managerDao.selectById(id);
    }

    @Override
    public boolean update(Manager manager) {
        int count = managerDao.update(manager);
        return count == 1 ? true : false;
    }


    @Override
    public boolean deleteAll(String[] ids) {

        int count = 0;
        for (String id : ids) {
            count += managerDao.deleteById(Integer.parseInt(id));
        }
        return count == ids.length ? true : false;
    }

    @Override
    public List<Manager> selectAll() {
        return managerDao.selectAll();
    }

}
