package com.syz.dormitory.filter;



import com.syz.dormitory.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// /*代表拦截所有的请求
@WebFilter(filterName = "login", urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("LoginFilter.doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String servletPath = request.getServletPath();
        //System.out.println("servletPath: " + servletPath);
        String method = request.getParameter("method");
        if(method==null){
            method="";
        }
        if (servletPath.endsWith(".jpg")
                || servletPath.endsWith(".png")
                || servletPath.endsWith(".woff2")
                || servletPath.endsWith(".woff")
                || servletPath.endsWith(".ttf")
                || servletPath.endsWith(".js")
                || servletPath.endsWith(".css")
                || servletPath.equals("/User/login.jsp")
                || servletPath.equals("/User/fail.jsp")
                || servletPath.equals("/User/register.jsp")
                || servletPath.equals("/verifyCode")
                || servletPath.equals("/user") && (method.equals("login")||method.equals("register"))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/User/login.jsp");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
