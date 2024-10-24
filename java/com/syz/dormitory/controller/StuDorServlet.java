package com.syz.dormitory.controller;

import com.syz.dormitory.pojo.StuDor;
import com.syz.dormitory.service.IStuDorService;
import com.syz.dormitory.service.impl.StuDorServiceImpl;
import com.syz.dormitory.util.JSONUtil;
import com.syz.dormitory.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/stuDor")
public class StuDorServlet extends HttpServlet {

    IStuDorService stuDorService=new StuDorServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        switch (method) {
            case "add":
                add(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            case "change":
                change(req, resp);
                break;
        }

    }

    private void change(HttpServletRequest req, HttpServletResponse resp) {
        String stuId=req.getParameter("stuId");
        String dorId=req.getParameter("dorId");
        if(stuId==null||stuId.equals("")||dorId==null||dorId.equals("")){
            JSONUtil.toJSON(resp,Result.error("数据不能为空"));
            return;
        }
        StuDor stuDor=new StuDor(Integer.parseInt(stuId),Integer.parseInt(dorId));
        if(stuDorService.change(stuDor)){
            JSONUtil.toJSON(resp, Result.ok("更改成功"));
        }else {
            JSONUtil.toJSON(resp,Result.error("更改失败"));
        }

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        String stuId=req.getParameter("stuId");
        String dorId=req.getParameter("dorId");
        if(stuId==null||stuId.equals("")||dorId==null||dorId.equals("")){
            JSONUtil.toJSON(resp,Result.error("数据不能为空"));
            return;
        }
        StuDor stuDor=new StuDor(Integer.parseInt(stuId),Integer.parseInt(dorId));
        if(stuDorService.delete(stuDor)){
            JSONUtil.toJSON(resp, Result.ok("退宿成功"));
        }else {
            JSONUtil.toJSON(resp,Result.error("退宿失败"));
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        String stuId=req.getParameter("stuId");
        String dorId=req.getParameter("dorId");
        if(stuId==null||stuId.equals("")||dorId==null||dorId.equals("")){
            JSONUtil.toJSON(resp,Result.error("数据不能为空"));
            return;
        }
        StuDor stuDor=new StuDor(Integer.parseInt(stuId),Integer.parseInt(dorId));
        if(stuDorService.add(stuDor)){
            JSONUtil.toJSON(resp, Result.ok("添加成功"));
        }else {
            JSONUtil.toJSON(resp,Result.error("添加失败"));
        }
    }
}
