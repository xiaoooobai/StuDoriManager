package com.syz.dormitory.service;

import com.syz.dormitory.pojo.StuDor;

public interface IStuDorService {
    boolean add(StuDor stuDor);
    boolean change(StuDor stuDor);
    boolean delete(StuDor stuDor);
}
