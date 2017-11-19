<%--
  Created by IntelliJ IDEA.
  User: Barbarossa
  Date: 19.11.2017
  Time: 2:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>
    <h4>Sign in to your account now!</h4>
    <form action="${pageContext.request.contextPath}/mpb" method="post">
        <input type="hidden" name="command" value="login">
        Login:<br>
        <label>
            <input type="text" name="userLogin" value="">
        </label><br>
        Password:<br>
        <label>
            <input type="password" name="password" value="">
        </label><br>
        <p id="button"><input type="submit" value="Sign in!"></p>
    </form>
</body>
</html>
