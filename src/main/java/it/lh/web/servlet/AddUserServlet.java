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

@WebServlet(name = "AddUserServlet", value = "/AddUserServlet")
public class AddUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //将得到的数据进行封装
        request.setCharacterEncoding("UTF-8");
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try{
            BeanUtils.populate(user,parameterMap);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImpl();
        boolean b = service.addUser(user);
        System.out.println("添加结果"+b);

        //
        if(!b){
            //添加失败：
            request.setAttribute("add_msg","添加失败！请重试！");
            request.getRequestDispatcher(request.getContextPath()+"/add.jsp").forward(request,response);
        }else{
            //添加成功,重定向到展示页面
            response.sendRedirect(request.getContextPath()+"/UserListServlet");
        }
    }
}
