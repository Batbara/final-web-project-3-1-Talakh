<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

    <link rel="stylesheet" type="text/css" href="/css/layout.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />

    <script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.message.signin" var="signinMessage"/>
    <fmt:message bundle="${loc}" key="local.message.login" var="logout"/>
    <fmt:message bundle="${loc}" key="local.message.password" var="password"/>
    <fmt:message bundle="${loc}" key="local.message.oops" var="oops"/>
    <fmt:message bundle="${loc}" key="local.submit.signin" var="signinButton"/>
    <fmt:message bundle="${loc}" key="local.error.login.user" var="loginUserError"/>
    <fmt:message bundle="${loc}" key="local.error.login" var="loginError"/>
    <fmt:message bundle="${loc}" key="local.error.login.password" var="loginPasswordError"/>
    <fmt:message bundle="${loc}" key="local.placeholder.password" var="passwordPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.placeholder.username" var="userNamePlaceholder"/>
    <title><c:out value="${signinButton}"/> | MotionPicture Bank [MPB]</title>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="main">
    <nav class="side-nav"></nav>
    <article>
        <c:if test="${requestScope.loginError == 'userIsBanned'}">
            <script>

                    $("#userIsBannedDialog").modal("show");


            </script>
        </c:if>
        <div class="loginForm">
            <h4 align="center"><c:out value="${signinMessage}"/></h4>

            <form id="form-login" action="${pageContext.request.contextPath}/mpb" method="post">
                <input type="hidden" name="command" value="login">
                <p align="right">
                    <label for="login"><c:out value="${logout}"/>:</label>
                    <input id="login" type="text" name="login" value=""
                           pattern="^[a-zA-Z0-9_]{3,}$" placeholder="${userNamePlaceholder}" required>

                </p>
                <p align="right">
                    <label for="password"><c:out value="${password}"/>:</label>
                    <input id="password" type="password" name="password" value=""
                           pattern="^[a-zA-Z0-9!*_?@#$%^&]{5,}$" placeholder="${passwordPlaceholder}" required>

                </p>
                <p id="button" align="center">

                    <input type="submit" value="${signinButton}">
                </p>
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
                        <c:when test="${requestScope.loginError == 'userIsBanned'}">

                            <mpb:ban-info user="${requestScope.user}"/>
                        </c:when>
                    </c:choose>

                </c:if>
            </p>
        </div>
        <div class="modal fade" id="useIsBannedDialog" role="dialog">
            <div class="modal-dialog modal-sm">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;
                        </button>
                        <h5 class="modal-title">${oops}</h5>
                    </div>
                    <div class="modal-body">
                        <div class="container">
                            <div class="row">

                            </div>
                            <div class="row">
                                <c:if test="${not empty requestScope.user}">
                                    <mpb:ban-info user="${requestScope.user}"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </article>
    <aside></aside>

</div>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

</body>
</html>
