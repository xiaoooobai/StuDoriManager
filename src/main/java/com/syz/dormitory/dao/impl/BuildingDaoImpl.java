package com.syz.dormitory.dao.impl;


import com.syz.dormitory.dao.IBuildingDao;
import com.syz.dormitory.pojo.Building;
import com.syz.dormitory.pojo.query.BuildingAllocation;
import com.syz.dormitory.pojo.query.BuildingQuery;
import com.syz.dormitory.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildingDaoImpl implements IBuildingDao {

    @Override
    public List<BuildingAllocation> queryAllocation() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<BuildingAllocation> list=new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from view_building_allocation order by tag desc";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                int id = resultSet.getInt("id");
                String tag=resultSet.getString("tag");
                int roomsMax=resultSet.getInt("rooms_max");
                int allocation=resultSet.getInt("has_allocation");
                BuildingAllocation buildingAllocation=new BuildingAllocation(id,tag,roomsMax,allocation);
                list.add(buildingAllocation);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }

    @Override
    public List<BuildingAllocation> queryStudentNum() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<BuildingAllocation> list=new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT *,(select count(*) from view_stu_dor_building as b where b.building_id=a.id) as building_num FROM dormitory_system.building as a order by tag asc";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                int id = resultSet.getInt("id");
                String tag=resultSet.getString("tag");
                int buildingNum=resultSet.getInt("building_num");
                BuildingAllocation buildingAllocation=new BuildingAllocation(id,tag,buildingNum);
                list.add(buildingAllocation);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }



    @Override
    public List<Building> selectByPage(BuildingQuery buildingQuery) {
        int offset = (buildingQuery.getPage() - 1) * buildingQuery.getLimit();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Building> list = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM view_building_manager where 1=1 ";
            //这里面放所有搜索条件
            List<Object> queryList = new ArrayList<>();
            String queryName = buildingQuery.getTag();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and tag like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryGender = buildingQuery.getGender();
            if (queryGender != null && !"".equals(queryGender)) {
                sql += " and gender like ? ";
                queryList.add("%"+queryGender+"%");
            }

            sql += " limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < queryList.size(); i++) {
                preparedStatement.setObject(i + 1, queryList.get(i));
            }
            preparedStatement.setInt(queryList.size() + 1, offset);
            preparedStatement.setInt(queryList.size() + 2, buildingQuery.getLimit());

            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                int id = resultSet.getInt("id");
                String tag = resultSet.getString("tag");
                Integer roomsMax = resultSet.getInt("rooms_max");
                String note = resultSet.getString("note");
                String gender = resultSet.getString("gender");
                String managerName=resultSet.getString("manager_name");
                String managerGender=resultSet.getString("manager_gender");
                Building building = new Building(id, tag,roomsMax,note,gender,managerName,managerGender);
                list.add(building);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }

    @Override
    public Integer selectTotalCount(BuildingQuery buildingQuery) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int totalCount = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select count(*) from building where 1=1 ";
            List<Object> queryList = new ArrayList<>();
            String queryName = buildingQuery.getTag();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and tag like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryGender = buildingQuery.getGender();
            if (queryGender != null && !"".equals(queryGender)) {
                sql += " and gender like? ";
                queryList.add("%"+queryGender+"%");
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
            String sql = "delete from building where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            //System.out.println(preparedStatement);
            count = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
        return count;
    }

    @Override
    public int add(Building building) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into building(tag,rooms_max,note,gender) values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, building.getTag());
            preparedStatement.setInt(2, building.getRoomsMax());
            preparedStatement.setString(3, building.getNote());
            preparedStatement.setString(4, building.getGender());
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
    public Building selectById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Building building = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM building where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                String tag = resultSet.getString("tag");
                Integer roomsMax = resultSet.getInt("rooms_max");
                String note = resultSet.getString("note");
                String gender = resultSet.getString("gender");
                building = new Building(id, tag,roomsMax,note,gender);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return building;
    }

    @Override
    public int update(Building building) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update building set tag=?,rooms_max=?,note=?,gender=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, building.getTag());
            preparedStatement.setInt(2, building.getRoomsMax());
            preparedStatement.setString(3, building.getNote());
            preparedStatement.setString(4, building.getGender());
            preparedStatement.setInt(5, building.getId());
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
    public List<Building> selectAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        List<Building> list=new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from building";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id=resultSet.getInt("id");
                String tag = resultSet.getString("tag");
                Integer roomsMax = resultSet.getInt("rooms_max");
                String note = resultSet.getString("note");
                String gender = resultSet.getString("gender");
                Building building = new Building(id, tag,roomsMax,note,gender);
                list.add(building);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }
        return list;
    }
}
