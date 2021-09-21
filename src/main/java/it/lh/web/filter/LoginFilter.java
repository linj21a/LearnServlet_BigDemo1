package it.lh.web.filter;

import it.lh.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登陆拦截器，没有登陆不允许访问资源
 */
//@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        if(requestURI.contains("/login.jsp")|| requestURI.contains("/js/")||
                requestURI.contains("/css/")|| requestURI.contains("/fonts/")|| requestURI.contains("/checkCodeServlet")
        ||requestURI.contains("/loginServlet")){
            filterChain.doFilter(servletRequest,servletResponse);
            //放行
        }else{
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("loginUser");
            if(user!=null){
                //登陆过了
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                request.setAttribute("login_msg","您尚未登陆，请登录！");
                request.getRequestDispatcher("/login.jsp").forward(request,servletResponse);
            }
        }
    }


}
