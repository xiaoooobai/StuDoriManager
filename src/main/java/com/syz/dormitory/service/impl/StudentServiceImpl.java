package com.syz.dormitory.service.impl;


import com.syz.dormitory.dao.IStudentDao;
import com.syz.dormitory.dao.impl.StudentDaoImpl;
import com.syz.dormitory.pojo.Student;
import com.syz.dormitory.pojo.query.StudentQuery;
import com.syz.dormitory.service.IStudentService;
import com.syz.dormitory.util.LayUITableResult;

import java.util.List;

public class StudentServiceImpl implements IStudentService {

    IStudentDao studentDao=new StudentDaoImpl();
    @Override
    public int[] queryGender() {
        return studentDao.queryGender();
    }
    @Override
    public LayUITableResult selectByPage(StudentQuery studentQuery) {
        List<Student> list = studentDao.selectByPage(studentQuery);
        Integer totalCount = studentDao.selectTotalCount(studentQuery);
        return LayUITableResult.ok(totalCount, list);
    }
    @Override
    public boolean deleteById(int id) {
        int count = studentDao.deleteById(id);
        return count == 1 ? true : false;
    }

    @Override
    public boolean add(Student student) {
        int count = studentDao.add(student);
        return count == 1 ? true : false;
    }

    @Override
    public Student selectById(int id) {
        return studentDao.selectById(id);
    }

    @Override
    public boolean update(Student student) {
        int count = studentDao.update(student);
        return count == 1 ? true : false;
    }
    

    @Override
    public boolean deleteAll(String[] ids) {

        int count = 0;
        for (String id : ids) {
            count += studentDao.deleteById(Integer.parseInt(id));
        }
        return count == ids.length ? true : false;
    }

    @Override
    public LayUITableResult selectHasCheckIn(StudentQuery studentQuery) {
        List<Student> list = studentDao.selectHasCheckIn(studentQuery);
        Integer totalCount = studentDao.countHasCheckIn(studentQuery);
        return LayUITableResult.ok(totalCount, list);
    }

    @Override
    public LayUITableResult selectNotCheckIn(StudentQuery studentQuery) {
        List<Student> list = studentDao.selectNotCheckIn(studentQuery);
        Integer totalCount = studentDao.countNotCheckIn(studentQuery);
        return LayUITableResult.ok(totalCount, list);
    }

    @Override
    public int countHasCheckIn(StudentQuery studentQuery) {
        return studentDao.countHasCheckIn(studentQuery);
    }

    @Override
    public int countNotCheckIn(StudentQuery studentQuery) {
        return studentDao.countNotCheckIn(studentQuery);
    }
}
