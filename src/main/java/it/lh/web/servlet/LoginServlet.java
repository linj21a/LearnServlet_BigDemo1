package it.lh.web.servlet;

import it.lh.domain.User;
import it.lh.service.UserService;
import it.lh.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "LoginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //登陆时数据会传到这里
        request.setCharacterEncoding("utf-8");
        //获取验证码
        String verifycode = request.getParameter("verifycode");
        //
        HttpSession sess = request.getSession();
        String service_checkCode = (String) sess.getAttribute("CHECKCODE_SERVER");
        //需要移除验证码
        sess.removeAttribute("CHECKCODE_SERVER");//保证了验证码不重复
        if(verifycode==null|| !verifycode.equalsIgnoreCase(service_checkCode)){
            //验证码不成功通过
            //给提示信息，
            request.setAttribute("login_msg","验证码错误！");
            //重定向到登陆界面，不给登录成功界面
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
        //验证码正确，则封装user对象
        Map<String,String[]> map = request.getParameterMap();
        User user = new User();
        try{
//            apache的beanutils自动将数据解析为对象
            BeanUtils.populate(user,map);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        //调用对应的service查询
        //判断user对象是否存在数据库表之中
        UserService userService = new UserServiceImpl();
        User loginUser = userService.login(user);

        if(loginUser==null){
            //登陆失败，给提示
            //给提示信息，
            request.setAttribute("login_msg","用户名或密码错误！");
            //重定向到登陆界面，不给登录成功界面
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
        //登陆成功
        sess.setAttribute("loginUser",loginUser);
        //重定向到登陆成功界面：
        response.sendRedirect(request.getContextPath()+"/index.jsp");

    }
}
