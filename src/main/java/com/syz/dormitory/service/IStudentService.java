package com.syz.dormitory.service;


import com.syz.dormitory.pojo.Student;
import com.syz.dormitory.pojo.query.StudentQuery;
import com.syz.dormitory.util.LayUITableResult;

import java.util.List;

//接口里面列出来代表Service能为Controller层提供的功能
public interface IStudentService {

    int[] queryGender();

    LayUITableResult selectByPage(StudentQuery studentQuery);
    
    boolean deleteById(int id);

    boolean add(Student student);

    Student selectById(int id);

    boolean update(Student student);

    boolean deleteAll(String[] ids);

    LayUITableResult selectHasCheckIn(StudentQuery studentQuery);
    LayUITableResult selectNotCheckIn(StudentQuery studentQuery);

    int countHasCheckIn(StudentQuery studentQuery);
    int countNotCheckIn(StudentQuery studentQuery);
}

