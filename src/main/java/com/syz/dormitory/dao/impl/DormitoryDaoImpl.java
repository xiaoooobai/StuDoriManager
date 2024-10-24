package com.syz.dormitory.dao.impl;

import com.syz.dormitory.dao.IDormitoryDao;
import com.syz.dormitory.pojo.Dormitory;
import com.syz.dormitory.pojo.query.DormitoryQuery;
import com.syz.dormitory.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DormitoryDaoImpl implements IDormitoryDao {

    @Override
    public List<Dormitory> selectByPage(DormitoryQuery dormitoryQuery) {
        int offset = (dormitoryQuery.getPage() - 1) * dormitoryQuery.getLimit();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Dormitory> list = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM view_dor_building where 1=1 ";
            //这里面放所有搜索条件
            List<Object> queryList = new ArrayList<>();
            String queryName = dormitoryQuery.getTag();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and tag like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryBuildingTag = dormitoryQuery.getBuildingTag();
            if (queryBuildingTag != null && !"".equals(queryBuildingTag)) {
                sql += " and building_tag = ? ";
                queryList.add(queryBuildingTag);
            }
            sql += " limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < queryList.size(); i++) {
                preparedStatement.setObject(i + 1, queryList.get(i));
            }
            preparedStatement.setInt(queryList.size() + 1, offset);
            preparedStatement.setInt(queryList.size() + 2, dormitoryQuery.getLimit());

            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                int id = resultSet.getInt("id");
                String tag = resultSet.getString("tag");
                Integer contain = resultSet.getInt("contain");
                String note = resultSet.getString("note");
                Integer buildingId = resultSet.getInt("buildingid");
                String buildingTag = resultSet.getString("building_tag");
                String buildingNote = resultSet.getString("building_note");
                String buildingGender = resultSet.getString("building_gender");
                Dormitory dormitory = new Dormitory(id, tag,contain,note,buildingId,buildingTag,buildingGender,buildingNote);
                list.add(dormitory);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }

    @Override
    public Integer selectTotalCount(DormitoryQuery dormitoryQuery) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int totalCount = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select count(*) from view_dor_building where 1=1 ";
            List<Object> queryList = new ArrayList<>();
            String queryName = dormitoryQuery.getTag();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and tag like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryBuildingTag = dormitoryQuery.getBuildingTag();
            if (queryBuildingTag != null && !"".equals(queryBuildingTag)) {
                sql += " and building_tag like ? ";
                queryList.add("%"+queryBuildingTag+"%");
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
            String sql = "delete from dormitory where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            //System.out.println(preparedStatement);
            count = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }finally {
            JDBCUtil.close(connection, preparedStatement, null);
        }
        return count;
    }

    @Override
    public int add(Dormitory dormitory) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into dormitory(tag,contain,note,buildingid) values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dormitory.getTag());
            preparedStatement.setInt(2, dormitory.getContain());
            preparedStatement.setString(3, dormitory.getNote());
            preparedStatement.setInt(4, dormitory.getBuildingId());
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
    public Dormitory selectById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Dormitory dormitory = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM view_dor_building where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                String tag = resultSet.getString("tag");
                Integer contain = resultSet.getInt("contain");
                String note = resultSet.getString("note");
                Integer buildingId = resultSet.getInt("buildingid");
                String  buildingTag = resultSet.getString("building_tag");
                String  buildingGender = resultSet.getString("building_gender");
                String  buildingNote = resultSet.getString("building_note");
                dormitory = new Dormitory(id,tag,contain,note,buildingId,buildingTag,buildingGender,buildingNote);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return dormitory;
    }

    @Override
    public int update(Dormitory dormitory) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update dormitory set tag=?,contain=?,note=?,buildingid=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dormitory.getTag());
            preparedStatement.setInt(2, dormitory.getContain());
            preparedStatement.setString(3, dormitory.getNote());
            preparedStatement.setInt(4, dormitory.getBuildingId());
            preparedStatement.setInt(5, dormitory.getId());
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
    public List<Dormitory> selectByBuilding(int buildingId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Dormitory> list=new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM dormitory where buildingid=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, buildingId);
            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id=resultSet.getInt("id");
                String tag = resultSet.getString("tag");
                Integer contain = resultSet.getInt("contain");
                String note = resultSet.getString("note");
                Dormitory dormitory = new Dormitory(id,tag,contain,note);
                list.add(dormitory);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }


}
