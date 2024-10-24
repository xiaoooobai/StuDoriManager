package com.syz.dormitory.dao.impl;

import com.syz.dormitory.dao.IStudentDao;
import com.syz.dormitory.pojo.Student;
import com.syz.dormitory.pojo.query.StudentQuery;
import com.syz.dormitory.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements IStudentDao {
    @Override
    public int[] queryGender() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int[] num = new int [2];
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select  sum(case when gender = '男' then 1 else 0 end) man ," +
                    "sum(case when gender = '女' then 1 else 0 end) woman " +
                    "from student";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                int man = resultSet.getInt("man");
                num[0]=man;
                int woman= resultSet.getInt("woman");
                num[1]=woman;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return num;
    }


    @Override
    public List<Student> selectByPage(StudentQuery studentQuery) {
        int offset = (studentQuery.getPage() - 1) * studentQuery.getLimit();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Student> list = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM student where 1=1 ";
            //这里面放所有搜索条件
            List<Object> queryList = new ArrayList<>();
            String queryName = studentQuery.getName();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and name like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryGender = studentQuery.getGender();
            if (queryGender != null && !"".equals(queryGender)) {
                sql += " and gender like ? ";
                queryList.add("%"+queryGender+"%");
            }
            String queryMajor = studentQuery.getMajor();
            if (queryMajor != null && !"".equals(queryMajor)) {
                sql += " and major like ? ";
                queryList.add("%"+queryMajor+"%");
            }
            String queryClass_ = studentQuery.getClass_();
            if (queryClass_ != null && !"".equals(queryClass_)) {
                sql += " and class like ? ";
                queryList.add("%"+queryClass_+"%");
            }
            sql += " limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < queryList.size(); i++) {
                preparedStatement.setObject(i + 1, queryList.get(i));
            }
            preparedStatement.setInt(queryList.size() + 1, offset);
            preparedStatement.setInt(queryList.size() + 2, studentQuery.getLimit());

            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                String major = resultSet.getString("major");
                String class_ = resultSet.getString("class");
                String phone = resultSet.getString("phone");
                String gender = resultSet.getString("gender");
                Student student = new Student(id, name, age,major,class_,phone,gender);
                list.add(student);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }

    @Override
    public Integer selectTotalCount(StudentQuery studentQuery) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int totalCount = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select count(*) from student where 1=1 ";
            List<Object> queryList = new ArrayList<>();
            String queryName = studentQuery.getName();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and name like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryGender = studentQuery.getGender();
            if (queryGender != null && !"".equals(queryGender)) {
                sql += " and gender like? ";
                queryList.add("%"+queryGender+"%");
            }
            String queryMajor = studentQuery.getMajor();
            if (queryMajor != null && !"".equals(queryMajor)) {
                sql += " and major like ? ";
                queryList.add("%"+queryMajor+"%");
            }
            String queryClass_ = studentQuery.getClass_();
            if (queryClass_ != null && !"".equals(queryClass_)) {
                sql += " and class like ? ";
                queryList.add("%"+queryClass_+"%");
            }
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < queryList.size(); i++) {
                preparedStatement.setObject(i + 1, queryList.get(i));
            }
            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                totalCount = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }
        return totalCount;
    }

    @Override
    public int deleteById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "delete from student where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            //System.out.println(preparedStatement);
            count = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.close(connection, preparedStatement, null);
        }
        return count;
    }

    @Override
    public int add(Student student) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into student(name,age,major,class,phone,gender) values(?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setString(3, student.getMajor());
            preparedStatement.setString(4, student.getClass_());
            preparedStatement.setString(5, student.getPhone());
            preparedStatement.setString(6, student.getGender());
            //System.out.println(preparedStatement);
            count = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.close(connection, preparedStatement, null);
        }
        return count;
    }

    @Override
    public Student selectById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Student student = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM student where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                String major = resultSet.getString("major");
                String class_ = resultSet.getString("class");
                String phone = resultSet.getString("phone");
                String gender = resultSet.getString("gender");
                student = new Student(id, name, age,major,class_,phone,gender);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return student;
    }

    @Override
    public int update(Student student) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update student set name=?,age=?,major=?,class=?,phone=?,gender=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setString(3, student.getMajor());
            preparedStatement.setString(4, student.getClass_());
            preparedStatement.setString(5, student.getPhone());
            preparedStatement.setString(6, student.getGender());
            preparedStatement.setInt(7, student.getId());
            //System.out.println(preparedStatement);
            count = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.close(connection, preparedStatement, null);
        }
        return count;
    }

    @Override
    public List<Student> selectHasCheckIn(StudentQuery studentQuery) {
        int offset = (studentQuery.getPage() - 1) * studentQuery.getLimit();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Student> list=new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM view_stu_dor_building where 1=1 ";
            List<Object> queryList = new ArrayList<>();
            String queryName = studentQuery.getName();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and name like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryGender = studentQuery.getGender();
            if (queryGender != null && !"".equals(queryGender)) {
                sql += " and gender like ? ";
                queryList.add("%"+queryGender+"%");
            }
            String queryMajor = studentQuery.getMajor();
            if (queryMajor != null && !"".equals(queryMajor)) {
                sql += " and major like ? ";
                queryList.add("%"+queryMajor+"%");
            }
            String queryClass_ = studentQuery.getClass_();
            if (queryClass_ != null && !"".equals(queryClass_)) {
                sql += " and class like ? ";
                queryList.add("%"+queryClass_+"%");
            }
            Integer dormitoryId = studentQuery.getDormitoryId();
            if (dormitoryId != null && !dormitoryId.equals(0)) {
                sql += " and dor_id = ? ";
                queryList.add(dormitoryId);
            }
            sql += "and dor_id is not null limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < queryList.size(); i++) {
                preparedStatement.setObject(i + 1, queryList.get(i));
            }
            preparedStatement.setInt(queryList.size() + 1, offset);
            preparedStatement.setInt(queryList.size() + 2, studentQuery.getLimit());
            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id=resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                String major = resultSet.getString("major");
                String class_ = resultSet.getString("class");
                String phone = resultSet.getString("phone");
                Integer dorId = resultSet.getInt("dor_id");
                String dorTag = resultSet.getString("dor_tag");
                Integer buildingId = resultSet.getInt("building_id");
                String buildingTag = resultSet.getString("building_tag");
                String gender = resultSet.getString("gender");
                Student student = new Student(id, name, age,major,class_,phone,gender,dorId,dorTag,buildingId,buildingTag);
                list.add(student);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }

    @Override
    public List<Student> selectNotCheckIn(StudentQuery studentQuery) {
        int offset = (studentQuery.getPage() - 1) * studentQuery.getLimit();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Student> list=new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM view_stu_dor_building where 1=1 ";
            List<Object> queryList = new ArrayList<>();
            String queryName = studentQuery.getName();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and name like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryGender = studentQuery.getGender();
            if (queryGender != null && !"".equals(queryGender)) {
                sql += " and gender like ? ";
                queryList.add("%"+queryGender+"%");
            }
            String queryMajor = studentQuery.getMajor();
            if (queryMajor != null && !"".equals(queryMajor)) {
                sql += " and major like ? ";
                queryList.add("%"+queryMajor+"%");
            }
            String queryClass_ = studentQuery.getClass_();
            if (queryClass_ != null && !"".equals(queryClass_)) {
                sql += " and class like ? ";
                queryList.add("%"+queryClass_+"%");
            }
            sql += "and dor_id is  null limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < queryList.size(); i++) {
                preparedStatement.setObject(i + 1, queryList.get(i));
            }
            preparedStatement.setInt(queryList.size() + 1, offset);
            preparedStatement.setInt(queryList.size() + 2, studentQuery.getLimit());

            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id=resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                String major = resultSet.getString("major");
                String class_ = resultSet.getString("class");
                String phone = resultSet.getString("phone");
                String gender = resultSet.getString("gender");
                Student student = new Student(id, name, age,major,class_,phone,gender);
                list.add(student);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }

    @Override
    public int countHasCheckIn(StudentQuery studentQuery) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int totalCount = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT count(*) FROM view_stu_dor_building where 1=1 ";
            List<Object> queryList = new ArrayList<>();
            String queryName = studentQuery.getName();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and name like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryGender = studentQuery.getGender();
            if (queryGender != null && !"".equals(queryGender)) {
                sql += " and gender like ? ";
                queryList.add("%"+queryGender+"%");
            }
            String queryMajor = studentQuery.getMajor();
            if (queryMajor != null && !"".equals(queryMajor)) {
                sql += " and major like ? ";
                queryList.add("%"+queryMajor+"%");
            }
            String queryClass_ = studentQuery.getClass_();
            if (queryClass_ != null && !"".equals(queryClass_)) {
                sql += " and class like ? ";
                queryList.add("%"+queryClass_+"%");
            }
            Integer dormitoryId = studentQuery.getDormitoryId();
            if (dormitoryId != null && !dormitoryId.equals(0)) {
                sql += " and dor_id = ? ";
                queryList.add(dormitoryId);
            }
            sql += "and dor_id is not null ";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement);
            for (int i = 0; i < queryList.size(); i++) {
                preparedStatement.setObject(i + 1, queryList.get(i));
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                totalCount = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }
        return totalCount;
    }

    @Override
    public int countNotCheckIn(StudentQuery studentQuery) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int totalCount = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT count(*) FROM view_stu_dor_building where 1=1 ";
            List<Object> queryList = new ArrayList<>();
            String queryName = studentQuery.getName();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and name like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryGender = studentQuery.getGender();
            if (queryGender != null && !"".equals(queryGender)) {
                sql += " and gender like ? ";
                queryList.add("%"+queryGender+"%");
            }
            String queryMajor = studentQuery.getMajor();
            if (queryMajor != null && !"".equals(queryMajor)) {
                sql += " and major like ? ";
                queryList.add("%"+queryMajor+"%");
            }
            String queryClass_ = studentQuery.getClass_();
            if (queryClass_ != null && !"".equals(queryClass_)) {
                sql += " and class like ? ";
                queryList.add("%"+queryClass_+"%");
            }
            sql += "and dor_id is  null ";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement);
            for (int i = 0; i < queryList.size(); i++) {
                preparedStatement.setObject(i + 1, queryList.get(i));
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                totalCount = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }
        return totalCount;
    }


}
