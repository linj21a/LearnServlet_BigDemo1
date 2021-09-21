<%--
  Created by IntelliJ IDEA.
  User: 60417
  Date: 2021/8/20
  Time: 0:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% System.out.println("service方法里面随便搞的方式");
    int i = 5;//局部变量
%>

<%! String s = "这是该jsp文件转为java文件时的成员变量或者静态变量等等,尽量少用，容易导致线程安全，servlet单例，所以不要定义！"; %>
<%! int i=5;//类的成员变量 %>
<%= i %>
<%--第三种方式定义的java代码，会获取其值输出到页面，通过转化后的java servlet的一个out对象来完成。--%>
</body>
</html>
