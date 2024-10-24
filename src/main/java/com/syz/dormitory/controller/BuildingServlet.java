package com.syz.dormitory.controller;




import com.syz.dormitory.pojo.Building;
import com.syz.dormitory.pojo.query.BuildingQuery;
import com.syz.dormitory.service.IBuildingService;
import com.syz.dormitory.service.IManagerService;
import com.syz.dormitory.service.impl.BuildingServiceImpl;
import com.syz.dormitory.service.impl.ManagerServiceImpl;
import com.syz.dormitory.util.JSONUtil;
import com.syz.dormitory.util.LayUITableResult;
import com.syz.dormitory.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/building")
public class BuildingServlet extends HttpServlet {
    IBuildingService buildingService=new BuildingServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        switch (method) {
            case "queryAllocation":
                queryAllocation(req, resp);
                break;
            case "queryStudentNum":
                queryStudentNum(req,resp);
                break;
            case "selectByPage":
                selectByPage(req, resp);
                break;
            case "deleteById":
                deleteById(req, resp);
                break;
            case "deleteAll":
                deleteAll(req, resp);
                break;
            case "add":
                add(req, resp);
                break;
            case "getBuildingUpdatePage":
                getBuildingUpdatePage(req, resp);
                break;
            case "update":
                update(req, resp);
                break;
            case "selectAll":
                selectAll(req,resp);
                break;
        }
    }

    private void selectAll(HttpServletRequest req, HttpServletResponse resp) {
        JSONUtil.toJSON(resp,buildingService.selectAll());
    }

    private void queryStudentNum(HttpServletRequest req, HttpServletResponse resp) {
        JSONUtil.toJSON(resp,buildingService.queryStudentNum());
    }

    private void queryAllocation(HttpServletRequest req, HttpServletResponse resp) {
        JSONUtil.toJSON(resp,buildingService.queryAllocation());
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("BuildingServlet.update");
        String id = req.getParameter("id");
        String tag = req.getParameter("tag");
        String roomsMax = req.getParameter("roomsMax");
        String note = req.getParameter("note");
        String gender = req.getParameter("gender");
        Building building = new Building(Integer.parseInt(id),tag,Integer.parseInt(roomsMax),note, gender);
        boolean isSuccess = buildingService.update(building);
        Result result = isSuccess ? Result.ok("修改成功") : Result.error("修改失败");
        JSONUtil.toJSON(resp, result);
    }

    private void getBuildingUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("BuildingServlet.getBuildingUpdatePage");
        String id = req.getParameter("id");
        Building building = buildingService.selectById(Integer.parseInt(id));
        req.setAttribute("building", building);
        req.getRequestDispatcher("Building/building_update.jsp").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("BuildingServlet.add");
        String tag = req.getParameter("tag");
        String roomsMax = req.getParameter("roomsMax");
        String note = req.getParameter("note");
        String gender = req.getParameter("gender");
        Building building = new Building( tag,  Integer.parseInt(roomsMax),note, gender);
        //System.err.println(building);
        boolean isSuccess = buildingService.add(building);
        if(isSuccess){
            JSONUtil.toJSON(resp,Result.ok("添加成功"));
        }else{
            JSONUtil.toJSON(resp,Result.error("添加失败"));
        }
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("BuildingServlet.deleteById");
        String id = req.getParameter("id");
        boolean isSuccess = buildingService.deleteById(Integer.parseInt(id));
        if(isSuccess){
            JSONUtil.toJSON(resp,Result.ok());
        }else{
            JSONUtil.toJSON(resp,Result.error());
        }
    }
    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("BuildingServlet.deleteAll");
        String[] ids = req.getParameterValues("ids[]");
        boolean isSuccess = buildingService.deleteAll(ids);

        Result result = isSuccess ? Result.ok("删除成功") : Result.error("删除失败");
        JSONUtil.toJSON(resp, result);
    }
    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("BuildingServlet.selectByPage");
        String pageStr = req.getParameter("page");
        if (pageStr == null || pageStr.equals("")) {
            pageStr = "1";
        }
        String limitStr = req.getParameter("limit");
        if (limitStr == null || limitStr.equals("")) {
            limitStr = "10";
        }
        int page = Integer.parseInt(pageStr);
        int limit = Integer.parseInt(limitStr);
        String tag = req.getParameter("tag");
        String gender = req.getParameter("gender");
        BuildingQuery buildingQuery = new BuildingQuery(page, limit, tag, gender);

        LayUITableResult layUITableResult = buildingService.selectByPage(buildingQuery);

        JSONUtil.toJSON(resp, layUITableResult);
    }

}
