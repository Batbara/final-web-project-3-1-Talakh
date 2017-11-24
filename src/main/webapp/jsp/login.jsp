<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.message.signin" var="signinMessage"/>
    <fmt:message bundle="${loc}" key="local.message.login" var="logout"/>
    <fmt:message bundle="${loc}" key="local.message.password" var="password"/>
    <fmt:message bundle="${loc}" key="local.submit.signin" var="signinButton"/>
    <fmt:message bundle="${loc}" key="local.error.login.user" var="loginUserError"/>
    <fmt:message bundle="${loc}" key="local.error.login" var="loginError"/>
    <fmt:message bundle="${loc}" key="local.error.login.password" var="loginPasswordError"/>

    <title><c:out value="${signinButton}"/> - MotionPicture Bank [MPB]</title>

    <script type="text/javascript">
        function checkForm(form) {
            if (document.form.login.value === "") {
                alert("Error: Username cannot be blank!");
                form.login.focus();
                return false;
            }
            re = /^\w+$/;
            if (!re.test(form.login.value)) {
                alert("Error: Username must contain only letters, numbers and underscores!");
                form.login.focus();
                return false;
            }

            if (form.password.value !== "") {
                if (form.password.value.length < 5 || form.password.value.length > 16) {
                    alert("Error: Incorrect password length!");
                    form.password.focus();
                    return false;
                }

            } else {
                alert("Error: Please check that you've entered and confirmed your password!");
                form.password.focus();
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="loginForm">
    <h4 align="center"><c:out value="${signinMessage}"/></h4>

    <form action="${pageContext.request.contextPath}/mpb" method="post" onsubmit="return checkForm(this);">
        <input type="hidden" name="command" value="login">
        <p align="right">
            <label for="login"><c:out value="${logout}"/>:</label>
            <input id="login" type="text" name="login" value="" required>

        </p>
        <p align="right">
            <label for="password"><c:out value="${password}"/>:</label>
            <input id="password" type="password" name="password" value="" required>

        </p>
        <p id="button" align="center"><input type="submit" value="${signinButton}"></p>
    </form>

    <p id="error">
        <c:if test="${not empty requestScope.loginError}">
            <c:choose>
                <c:when test="${requestScope.loginError == 'user'}">
                    <c:out value="${loginUserError}"/>
                </c:when>
                <c:when test="${requestScope.loginError == 'login'}">
                    <c:out value="${loginError}"/>
                </c:when>
                <c:when test="${requestScope.loginError == 'password'}">
                    <c:out value="${loginPasswordError}"/>
                </c:when>

            </c:choose>

        </c:if>
    </p>
</div>
</body>
</html>
