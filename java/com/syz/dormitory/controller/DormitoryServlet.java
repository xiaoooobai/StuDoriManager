package com.syz.dormitory.controller;




import com.syz.dormitory.pojo.Dormitory;
import com.syz.dormitory.pojo.query.DormitoryQuery;
import com.syz.dormitory.service.IDormitoryService;
import com.syz.dormitory.service.impl.DormitoryServiceImpl;
import com.syz.dormitory.util.JSONUtil;
import com.syz.dormitory.util.LayUITableResult;
import com.syz.dormitory.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/dormitory")
public class DormitoryServlet extends HttpServlet {
    IDormitoryService dormitoryService=new DormitoryServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        switch (method) {
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
            case "getDormitoryUpdatePage":
                getDormitoryUpdatePage(req, resp);
                break;
            case "update":
                update(req, resp);
                break;
            case "selectByBuilding":
                selectByBuilding(req,resp);
                break;
        }
    }

    private void selectByBuilding(HttpServletRequest req, HttpServletResponse resp) {
        String buildingId=req.getParameter("buildingId");
        JSONUtil.toJSON(resp,dormitoryService.selectByBuilding(Integer.parseInt(buildingId)));
    }


    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("DormitoryServlet.update");
        String id = req.getParameter("id");
        String tag = req.getParameter("tag");
        String contain = req.getParameter("contain");
        String note = req.getParameter("note");
        String buildingId = req.getParameter("buildingId");
        Dormitory dormitory = new Dormitory(Integer.parseInt(id), tag,Integer.parseInt(contain),note,Integer.parseInt(buildingId));
        boolean isSuccess = dormitoryService.update(dormitory);
        Result result = isSuccess ? Result.ok("修改成功") : Result.error("修改失败");
        JSONUtil.toJSON(resp, result);
    }

    private void getDormitoryUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("DormitoryServlet.getDormitoryUpdatePage");
        String id = req.getParameter("id");
        Dormitory dormitory = dormitoryService.selectById(Integer.parseInt(id));
        req.setAttribute("dormitory", dormitory);
        req.getRequestDispatcher("Dormitory/dormitory_update.jsp").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("DormitoryServlet.add");
        String tag = req.getParameter("tag");
        String contain = req.getParameter("contain");
        String note = req.getParameter("note");
        String buildingId = req.getParameter("buildingId");
        if(buildingId.equals("")||buildingId==null){
            JSONUtil.toJSON(resp,Result.error("添加数据不能为空"));
            return;
        }
        Dormitory dormitory = new Dormitory( tag,Integer.parseInt(contain),note,Integer.parseInt(buildingId));
        boolean isSuccess = dormitoryService.add(dormitory);

        if(isSuccess){
            JSONUtil.toJSON(resp,Result.ok("添加成功"));
        }else{
            JSONUtil.toJSON(resp,Result.error("添加失败"));
        }
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("DormitoryServlet.deleteById");
        String id = req.getParameter("id");
        boolean isSuccess = dormitoryService.deleteById(Integer.parseInt(id));
        if(isSuccess){
            JSONUtil.toJSON(resp,Result.ok());
        }else{
            JSONUtil.toJSON(resp,Result.error());
        }

    }
    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("DormitoryServlet.deleteAll");
        String[] ids = req.getParameterValues("ids[]");
        boolean isSuccess = dormitoryService.deleteAll(ids);

        Result result = isSuccess ? Result.ok("删除成功") : Result.error("删除失败");
        JSONUtil.toJSON(resp, result);
    }
    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("DormitoryServlet.selectByPage");
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
        String buildingTag = req.getParameter("buildingTag");
        DormitoryQuery dormitoryQuery = new DormitoryQuery(page, limit, tag,buildingTag);

        LayUITableResult layUITableResult = dormitoryService.selectByPage(dormitoryQuery);

        JSONUtil.toJSON(resp, layUITableResult);
    }
}
