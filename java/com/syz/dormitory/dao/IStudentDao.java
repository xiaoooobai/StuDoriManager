package com.syz.dormitory.dao;


import com.syz.dormitory.pojo.Student;
import com.syz.dormitory.pojo.query.StudentQuery;

import java.util.List;

public interface IStudentDao {

    int[] queryGender();
    List<Student> selectByPage(StudentQuery studentQuery);
    Integer selectTotalCount(StudentQuery studentQuery);


    int deleteById(int id);

    int add(Student student);

    Student selectById(int id);

    int update(Student student);

    List<Student> selectHasCheckIn(StudentQuery studentQuery);
    List<Student> selectNotCheckIn(StudentQuery studentQuery);
    int countHasCheckIn(StudentQuery studentQuery);
    int countNotCheckIn(StudentQuery studentQuery);
}
