package com.syz.dormitory.service.impl;



import com.syz.dormitory.dao.IBuildingDao;
import com.syz.dormitory.dao.impl.BuildingDaoImpl;
import com.syz.dormitory.pojo.Building;
import com.syz.dormitory.pojo.query.BuildingAllocation;
import com.syz.dormitory.pojo.query.BuildingQuery;
import com.syz.dormitory.service.IBuildingService;
import com.syz.dormitory.util.LayUITableResult;


import java.util.List;

public class BuildingServiceImpl implements IBuildingService {
    IBuildingDao buildingDao=new BuildingDaoImpl();


    @Override
    public List<BuildingAllocation> queryAllocation() {
        return buildingDao.queryAllocation();
    }

    @Override
    public List<BuildingAllocation> queryStudentNum() {
        return buildingDao.queryStudentNum();
    }

    @Override
    public LayUITableResult selectByPage(BuildingQuery buildingQuery) {
        List<Building> list = buildingDao.selectByPage(buildingQuery);
        Integer totalCount = buildingDao.selectTotalCount(buildingQuery);
        return LayUITableResult.ok(totalCount, list);
    }
    @Override
    public boolean deleteById(int id) {
        int count = buildingDao.deleteById(id);
        return count == 1 ? true : false;
    }

    @Override
    public boolean add(Building building) {
        int count = buildingDao.add(building);
        return count == 1 ? true : false;
    }

    @Override
    public Building selectById(int id) {
        return buildingDao.selectById(id);
    }

    @Override
    public boolean update(Building building) {
        int count = buildingDao.update(building);
        return count == 1 ? true : false;
    }


    @Override
    public boolean deleteAll(String[] ids) {

        int count = 0;
        for (String id : ids) {
            count += buildingDao.deleteById(Integer.parseInt(id));
        }
        return count == ids.length ? true : false;
    }

    @Override
    public List<Building> selectAll() {
        return buildingDao.selectAll();
    }
}
