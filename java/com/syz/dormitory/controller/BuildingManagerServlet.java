package com.syz.dormitory.controller;

import com.syz.dormitory.pojo.BuildingManager;
import com.syz.dormitory.service.IBuildingManagerService;
import com.syz.dormitory.service.impl.BuildingManagerServiceImpl;
import com.syz.dormitory.util.JSONUtil;
import com.syz.dormitory.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/buildingManager")
public class BuildingManagerServlet extends HttpServlet {

    IBuildingManagerService buildingManagerService=new BuildingManagerServiceImpl();
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
        String buildingId=req.getParameter("buildingId");
        String managerId=req.getParameter("managerId");
        if(buildingId==null||buildingId.equals("")||managerId==null||managerId.equals("")){
            JSONUtil.toJSON(resp,Result.error("数据不能为空"));
            return;
        }
        BuildingManager buildingManager=new BuildingManager(Integer.parseInt(buildingId),Integer.parseInt(managerId));
        if(buildingManagerService.change(buildingManager)){
            JSONUtil.toJSON(resp, Result.ok("更改成功"));
        }else {
            JSONUtil.toJSON(resp,Result.error("更改失败"));
        }

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        String buildingId=req.getParameter("buildingId");
        if(buildingId==null||buildingId.equals("")){
            JSONUtil.toJSON(resp,Result.error("数据不能为空"));
            return;
        }
        BuildingManager buildingManager=new BuildingManager(Integer.parseInt(buildingId));
        if(buildingManagerService.delete(buildingManager)){
            JSONUtil.toJSON(resp, Result.ok("删除成功"));
        }else {
            JSONUtil.toJSON(resp,Result.error("删除失败"));
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        String buildingId=req.getParameter("buildingId");
        String managerId=req.getParameter("managerId");
        if(buildingId==null||buildingId.equals("")||managerId==null||managerId.equals("")){
            JSONUtil.toJSON(resp,Result.error("数据不能为空"));
            return;
        }
        BuildingManager buildingManager=new BuildingManager(Integer.parseInt(buildingId),Integer.parseInt(managerId));
        if(buildingManagerService.add(buildingManager)){
            JSONUtil.toJSON(resp, Result.ok("添加成功"));
        }else {
            JSONUtil.toJSON(resp,Result.error("添加失败"));
        }
    }
}
