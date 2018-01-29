<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <c:import url="/WEB-INF/jsp/styling.jsp"/>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/user-form.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.message.signin" var="signinMessage"/>
    <fmt:message bundle="${loc}" key="local.navButton.login" var="login"/>
    <fmt:message bundle="${loc}" key="local.message.password" var="password"/>
    <fmt:message bundle="${loc}" key="local.message.oops" var="oops"/>
    <fmt:message bundle="${loc}" key="local.submit.signin" var="signinButton"/>
    <fmt:message bundle="${loc}" key="local.error.login.user" var="loginUserError"/>
    <fmt:message bundle="${loc}" key="local.error.login" var="loginError"/>
    <fmt:message bundle="${loc}" key="local.error.login.password" var="loginPasswordError"/>
    <fmt:message bundle="${loc}" key="local.error.username" var="usernamePatternMismatch"/>
    <fmt:message bundle="${loc}" key="local.error.password" var="passwordPatternMismatch"/>
    <fmt:message bundle="${loc}" key="local.error.password.empty" var="passwordEmpty"/>
    <fmt:message bundle="${loc}" key="local.error.username.empty" var="usernameEmpty"/>
    <fmt:message bundle="${loc}" key="local.message.password" var="passwordPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.message.username" var="userNamePlaceholder"/>
    <title><c:out value="${signinButton}"/> | MotionPicture Bank [MPB]</title>

</head>
<body>
<c:import url="/WEB-INF/jsp/header/header.jsp"/>
<div class="main">
    <nav class="side-nav"></nav>
    <article>
        <div class="container-fluid">
            <div class="row-fluid">

                <div class="col-md-offset-4 col-md-4" id="box">
                    <h2>${signinMessage}</h2>
                    <hr>
                    <form class="form-horizontal" action="${pageContext.request.contextPath}/mpb" method="post"
                          id="contact_form" data-toggle="validator" role="form">
                        <fieldset>
                            <input type="hidden" name="command" value="login">

                            <input type="hidden" name="address" value="${pageContext.request.getParameter("address")}"/>
                            <input type="hidden" name="query" value="${pageContext.request.getParameter("query")}"/>

                            <c:import url="/WEB-INF/jsp/form/usernameField.jsp"/>

                            <c:import url="/WEB-INF/jsp/form/passwordField.jsp"/>


                            <div class="form-group">

                                <div class="col-md-12">
                                    <button type="submit" class="btn btn-md btn-danger pull-right">${login}</button>
                                </div>
                            </div>

                        </fieldset>
                    </form>
                    <c:if test="${not empty requestScope.loginError}">
                        <div class="alert alert-warning alert-dismissable fade in">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
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
                        </div>
                    </c:if>

                </div>
            </div>

        </div>
    </article>
    <aside>

    </aside>

</div>
<c:import url="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
