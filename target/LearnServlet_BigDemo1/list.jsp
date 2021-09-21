<%--
  User: 60417
  Date: 2021/8/22
  Time: 21:49
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <script>
        function deleteUser(id){
            if(confirm("确定要删除用户吗？")){
                //是的话，将该标签的href定向
                location.href = "${pageContext.request.contextPath}/delUserServlet?id="+id;
            }else{

            }

        }
        window.onload =  function () {
            document.getElementById("deleteSelected").onclick = function () {
                if (confirm("真的要删除选中数据吗？")) {
                    var flag = false;
                    //判断是否有选中条目
                    var cbs = document.getElementsByName("uid");
                    for (var i = 0; i < cbs.length; i++) {
                        if (cbs[i].checked) {
                            //有一个条目选中了
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag) {//有条目被选中
                    //表单提交
                    document.getElementById("userInfoList").submit();
                }
            }
            //1.获取第一个cb
            document.getElementById("firstCk").onclick = function(){
                //2.获取下边列表中所有的cb
                var cbs = document.getElementsByName("uid");
                //3.遍历
                for (var i = 0; i < cbs.length; i++) {
                    //4.设置这些cbs[i]的checked状态 = firstCb.checked
                    cbs[i].checked = this.checked;
                }

            }
        }

    </script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>
<%--    添加一个内联表单：boostrap上的--%>
<%--    表单左浮动--%>
    <div class="row">
        <div style="float: left;">
<%--            分组查询--%>
            <form class="form-inline" action="${pageContext.request.contextPath}/FindUserByPageServlet" method="post">

                <div class="form-group">
                    <label for="name">姓名</label>
                    <input type="text" name="name" class="form-control" id="name" value="${requestScope.condition.name[0]}" >
                </div>
                <div class="form-group">
                    <label for="address" >籍贯</label>
                    <input type="text" name="address" class="form-control" id="address" value="${requestScope.condition.address[0]}" >
                </div>
                <div class="form-group">
                    <label for="email" >邮箱</label>
                    <input type="text" name="email" class="form-control" id="email" value="${requestScope.condition.email[0]}">
                </div>
                <button type="submit" class="btn btn-primary mb-2">查询</button>
            </form>
        </div>

        <%--    div往右边浮动 边距设为5px--%>
        <div style="float: right;margin-left: 5px">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>
<%--            点击不绑定href但是添加onclick事件，触发表单提交--%>
            <a class="btn btn-primary" href="javascript:void(0)" id="deleteSelected">删除选中</a>
        </div>
    </div>
    <form id="userInfoList" method="post" action="${pageContext.request.contextPath}/DelSelectUserServlet">
        <table border="1" class="table table-bordered table-hover">
                <tr class="success">
                    <%--            全选按钮--%>
                    <th><input type="checkbox" id="firstCk" ></th>
                    <th>编号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>年龄</th>
                    <th>籍贯</th>
                    <th>QQ</th>
                    <th>邮箱</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${requestScope.pageBean.list}" varStatus="list" var="user">
<%--                    现在使用分页，则改为pageBean--%>
                <tr>
                        <%--                注意单选框如果不写value，默认传过去的是on或者off--%>
                    <th><input type="checkbox" name="uid" value="${user.id}"></th>
                    <td>${list.count}</td>
                    <td>${user.name}</td>
                    <td>${user.gender}</td>
                    <td>${user.age}</td>
                    <td>${user.address}</td>
                    <td>${user.qq}</td>
                    <td>${user.email}</td>
                    <td>
                        <a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}">修改</a>&nbsp;
                        <a class="btn btn-default btn-sm" href="javascript:deleteUser(${user.id})">删除</a></td>
                </tr>
                </c:forEach>
        </table>
    </form>


    <div>
        <nav aria-label="Page navigation example">
            <ul class="pagination">

                    <c:if test="${requestScope.pageBean.currentPage<=1}">
                        <li class="disabled">
                        <a  href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                        </li>
                    </c:if>
                    <c:if test="${requestScope.pageBean.currentPage>1}">
                        <li class="page-item">
                            <a class="page-link"
<%--                               这里如果href换行，会把空格也带进去的--%>
                               href="${pageContext.request.contextPath}/FindUserByPageServlet?currentPage=${requestScope.pageBean.currentPage-1}&rows=${requestScope.pageBean.row}&name=${requestScope.condition.name[0]}&email=${requestScope.condition.email[0]}&address=${requestScope.condition.address[0]}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                        </li>
                    </c:if>
<%--                        等于1不允许再点了--%>


                <c:forEach begin="1" end="${requestScope.pageBean.totalPages}" var="curPage">

<%--                    点击的时候跳转到对应的页 --%>
                    <c:if test="${requestScope.pageBean.currentPage==curPage}">
                        <li class="active">
                            <a class="page-link" href="${pageContext.request.contextPath}/FindUserByPageServlet?currentPage=${curPage}&rows=${requestScope.pageBean.row}&name=${requestScope.condition.name[0]}&email=${requestScope.condition.email[0]}&address=${requestScope.condition.address[0]}">${curPage}</a></li>
                        <%--                    点击的时候跳转到对应的页 --%>
                    </c:if>
                    <c:if test="${requestScope.pageBean.currentPage!=curPage}">
                        <li class="page-item">
                            <a class="page-link" href="${pageContext.request.contextPath}/FindUserByPageServlet?currentPage=${curPage}&rows=${requestScope.pageBean.row}&name=${requestScope.condition.name[0]}&email=${requestScope.condition.email[0]}&address=${requestScope.condition.address[0]}">${curPage}</a></li>
                        <%--                    点击的时候跳转到对应的页 --%>
                    </c:if>

                </c:forEach>

                    <c:if test="${requestScope.pageBean.currentPage>=requestScope.pageBean.totalPages}">
                        <li class="disabled">
                        <a  href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                        </li>

                    </c:if>
                    <c:if test="${requestScope.pageBean.currentPage<requestScope.pageBean.totalPages}">
                        <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/FindUserByPageServlet?currentPage=${requestScope.pageBean.currentPage+1}&rows=${requestScope.pageBean.row}&name=${requestScope.condition.name[0]}&email=${requestScope.condition.email[0]}&address=${requestScope.condition.address[0]}"
                                   aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                        </li>
                    </c:if>

                <span style="font-size: 20px;margin-left: 5px;">
                    共${requestScope.pageBean.totalCount}条记录，共${requestScope.pageBean.totalPages}页
                </span>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>

