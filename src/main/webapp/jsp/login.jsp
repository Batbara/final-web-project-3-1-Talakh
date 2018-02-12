<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <c:import url="/WEB-INF/jsp/page_structure/styling.jsp"/>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form-style.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.user.message.signin" var="signinMessage"/>
    <fmt:message bundle="${loc}" key="local.user.login" var="login"/>
    <fmt:message bundle="${loc}" key="local.user.message.password" var="password"/>
    <fmt:message bundle="${loc}" key="local.message.oops" var="oops"/>
    <fmt:message bundle="${loc}" key="local.submit.signin" var="signinButton"/>
    <fmt:message bundle="${loc}" key="local.error.login.user" var="loginUserError"/>
    <fmt:message bundle="${loc}" key="local.error.login" var="loginError"/>
    <fmt:message bundle="${loc}" key="local.error.login.password" var="loginPasswordError"/>
    <fmt:message bundle="${loc}" key="local.error.username" var="usernamePatternMismatch"/>
    <fmt:message bundle="${loc}" key="local.error.password" var="passwordPatternMismatch"/>
    <fmt:message bundle="${loc}" key="local.error.field.empty" var="emptyField"/>
    <fmt:message bundle="${loc}" key="local.error.username.empty" var="usernameEmpty"/>
    <fmt:message bundle="${loc}" key="local.user.message.password" var="passwordPlaceholder"/>
    <fmt:message bundle="${loc}" key="local.user.message.username" var="userNamePlaceholder"/>
    <title><c:out value="${signinButton}"/> | MotionPicture Bank [MPB]</title>

</head>
<body>
<c:import url="/WEB-INF/jsp/page_structure/header.jsp"/>
<c:if test="${sessionScope.user ne null}">
    <c:redirect url="/index.jsp"/>
</c:if>
<div class="main">
    <nav class="side-nav"></nav>
    <article>
        <div class="container-fluid">
            <div class="row-fluid">

                <div class="col-md-offset-4 col-md-4 box">
                    <h2>${signinMessage}</h2>
                    <hr>
                    <form class="form-horizontal" action="${pageContext.request.contextPath}/mpb" method="post"
                          id="loginForm" data-toggle="validator" role="form">
                        <fieldset>
                            <input type="hidden" name="command" value="login">

                            <input type="hidden" name="address" value="${pageContext.request.getParameter("address")}"/>
                            <input type="hidden" name="query" value="${pageContext.request.getParameter("query")}"/>

                            <c:import url="/WEB-INF/jsp/user/usernameField.jsp"/>

                            <c:import url="/WEB-INF/jsp/user/passwordField.jsp"/>


                            <div class="form-group">

                                <div class="col-md-12">
                                    <button type="submit" class="btn btn-md btn-danger pull-right">${login}</button>
                                </div>
                            </div>

                        </fieldset>
                    </form>
                    <div id="errorContainer">
                    </div>

                </div>
            </div>

        </div>
    </article>
    <aside>

    </aside>

</div>
<c:import url="/WEB-INF/jsp/page_structure/footer.jsp"/>
</body>
</html>
