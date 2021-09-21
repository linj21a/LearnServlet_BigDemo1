<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
    <head>
<%--    	<base href="<%=%>"/>--%>
        <!-- 指定字符集 -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>修改用户</title>

        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script src="js/jquery-2.1.0.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        
    </head>
    <body>
        <div class="container" style="width: 400px;">
        <h3 style="text-align: center;">修改联系人</h3>
        <form action="${pageContext.request.contextPath}/UpdateUserServlet" method="post">
            <input type="hidden" name="id" value="${requestScope.userInfo.id}">
          <div class="form-group">
            <label for="name">姓名：</label>
            <input type="text" class="form-control" id="name" name="name" value="${requestScope.userInfo.name}" readonly="readonly" placeholder="zhansan" />
          </div>

          <div class="form-group">
            <label >性别：</label>
              <c:if test="${requestScope.userInfo.gender.equals('男')}">
                  <input type="radio" name="gender" value="男" checked />男
                  <input type="radio" name="gender" value="女"  />女
              </c:if>
              <c:if test="${requestScope.userInfo.gender.equals('女')}">
                  <input type="radio" name="gender" value="男"  />男
                  <input type="radio" name="gender" value="女" checked />女
              </c:if>

          </div>

          <div class="form-group">
            <label for="age">年龄：</label>
            <input type="text" class="form-control" id="age" value="${requestScope.userInfo.age}" placeholder="18" name="age" />
          </div>

          <div class="form-group">
            <label for="address">籍贯：</label>
             <select name="address" id="address" class="form-control" >
                 <c:if test="${requestScope.userInfo.address.equals('广东')}">
                     <option value="广东" selected>广东</option>
                     <option value="广西">广西</option>
                     <option value="湖南">湖南</option>
                 </c:if>
                 <c:if test="${requestScope.userInfo.address.equals('广西')}">
                     <option value="广东" >广东</option>
                     <option value="广西" selected>广西</option>
                     <option value="湖南">湖南</option>
                 </c:if>
                 <c:if test="${requestScope.userInfo.address.equals('湖南')}">
                     <option value="广东" >广东</option>
                     <option value="广西">广西</option>
                     <option value="湖南" selected>湖南</option>
                 </c:if>
                 <c:if test="${
                 not(requestScope.userInfo.address.equals('湖南')) and
                  not(requestScope.userInfo.address.equals('湖南')) and
                 not (requestScope.userInfo.address.equals('广西'))
                 }">
                     <option value="广东" selected>广东</option>
                     <option value="广西">广西</option>
                     <option value="湖南" selected>湖南</option>
                 </c:if>

            </select>
          </div>

          <div class="form-group">
            <label for="qq">QQ：</label>
            <input type="text" class="form-control" name="qq" value="${requestScope.userInfo.qq}" id="qq" placeholder="123456"/>
          </div>

          <div class="form-group">
            <label for="email">Email：</label>
            <input type="text" class="form-control" name="email" value="${requestScope.userInfo.email}" id="email" placeholder="111@qq.com"/>
          </div>

             <div class="form-group" style="text-align: center">
                <input class="btn btn-primary" type="submit" value="提交" />
                <input class="btn btn-default" type="reset" value="重置" />
                <input class="btn btn-default" type="button" value="返回"/>
             </div>
        </form>
        </div>
    </body>
</html>