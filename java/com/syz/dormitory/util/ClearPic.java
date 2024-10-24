package com.syz.dormitory.util;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClearPic {
    public static void main(String arg[]){
        List<String> imageL=new ArrayList<>();
        List<String> localPic=new ArrayList<>();
        ResultSet resultSet = null;
        int countnull=0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            System.out.println("开始遍历manager表");
            String sql = "select image from manager";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String res= resultSet.getString("image");
                if(res!=null&&!res.equals("")){
                    imageL.add(res);
                }else{
                    countnull++;
                }
            }
            System.out.println("添加manager数据完毕");

        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            System.err.println("加载数据库出错，程序退出");
            return ;
        }
        System.out.println("一共"+countnull+"个空数据  一共"+imageL.size()+"条非空数据：");
        for(String s:imageL){
            System.out.println(s);
        }

        System.out.println("\n\n开始清理>>>>\n\n");
        String folderPath = "D:\\学习\\...中享培训\\WP\\SituWork\\DormitorySystem\\managerPic"; // 修改为你要遍历的文件夹路径
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            localPic= traverseFolder(folder);
            System.out.println(folderPath+"  文件夹遍历完毕");
        } else {
            System.err.println("指定的路径"+folderPath+"不是一个存在的文件夹，程序退出");
            return ;
        }
        for(String pic:localPic){
            if(!imageL.contains(pic)){
                System.out.print("发现本地图片"+pic+"不在数据库中，准备删除...");
                if(deleteFile(folderPath+"\\"+pic)){
                    System.out.println("删除成功");
                }else {
                    System.out.println("删除失败");
                }
            }
        }
        System.out.println("------------执行完毕-------------");
    }

    public static ArrayList<String> traverseFolder(File folder) {
        File[] files = folder.listFiles();
        List<String> picname=new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String name=file.getName();
                    //System.out.println(name);
                    picname.add(name);
                }
            }
        }
        return (ArrayList<String>) picname;
    }
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            return file.delete();
        } else {
            System.out.println("文件不存在或不是一个文件");
            return false;
        }
    }
}
