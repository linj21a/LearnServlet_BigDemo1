package it.lh.web.servlet;

import it.lh.domain.PageBean;
import it.lh.domain.User;
import it.lh.service.UserService;
import it.lh.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "FindUserByPageServlet", value = "/FindUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取参数
        request.setCharacterEncoding("utf-8");
        String currentPage = request.getParameter("currentPage");//当前页码
        String rows = request.getParameter("rows");//每页显示的行数

        if(currentPage==null||"".equals(currentPage)){
            currentPage = "1";
        }
        if(rows==null|| "".equals(rows)){
            rows = "4";
        }
        if(Integer.parseInt(currentPage)<1){
                //无须请求,此时数据没有变化
            currentPage = "1";
        }
        Map<String, String[]> parameterMap = request.getParameterMap();


        //调用service查询
        UserService userService = new UserServiceImpl();
        //查找当前页的数据
        PageBean<User> pb = userService.findUserByPage(currentPage.trim(),rows.trim(),parameterMap);

        //数据存放到request
        request.setAttribute("pageBean",pb);
//        查询条件回显
        request.setAttribute("condition",parameterMap);
        System.out.println(pb);
        //转发到list.jsp
        //服务器就使用相等目录
//        request.getRequestDispatcher(request.getContextPath()+"/list.jsp").forward(request,response);
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }
}
