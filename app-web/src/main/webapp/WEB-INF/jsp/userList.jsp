
<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 3.11.15
  Time: 2.28
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title><spring:message code="user.list"/></title>
</head>
<body>

<h1><spring:message code="user.list"/></h1>
<table border="1">
    <tr>
        <td>id</td>
        <td>login</td>
        <td>password</td>
    </tr>
    <c:forEach items="${dto.users}" var="user">
        <tr>
            <td>${user.userId}</td>
            <td>${user.login}</td>
            <td>${user.password}</td>
        </tr>
    </c:forEach>
</table>
<h3><spring:message code="user.total"/> ${dto.total}</h3>
<a href="/inputUser"><spring:message code="user.create"/></a>
</body>
</html>
