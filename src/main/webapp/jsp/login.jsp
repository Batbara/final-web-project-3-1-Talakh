
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <input type="text" name="login" value="">
        </label><br>
        Password:<br>
        <label>
            <input type="password" name="password" value="">
        </label><br>
        <p id="button"><input type="submit" value="Sign in!"></p>
    </form>
    <c:if test="${not empty sessionScope.loginError}">
        <c:out value="${sessionScope.loginError}"/>
    </c:if>
</body>
</html>
