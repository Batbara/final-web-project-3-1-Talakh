<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<html>
<head>
    <c:import url="/WEB-INF/jsp/page_structure/styling.jsp"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/account.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/review.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.user.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.user.register" var="register"/>
    <fmt:message bundle="${loc}" key="local.link.home" var="home"/>
    <fmt:message bundle="${loc}" key="local.user.login" var="toLogin"/>
    <fmt:message bundle="${loc}" key="local.title.profile" var="profile"/>

    <fmt:message bundle="${loc}" key="local.response.message.authorization" var="needToLogin"/>
    <fmt:message bundle="${loc}" key="local.info.user.status" var="status"/>
    <fmt:message bundle="${loc}" key="local.info.user.hereSince" var="since"/>
    <title> ${profile} | ${sessionScope.user.userName} | MotionPicture Bank [MPB]</title>
</head>
<body>
<jsp:useBean id="user" class="by.tr.web.domain.User" type="by.tr.web.domain.User" scope="session"/>
<c:import url="/WEB-INF/jsp/page_structure/header.jsp"/>

<div class="main">
    <nav class="side-nav"></nav>
    <article>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <div class="holder">
                <div class="user-info-wrapper">
                    <span class="glyphicon glyphicon-user"></span>${sessionScope.user.userName}
                    <table class="table table-hover">
                        <tr>
                            <td class="type">
                                <c:out value="${status}"/>
                            </td>
                            <td>
                                <mpb:user-status userStatus="${sessionScope.user.userStatus}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="type">
                                <c:out value="${since}"/>
                            </td>
                            <td>
                                <fmt:formatDate pattern="dd.MM.yyyy"
                                                value="${sessionScope.user.registrationDate}"/>
                            </td>
                        </tr>
                    </table>
                </div>
                </div>


            </c:when>
            <c:otherwise>
                <div class="fluid-container login-message">
                    <div class="row text-center">
                        <div class="col-md-6 col-lg-offset-3 alert alert-warning text-center">${needToLogin}
                            <a class="alert-link"
                               href="${pageContext.request.contextPath}/login">${fn:toLowerCase(toLogin)}</a>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </article>
    <aside>
        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>
    </aside>

</div>
<c:import url="/WEB-INF/jsp/page_structure/footer.jsp"/>
</body>
</html>
