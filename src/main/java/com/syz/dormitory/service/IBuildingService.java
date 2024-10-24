package com.syz.dormitory.service;


import com.syz.dormitory.pojo.Building;
import com.syz.dormitory.pojo.query.BuildingAllocation;
import com.syz.dormitory.pojo.query.BuildingQuery;
import com.syz.dormitory.util.LayUITableResult;

import java.util.List;

//接口里面列出来代表Service能为Controller层提供的功能
public interface IBuildingService {

    List<BuildingAllocation> queryAllocation();
    List<BuildingAllocation> queryStudentNum();

    LayUITableResult selectByPage(BuildingQuery buildingQuery);

    boolean deleteById(int id);

    boolean add(Building building);

    Building selectById(int id);

    boolean update(Building building);

    boolean deleteAll(String[] ids);

    List<Building> selectAll();
}

