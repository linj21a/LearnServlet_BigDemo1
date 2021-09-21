package it.lh.web.servlet;

import it.lh.domain.User;
import it.lh.service.UserService;
import it.lh.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "UpdateUserServlet", value = "/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("UTF-8");
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();

        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService userService = new UserServiceImpl();
        userService.updateUser(user);
        //这里不要用重定向，不会触发查询所有信息的
//        response.sendRedirect(request.getContextPath()+"/list.jsp");
        response.sendRedirect(request.getContextPath()+"/UserListServlet");
//而转发不要添加request.getContextPath()
//        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }
}
