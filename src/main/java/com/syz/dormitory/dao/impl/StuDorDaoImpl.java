package com.syz.dormitory.dao.impl;

import com.syz.dormitory.dao.IStuDorDao;
import com.syz.dormitory.pojo.StuDor;
import com.syz.dormitory.pojo.User;
import com.syz.dormitory.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StuDorDaoImpl implements IStuDorDao {


    @Override
    public int add(StuDor stuDor) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count=0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into stu_dor(stuid,dorid) values (?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, stuDor.getStuId());
            preparedStatement.setInt(2, stuDor.getDorId());
            //System.out.println(preparedStatement);
            count = preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, null);
        }

        return count;
    }

    @Override
    public int delete(StuDor stuDor) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count=0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "DELETE FROM stu_dor WHERE stuid=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, stuDor.getStuId());
            //System.out.println(preparedStatement);
            count = preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, null);
        }

        return count;
    }

    @Override
    public int change(StuDor stuDor) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count=0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "UPDATE stu_dor SET dorid = ? WHERE stuid=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, stuDor.getDorId());
            preparedStatement.setInt(2, stuDor.getStuId());
            //System.out.println(preparedStatement);
            count = preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, null);
        }

        return count;
    }
}
