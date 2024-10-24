package com.syz.dormitory.service.impl;

import com.syz.dormitory.dao.IBuildingManagerDao;
import com.syz.dormitory.dao.impl.BuildingManagerDaoImpl;
import com.syz.dormitory.pojo.BuildingManager;
import com.syz.dormitory.service.IBuildingManagerService;

public class BuildingManagerServiceImpl implements IBuildingManagerService {
    IBuildingManagerDao buildingManagerDao=new BuildingManagerDaoImpl();
    @Override
    public boolean add(BuildingManager buildingManager) {
        return buildingManagerDao.add(buildingManager)>0?true:false;
    }

    @Override
    public boolean change(BuildingManager buildingManager) {
        return buildingManagerDao.change(buildingManager)>0?true:false;
    }

    @Override
    public boolean delete(BuildingManager buildingManager) {
        return buildingManagerDao.delete(buildingManager)>0?true:false;
    }
}
