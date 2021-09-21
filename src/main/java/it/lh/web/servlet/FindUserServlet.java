package it.lh.web.servlet;

import it.lh.domain.User;
import it.lh.service.UserService;
import it.lh.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "FindUserServlet", value = "/findUserServlet")
public class FindUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 查找user，然后实现信息的回显
         */
        String id = request.getParameter("id");
        //查找该id的user 信息
        UserService userService = new UserServiceImpl();
        User user = userService.findUserById(id);

        //回显操作：
        request.setAttribute("userInfo",user);

        //转发到
//        request.getRequestDispatcher(request.getContextPath()+"/update.jsp").forward(request,response);
        request.getRequestDispatcher("/update.jsp").forward(request,response);


    }
}
