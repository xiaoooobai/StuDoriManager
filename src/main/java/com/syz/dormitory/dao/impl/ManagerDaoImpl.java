package com.syz.dormitory.dao.impl;


import com.syz.dormitory.dao.IManagerDao;
import com.syz.dormitory.pojo.Manager;
import com.syz.dormitory.pojo.query.ManagerQuery;
import com.syz.dormitory.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerDaoImpl implements IManagerDao {
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
                    "from manager";
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
    public List<Manager> selectByPage(ManagerQuery managerQuery) {
        int offset = (managerQuery.getPage() - 1) * managerQuery.getLimit();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Manager> list = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM manager where 1=1 ";
            //这里面放所有搜索条件
            List<Object> queryList = new ArrayList<>();
            String queryName = managerQuery.getName();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and name like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryGender = managerQuery.getGender();
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
            preparedStatement.setInt(queryList.size() + 2, managerQuery.getLimit());

            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                String image=resultSet.getString("image");
                Manager manager = new Manager(id, name,gender,image);
                list.add(manager);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }

    @Override
    public Integer selectTotalCount(ManagerQuery managerQuery) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int totalCount = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select count(*) from manager where 1=1 ";
            List<Object> queryList = new ArrayList<>();
            String queryName = managerQuery.getName();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and name like ? ";
                queryList.add("%"+queryName+"%");
            }
            String queryGender = managerQuery.getGender();
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
            String sql = "delete from manager where id=?";
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
    public int add(Manager manager) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "insert into manager(name,gender,image) values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, manager.getName());
            preparedStatement.setString(2, manager.getGender());
            preparedStatement.setString(3, manager.getImage());
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
    public Manager selectById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Manager manager = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM manager where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                String image=resultSet.getString("image");
                manager = new Manager(id, name,gender,image);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return manager;
    }

    @Override
    public int update(Manager manager) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update manager set name=?,gender=?,image=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, manager.getName());
            preparedStatement.setString(2, manager.getGender());
            preparedStatement.setString(3, manager.getImage());
            preparedStatement.setInt(4, manager.getId());
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
    public List<Manager> selectAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Manager> list = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM manager where 1=1 ";
            preparedStatement = connection.prepareStatement(sql);
           // System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                Manager manager = new Manager(id, name,gender);
                list.add(manager);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }

}
