package it.lh.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {
    private StringBuilder sensitiveWords = new StringBuilder();
    public void init(FilterConfig config) throws ServletException {
        //在这里做文章：将需要加载包含敏感词汇的文件打进来
        InputStream resourceAsStream = SensitiveWordsFilter.class.getClassLoader().getResourceAsStream("SensitiveWords.TXT");
        assert resourceAsStream != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String line;

        while (true){
            try {
                if ((line = bufferedReader.readLine()) == null)
                    break;
                else
                sensitiveWords.append(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(sensitiveWords.toString());

    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //在这里开始增强request的相关方法
        request.setCharacterEncoding("utf-8");
        HttpServletRequest request1 = (HttpServletRequest) request;
        //增强request1_动态代理
        HttpServletRequest getParameter = (HttpServletRequest)Proxy.newProxyInstance(request1.getClass().getClassLoader(), request1.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //在这里过滤对应的方法
                if (method.getName().equals("getParameter")) {
                    String s = sensitiveWords.toString();
                    //不要写object，需要写真实的代理类
                    String value = (String) method.invoke(request1, args);
                    System.out.println("replacePre:" + value);
                    //敏感词过滤：
                    if (value != null && s.contains(value)) {
                        value = "****";
                    }
                    System.out.println("replaceAfter" + value);
                    return value;
                }
                //不要写object，需要写真实的代理类
                return method.invoke(request1,args);
            }
        });
        chain.doFilter(getParameter,response);
    }
}
