package it.lh.web.servlet;

import it.lh.service.UserService;
import it.lh.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DelSelectUserServlet", value = "/DelSelectUserServlet")
public class DelSelectUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//将数据提取出来
        request.setCharacterEncoding("UTF-8");
        //获取单选框的选中的id值集合
        String[] uids = request.getParameterValues("uid");

        UserService userService = new UserServiceImpl();
//        不要这样做，这样业务的耦合性变高
//        for(String id:uids){
//            userService.deleteUser(uid);
//        }
        userService.deleteUsers(uids);

        //然后跳转到查询所有用户信息
        response.sendRedirect(request.getContextPath()+"/UserListServlet");


    }
}
