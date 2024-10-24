package com.syz.dormitory.dao;

import com.syz.dormitory.pojo.BuildingManager;

public interface IBuildingManagerDao {

    int add(BuildingManager buildingManager);
    int delete(BuildingManager buildingManager);
    int change(BuildingManager buildingManager);
}
