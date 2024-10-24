package com.syz.dormitory.dao;

import com.syz.dormitory.pojo.StuDor;

public interface IStuDorDao {

    int add(StuDor stuDor);
    int delete(StuDor stuDor);
    int change(StuDor stuDor);
}
