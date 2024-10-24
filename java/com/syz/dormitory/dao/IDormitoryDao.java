package com.syz.dormitory.dao;


import com.syz.dormitory.pojo.Dormitory;
import com.syz.dormitory.pojo.query.DormitoryQuery;

import java.util.List;

public interface IDormitoryDao {

    List<Dormitory> selectByPage(DormitoryQuery dormitoryQuery);

    Integer selectTotalCount(DormitoryQuery dormitoryQuery);


    int deleteById(int id);

    int add(Dormitory dormitory);

    Dormitory selectById(int id);

    int update(Dormitory dormitory);

    List<Dormitory> selectByBuilding(int id);
}
