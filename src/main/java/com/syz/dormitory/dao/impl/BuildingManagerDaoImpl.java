package com.syz.dormitory.dao.impl;

import com.syz.dormitory.dao.IBuildingManagerDao;
import com.syz.dormitory.pojo.BuildingManager;
import com.syz.dormitory.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BuildingManagerDaoImpl implements IBuildingManagerDao {


    @Override
    public int add(BuildingManager buildingManager) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count=0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into building_manager(buildingid,managerid) values (?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, buildingManager.getBuildingId());
            preparedStatement.setInt(2, buildingManager.getManagerId());
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
    public int delete(BuildingManager buildingManager) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count=0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "DELETE FROM building_manager WHERE buildingId=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, buildingManager.getBuildingId());
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
    public int change(BuildingManager buildingManager) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count=0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "UPDATE building_manager SET managerId = ? WHERE buildingId=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, buildingManager.getManagerId());
            preparedStatement.setInt(2, buildingManager.getBuildingId());
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
