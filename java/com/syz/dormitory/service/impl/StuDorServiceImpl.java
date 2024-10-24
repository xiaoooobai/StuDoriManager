package com.syz.dormitory.service.impl;

import com.syz.dormitory.dao.IStuDorDao;
import com.syz.dormitory.dao.impl.StuDorDaoImpl;
import com.syz.dormitory.pojo.StuDor;
import com.syz.dormitory.service.IStuDorService;

public class StuDorServiceImpl implements IStuDorService {
    IStuDorDao stuDorDao=new StuDorDaoImpl();
    @Override
    public boolean add(StuDor stuDor) {
        return stuDorDao.add(stuDor)>0?true:false;
    }

    @Override
    public boolean change(StuDor stuDor) {
        return stuDorDao.change(stuDor)>0?true:false;
    }

    @Override
    public boolean delete(StuDor stuDor) {
        return stuDorDao.delete(stuDor)>0?true:false;
    }
}
