package com.syz.dormitory.service;

import com.syz.dormitory.pojo.BuildingManager;

public interface IBuildingManagerService {
    boolean add(BuildingManager buildingManager);
    boolean change(BuildingManager buildingManager);
    boolean delete(BuildingManager buildingManager);
}
