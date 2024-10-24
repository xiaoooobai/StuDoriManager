package com.syz.dormitory.controller;




import com.syz.dormitory.pojo.Manager;
import com.syz.dormitory.pojo.query.ManagerQuery;
import com.syz.dormitory.service.IManagerService;
import com.syz.dormitory.service.IManagerService;
import com.syz.dormitory.service.impl.ManagerServiceImpl;
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


@WebServlet("/manager")
public class ManagerServlet extends HttpServlet {
    IManagerService managerService=new ManagerServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        switch (method) {
            case "queryGender":
                queryGender(req, resp);
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
            case "getManagerUpdatePage":
                getManagerUpdatePage(req, resp);
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
        JSONUtil.toJSON(resp,managerService.selectAll());
    }

    private void queryGender(HttpServletRequest req, HttpServletResponse resp) {
        JSONUtil.toJSON(resp,managerService.queryGender());
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("ManagerServlet.update");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String image=req.getParameter("image");
        Manager manager = new Manager(Integer.parseInt(id), name,  gender,image);
        boolean isSuccess = managerService.update(manager);
        Result result = isSuccess ? Result.ok("修改成功") : Result.error("修改失败");
        JSONUtil.toJSON(resp, result);
    }

    private void getManagerUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("ManagerServlet.getManagerUpdatePage");
        String id = req.getParameter("id");
        Manager manager = managerService.selectById(Integer.parseInt(id));
        req.setAttribute("manager", manager);
        req.getRequestDispatcher("Manager/manager_update.jsp").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("ManagerServlet.add");
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String image=req.getParameter("image");
        Manager manager = new Manager( name, gender,image);
        //System.err.println(manager);
        boolean isSuccess = managerService.add(manager);
        //resp.sendRedirect(req.getContextPath() + "/manager");
        if(isSuccess){
            JSONUtil.toJSON(resp,Result.ok("添加成功"));
        }else{
            JSONUtil.toJSON(resp,Result.error("添加失败"));
        }
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("ManagerServlet.deleteById");
        String id = req.getParameter("id");
        boolean isSuccess = managerService.deleteById(Integer.parseInt(id));
        if(isSuccess){
            JSONUtil.toJSON(resp,Result.ok());
        }else{
            JSONUtil.toJSON(resp,Result.error());
        }

        //resp.sendRedirect(req.getContextPath() + "/manager");
    }
    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("ManagerServlet.deleteAll");
        String[] ids = req.getParameterValues("ids[]");
        boolean isSuccess = managerService.deleteAll(ids);

        Result result = isSuccess ? Result.ok("删除成功") : Result.error("删除失败");
        JSONUtil.toJSON(resp, result);
    }
    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("ManagerServlet.selectByPage");
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
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        ManagerQuery managerQuery = new ManagerQuery(page, limit, name, gender);

        LayUITableResult layUITableResult = managerService.selectByPage(managerQuery);
        //System.out.println("LayUITableResult: " + layUITableResult);

        JSONUtil.toJSON(resp, layUITableResult);
    }

}
