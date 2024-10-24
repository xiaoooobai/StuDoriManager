package com.syz.dormitory.controller;




import com.syz.dormitory.pojo.User;
import com.syz.dormitory.pojo.query.UserQuery;
import com.syz.dormitory.service.IUserService;
import com.syz.dormitory.service.impl.UserServiceImpl;
import com.syz.dormitory.util.JSONUtil;
import com.syz.dormitory.util.LayUITableResult;
import com.syz.dormitory.util.MD5Util;
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


@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private IUserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        switch (method) {
            case "login":
                login(req, resp);
                break;
            case "logout":
                logout(req, resp);
                break;
            case "register":
                register(req,resp);
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
            case "updateStatus":
                updateStatus(req, resp);
                break;
            case "changePassword":
                changePassword(req,resp);
        }
    }

    private void changePassword(HttpServletRequest req, HttpServletResponse resp) {
        String userId=req.getParameter("userId");
        String oriOldPassword=req.getParameter("oldPassword");
        String oriNewPassword=req.getParameter("newPassword");
        String oldPassword=MD5Util.MD5Encode(oriOldPassword);
        String newPassword=MD5Util.MD5Encode(oriNewPassword);
        if(userService.changePassword(Integer.parseInt(userId),oldPassword,newPassword)){
            JSONUtil.toJSON(resp,Result.ok("更改成功"));
        }else {
            JSONUtil.toJSON(resp,Result.error("更改失败，可能原密码不正确"));
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) {
        //System.out.println("UserServlet.register");
        String username=req.getParameter("username");
        String oriPassword=req.getParameter("password");
        String nickname=req.getParameter("nickname");
        String password= MD5Util.MD5Encode(oriPassword);
        if(userService.register(username,password,nickname)){
            JSONUtil.toJSON(resp,Result.ok("注册成功"));
        }else {
            JSONUtil.toJSON(resp,Result.error("注册失败，可能此用户名已经注册"));
        }
    }


    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("UserServlet.logout");
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/User/login.jsp");
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("UserServlet.login");
        String name = req.getParameter("username");
        String oriPassword = req.getParameter("password");
        String code = req.getParameter("code");
        String password=MD5Util.MD5Encode(oriPassword);
        HttpSession session = req.getSession();
        String codeInSession = (String) session.getAttribute("codeInSession");

        if (!codeInSession.equalsIgnoreCase(code)) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", 1);
            map.put("msg", "验证码错误");
            JSONUtil.toJSON(resp, map);
            return;
        }


        User user = userService.login(name, password );
        if (user != null) {
            //先判断状态
            if (user.getStatus() != 0) {
                JSONUtil.toJSON(resp, Result.error("该用户被禁用"));
                return;
            }

            //把user作为登录凭证放到Session，
            //后面只要判断Session里面有没有user，就知道当前这个用户有没有登录
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(0); //设置会话时长
            JSONUtil.toJSON(resp, Result.ok("登录成功"));
        } else {
            JSONUtil.toJSON(resp, Result.error("用户名或密码错误"));
        }
    }

    private void selectByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("UserServlet.selectByPage");
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
        String name = req.getParameter("username");
        UserQuery userQuery = new UserQuery(page, limit, name);

        LayUITableResult layUITableResult = userService.selectByPage(userQuery);
        //System.out.println("LayUITableResult: " + layUITableResult);

        JSONUtil.toJSON(resp, layUITableResult);
    }
    private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("UserServlet.deleteById");
        String id = req.getParameter("id");
        boolean isSuccess = userService.deleteById(Integer.parseInt(id));
        if(isSuccess){
            JSONUtil.toJSON(resp,Result.ok("删除成功"));
        }else{
            JSONUtil.toJSON(resp,Result.error("删除失败"));
        }

        //resp.sendRedirect(req.getContextPath() + "/user");
    }
    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //System.out.println("UserServlet.deleteAll");
        String[] ids = req.getParameterValues("ids[]");
        boolean isSuccess = userService.deleteAll(ids);

        Result result = isSuccess ? Result.ok("删除成功") : Result.error("删除失败");
        JSONUtil.toJSON(resp, result);
    }
    private void updateStatus(HttpServletRequest req, HttpServletResponse resp) {
        //System.out.println("UserServlet.updateStatus");
        String id = req.getParameter("id");
        String status = req.getParameter("status");
        boolean isSuccess = userService.updateStatus(Integer.parseInt(id), Integer.parseInt(status));

        Result result = isSuccess ? Result.ok("更新状态成功") : Result.error("更新状态失败");
        JSONUtil.toJSON(resp, result);
    }
}
