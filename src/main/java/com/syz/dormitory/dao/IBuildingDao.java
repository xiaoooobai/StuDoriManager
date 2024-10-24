package com.syz.dormitory.dao;


import com.syz.dormitory.pojo.Building;
import com.syz.dormitory.pojo.query.BuildingAllocation;
import com.syz.dormitory.pojo.query.BuildingQuery;

import java.util.List;

public interface IBuildingDao {

    List<BuildingAllocation> queryAllocation();

    List<BuildingAllocation> queryStudentNum();

    List<Building> selectByPage(BuildingQuery buildingQuery);
    Integer selectTotalCount(BuildingQuery buildingQuery);


    int deleteById(int id);

    int add(Building building);

    Building selectById(int id);

    int update(Building building);

    List<Building> selectAll();

}
