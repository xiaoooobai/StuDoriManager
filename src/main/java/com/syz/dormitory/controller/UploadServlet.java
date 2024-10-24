package com.syz.dormitory.controller;

import com.syz.dormitory.util.JSONUtil;
import com.syz.dormitory.util.Result;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("UploadServlet.service");
        //1.DiskFileItemFactory磁盘文件项工厂：一些配置的设置，缓存的大小，临时文件位置
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 1M=1024KB  1KB=1024B
        //设置缓存大小
        factory.setSizeThreshold(10 * 1024 * 1024);
        String tempPath = getServletContext().getRealPath("temp");
        //设置临时文件
        factory.setRepository(new File(tempPath));

        //2.文件上传的核心类
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        //设置文件上传名的编码方式
        servletFileUpload.setHeaderEncoding("UTF-8");
        String fileName = "";
        if (ServletFileUpload.isMultipartContent(req)) {
            try {
                List<FileItem> fileItems = servletFileUpload.parseRequest(req);
                if (fileItems != null) {
                    for (FileItem fileItem : fileItems) {
                        //如果是普通表单项目: name:zhansgan
                        if (!fileItem.isFormField()) {//文件上传
                            //fileItem 封装了前台传递过来的文件上传相关的信息
                            //abc.png
                            String name = fileItem.getName();
                            //png
                            String extension = FilenameUtils.getExtension(name);
                            //851fee51ae5d4baf9b7b32aefac508c1
                            String uuid = UUID.randomUUID().toString().replace("-", "");
                            //851fee51ae5d4baf9b7b32aefac508c1.png
                            fileName = uuid + "." + extension;
                            InputStream inputStream = fileItem.getInputStream();
                            OutputStream outputStream = new FileOutputStream("D:\\学习\\...中享培训\\WP\\SituWork\\DormitorySystem\\managerPic\\" + fileName);
                            IOUtils.copy(inputStream, outputStream);
                            outputStream.close();
                            inputStream.close();
                            //删除临时文件
                            fileItem.delete();
                        }
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }

        JSONUtil.toJSON(resp, Result.ok("上传成功", fileName));
    }


}
