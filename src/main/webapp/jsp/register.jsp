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
    <fmt:message bundle="${loc}" key="local.error.register.user" var="regUserError"/>
    <fmt:message bundle="${loc}" key="local.error.login" var="regLoginError"/>
    <fmt:message bundle="${loc}" key="local.error.register.eMail" var="regEMailError"/>
    <fmt:message bundle="${loc}" key="local.error.register.password" var="regPasswordError"/>
    <fmt:message bundle="${loc}" key="local.placeholder.password" var="passwordPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.placeholder.username" var="userNamePlaceholder"/>

    <title><c:out value="${signupButton}"/> - MotionPicture Bank [MPB]</title>
    <script type="text/javascript">
            function checkForm(form)
            {
                if(document.form.login.value === "") {
                    alert("Error: Username cannot be blank!");
                    form.login.focus();
                    return false;
                }
                re = /^\w+$/;
                if(!re.test(form.login.value)) {
                    alert("Error: Username must contain only letters, numbers and underscores!");
                    form.login.focus();
                    return false;
                }

                if(form.password.value !== "") {
                    if(form.password.value.length < 5 || form.password.value.length > 16 ) {
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

<div class="registerForm">
    <h4><c:out value="${signupMessage}"/></h4>
    <form action="${pageContext.request.contextPath}/mpb" method="post" onsubmit="checkForm(this);">
        <input type="hidden" name="command" value="register">
        <p align="right">
            <label for="login" ><c:out value="${username}"/>:</label>
            <input id="login" type="text" name="login" value=""
                   pattern="^[a-zA-Z0-9_]{3,}$" placeholder="${userNamePlaceholder}" required>
        </p>

        <p align="right">
            <label for="password" ><c:out value="${password}"/>:</label>
            <input id="password" type="password" name="password" value=""
                   pattern="^[a-zA-Z0-9!*_?@#$%^&]{5,}$" placeholder="${passwordPlaceholder}" required>
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
                <c:when test="${requestScope.registerError == 'user'}">
                    <c:out value="${regUserError}"/>
                </c:when>
                <c:when test="${requestScope.registerError == 'eMail'}">
                    <c:out value="${regEMailError}"/>
                </c:when>
                <c:when test="${requestScope.registerError == 'password'}">
                    <c:out value="${regPasswordError}"/>
                </c:when>

            </c:choose>

        </c:if>
        </p>
    </form>
</div>
</body>
</html>
