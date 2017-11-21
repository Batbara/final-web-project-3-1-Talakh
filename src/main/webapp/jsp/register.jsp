<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.message.signup" var="signupMessage"/>
    <fmt:message bundle="${loc}" key="local.message.username" var="username"/>
    <fmt:message bundle="${loc}" key="local.message.password" var="password"/>
    <fmt:message bundle="${loc}" key="local.submit.signup" var="signupButton"/>
    <fmt:message bundle="${loc}" key="local.error.register.login" var="regLoginError"/>

    <title><c:out value="${signupButton}"/> - MotionPicture Bank [MPB]</title>
    <script src="${pageContext.request.contextPath}/WEB-INF/js/validation.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="registerForm">
    <h4><c:out value="${signupMessage}"/></h4>
    <form action="${pageContext.request.contextPath}/mpb" method="post" onsubmit="checkForm(this);">
        <input type="hidden" name="command" value="register">
        <p align="right">
            <label for="login" ><c:out value="${username}"/>:</label>
            <input id="login" type="text" name="login" value="" required>
        </p>

        <p align="right">
            <label for="password" ><c:out value="${password}"/>:</label>
            <input id="password" type="password" name="password" value="" required>
        </p>

        <p align="right">
            <label for="eMail" >E-mail:</label>
            <input id="eMail" type="email" name="eMail" value="" required>
        </p>

        <p id="button" align="center"><input type="submit" value="${signupButton}"></p>
        <p id="error"><c:if test="${not empty requestScope.registerError}">
            <c:choose>
                <c:when test="${requestScope.registerError == 'login'}">
                    <c:out value="${regLoginError}"/>
                </c:when>

            </c:choose>

        </c:if>
        </p>
    </form>
</div>
</body>
</html>
