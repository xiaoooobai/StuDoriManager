package com.syz.dormitory.service;


import com.syz.dormitory.pojo.Building;
import com.syz.dormitory.pojo.Dormitory;
import com.syz.dormitory.pojo.query.DormitoryQuery;
import com.syz.dormitory.util.LayUITableResult;

import java.util.List;

//接口里面列出来代表Service能为Controller层提供的功能
public interface IDormitoryService {


    LayUITableResult selectByPage(DormitoryQuery dormitoryQuery);
    
    boolean deleteById(int id);

    boolean add(Dormitory dormitory);

    Dormitory selectById(int id);

    boolean update(Dormitory dormitory);

    boolean deleteAll(String[] ids);
    List<Dormitory> selectByBuilding(Integer buildingId);
}

