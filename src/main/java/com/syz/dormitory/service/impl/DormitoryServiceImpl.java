package com.syz.dormitory.service.impl;


import com.syz.dormitory.dao.IDormitoryDao;
import com.syz.dormitory.dao.impl.DormitoryDaoImpl;
import com.syz.dormitory.pojo.Dormitory;
import com.syz.dormitory.pojo.query.DormitoryQuery;
import com.syz.dormitory.service.IDormitoryService;
import com.syz.dormitory.util.LayUITableResult;

import java.util.List;

public class DormitoryServiceImpl implements IDormitoryService {

    IDormitoryDao dormitoryDao=new DormitoryDaoImpl();
    @Override
    public LayUITableResult selectByPage(DormitoryQuery dormitoryQuery) {
        List<Dormitory> list = dormitoryDao.selectByPage(dormitoryQuery);
        Integer totalCount = dormitoryDao.selectTotalCount(dormitoryQuery);
        return LayUITableResult.ok(totalCount, list);
    }
    @Override
    public boolean deleteById(int id) {
        int count = dormitoryDao.deleteById(id);
        return count == 1 ? true : false;
    }

    @Override
    public boolean add(Dormitory dormitory) {
        int count = dormitoryDao.add(dormitory);
        return count == 1 ? true : false;
    }

    @Override
    public Dormitory selectById(int id) {
        return dormitoryDao.selectById(id);
    }

    @Override
    public boolean update(Dormitory dormitory) {
        int count = dormitoryDao.update(dormitory);
        return count == 1 ? true : false;
    }
    

    @Override
    public boolean deleteAll(String[] ids) {

        int count = 0;
        for (String id : ids) {
            count += dormitoryDao.deleteById(Integer.parseInt(id));
        }
        return count == ids.length ? true : false;
    }

    @Override
    public List<Dormitory> selectByBuilding(Integer buildingId) {
        return dormitoryDao.selectByBuilding(buildingId);
    }
}
