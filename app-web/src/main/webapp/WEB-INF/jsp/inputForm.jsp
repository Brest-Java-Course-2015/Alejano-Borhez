<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title></title>
</head>
<body>

<form action="/submitUser" method="post">
  Login: <input type="text" name="login">
  Password: <input type="password" name="password">
  <input type="submit">
</form>
</body>
</html>