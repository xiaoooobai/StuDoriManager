package com.syz.dormitory.dao.impl;



import com.syz.dormitory.dao.IUserDao;
import com.syz.dormitory.pojo.User;
import com.syz.dormitory.pojo.query.UserQuery;
import com.syz.dormitory.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements IUserDao {

    @Override
    public User login(String name, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM user where username=? and password=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                int id = resultSet.getInt("id");
                String nickName = resultSet.getString("nickname");
                Integer status=resultSet.getInt("status");
                user = new User(id, name, password, nickName,status);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return user;
    }

    @Override
    public int register(String name, String password, String nickname) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        int count=0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select count(*) as num from user where username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            int n=0;
            while (resultSet.next()){
                if(resultSet.getInt("num")<1){
                    break;
                }
                n++;
            }
            if(n>0){
                return 0;
            }

            sql = "insert into user(username,password,nickname) values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,nickname);
            count = preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, null);
        }
        return count;
    }

    @Override
    public List<User> selectByPage(UserQuery userQuery) {
        int offset = (userQuery.getPage() - 1) * userQuery.getLimit();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> list = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM user where 1=1 ";
            //这里面放所有搜索条件
            List<Object> queryList = new ArrayList<>();
            String queryName = userQuery.getUsername();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and username like ? ";
                queryList.add("%"+queryName+"%");
            }
            sql += " limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < queryList.size(); i++) {
                preparedStatement.setObject(i + 1, queryList.get(i));
            }
            preparedStatement.setInt(queryList.size() + 1, offset);
            preparedStatement.setInt(queryList.size() + 2, userQuery.getLimit());

            //System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//判断下一个有没有，如果返回true而且指向下一个，没有返回false
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String nickname = resultSet.getString("nickname");
                Integer status=resultSet.getInt("status");
                User user = new User(id, username, password, nickname,status);
                list.add(user);
            }
//            for (User user : list) {
//                System.out.println(user);
//            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }

    @Override
    public Integer selectTotalCount(UserQuery userQuery) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int totalCount = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select count(*) from user where 1=1 ";
            List<Object> queryList = new ArrayList<>();
            String queryName = userQuery.getUsername();
            if (queryName != null && !"".equals(queryName)) {
                sql += " and username like ? ";
                queryList.add("%"+queryName+"%");
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
            String sql = "delete from user where id=?";
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
    public int updateStatus(int id, int status) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update user set status=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, id);
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
    public int changePassword(int userId, String oldPassword, String newPassword) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update user set password=? where id=? and password=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, oldPassword);
            //System.out.println(preparedStatement);
            count = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.close(connection, preparedStatement, null);
        }
        return count;
    }
}
