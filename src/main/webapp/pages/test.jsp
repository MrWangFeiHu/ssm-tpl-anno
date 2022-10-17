<%--
  Created by IntelliJ IDEA.
  User: wangfeihu
  Date: 2022/10/16
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>CRUD TEST</title>
</head>
<body>
<table>
    <c:forEach items="${tests}" var="test">
        <tr>
            <td>${test.id}</td>
            <td>${test.name}</td>
            <td>
                <button onclick="let modifyForms = document.getElementsByName('modifyForm'); modifyForms.forEach(item => item.style.display = 'none'); document.getElementById('form${test.id}').style.display = 'inline'">修改</button>
                <button onclick="location.href='${pageContext.request.contextPath}/test/remove?id=${test.id}'">删除</button>
            </td>
            <td>
                <form id="form${test.id}" name="modifyForm" action="${pageContext.request.contextPath}/test/modify" method="post" style="display: none">
                    <input hidden name="id" value="${test.id}">
                    <input name="name" value="${test.name}">
                    <button type="submit">确认修改</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<form action="${pageContext.request.contextPath}/test/save" method="post">
    <label>
        <input name="name" id="name" placeholder="请输入测试名称~">
    </label>
    <button type="submit">提交</button>
</form>
</body>
</html>
