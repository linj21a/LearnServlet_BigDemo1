package it.lh.web.servlet;

import it.lh.domain.PageBean;
import it.lh.domain.User;
import it.lh.service.UserService;
import it.lh.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserListServlet", value = "/UserListServlet")
public class UserListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //1、调用UserService——service业务逻辑层提供的一个接口层完成查询
        UserService userService = new UserServiceImpl();
        List<User> userList = userService.findAll();
        System.out.println("userList个数："+userList.size());
        PageBean<User> pageBean = new PageBean<>();
        pageBean.setTotalCount(userList.size());
        pageBean.setRow(5);
        pageBean.setCurrentPage(1);
        pageBean.setList(userList);
        //计算总的页码
        int res = userList.size() % 5;
        int totalPage = res ==0?userList.size()/5:userList.size()/5+1;
        pageBean.setTotalPages(totalPage);
        //2、将userList存入request域
        //pageBean存入
        request.setAttribute("pageBean",pageBean);

        //3、转发到list.jsp
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }
}
