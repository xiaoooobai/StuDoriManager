package com.syz.dormitory.controller;




import com.syz.dormitory.pojo.Student;
import com.syz.dormitory.pojo.query.StudentQuery;
import com.syz.dormitory.service.IStudentService;
import com.syz.dormitory.service.IStudentService;
import com.syz.dormitory.service.impl.StudentServiceImpl;
import com.syz.dormitory.service.impl.StudentServiceImpl;
import com.syz.dormitory.util.JSONUtil;
import com.syz.dormitory.util.LayUITableResult;
import com.syz.dormitory.util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    IStudentService studentService=new StudentServiceImpl();

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
            case "getStudentUpdatePage":
                getStudentUpdatePage(req, resp);
                break;
            case "update":
                update(req, resp);
                break;
            case "selectHasCheckIn":
                selectHasCheckIn(req,resp);
                break;
            case "selectNotCheckIn":
                selectNotCheckIn(req,resp);
                break;
        }
    }

    private void selectNotCheckIn(HttpServletRequest req, HttpServletResponse resp) {
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
        String major = req.getParameter("major");
        String class_ = req.getParameter("class_");
        StudentQuery studentQuery = new StudentQuery(page, limit, name, gender,major,class_);
        LayUITableResult layUITableResult=studentService.selectNotCheckIn(studentQuery);
        JSONUtil.toJSON(resp,layUITableResult);
    }

    private void selectHasCheckIn(HttpServletRequest req, HttpServletResponse resp) {
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
        String major = req.getParameter("major");
        String class_ = req.getParameter("class_");
        String dormitoryIdstr= req.getParameter("dormitoryId");
        Integer dormitoryId;
        if(dormitoryIdstr==null||dormitoryIdstr.equals("")){
            dormitoryId=null;
        }else {
            dormitoryId=Integer.parseInt(dormitoryIdstr);
        }
        StudentQuery studentQuery = new StudentQuery(page, limit, name, gender,major,class_,dormitoryId);
        LayUITableResult layUITableResult=studentService.selectHasCheckIn(studentQuery);
        JSONUtil.toJSON(resp,layUITableResult);
    }

    private void queryGender(HttpServletRequest req, HttpServletResponse resp) {
        JSONUtil.toJSON(resp,studentService.queryGender());
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("StudentServlet.update");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String major = req.getParameter("major");
        String class_ = req.getParameter("class_");
        String phone = req.getParameter("phone");
        String gender = req.getParameter("gender");
        Student student = new Student(Integer.parseInt(id), name,  Integer.parseInt(age),major,class_,phone, gender);
        boolean isSuccess = studentService.update(student);
        Result result = isSuccess ? Result.ok("修改成功") : Result.error("修改失败");
        JSONUtil.toJSON(resp, result);
    }

    private void getStudentUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("StudentServlet.getStudentUpdatePage");
        String id = req.getParameter("id");
        Student student = studentService.selectById(Integer.parseInt(id));
        req.setAttribute("student", student);
        req.getRequestDispatcher("Student/student_update.jsp").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("StudentServlet.add");
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String major = req.getParameter("major");
        String class_ = req.getParameter("class_");
        String phone = req.getParameter("phone");
        String gender = req.getParameter("gender");
        Student student = new Student( name,  Integer.parseInt(age),major,class_,phone, gender);
        //System.err.println(student);
        boolean isSuccess = studentService.add(student);
        //resp.sendRedirect(req.getContextPath() + "/student");
        if(isSuccess){
            JSONUtil.toJSON(resp,Result.ok("添加成功"));
        }else{
            JSONUtil.toJSON(resp,Result.error("添加失败"));
        }
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("StudentServlet.deleteById");
        String id = req.getParameter("id");
        boolean isSuccess = studentService.deleteById(Integer.parseInt(id));
        if(isSuccess){
            JSONUtil.toJSON(resp,Result.ok());
        }else{
            JSONUtil.toJSON(resp,Result.error());
        }

        //resp.sendRedirect(req.getContextPath() + "/student");
    }
    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("StudentServlet.deleteAll");
        String[] ids = req.getParameterValues("ids[]");
        boolean isSuccess = studentService.deleteAll(ids);

        Result result = isSuccess ? Result.ok("删除成功") : Result.error("删除失败");
        JSONUtil.toJSON(resp, result);
    }
    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("StudentServlet.selectByPage");
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
        String major = req.getParameter("major");
        String class_ = req.getParameter("class_");
        StudentQuery studentQuery = new StudentQuery(page, limit, name, gender,major,class_);

        LayUITableResult layUITableResult = studentService.selectByPage(studentQuery);
        //System.out.println("LayUITableResult: " + layUITableResult);

        JSONUtil.toJSON(resp, layUITableResult);
    }
}
