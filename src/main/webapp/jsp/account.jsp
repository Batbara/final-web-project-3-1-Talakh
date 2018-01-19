<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="mpb" uri="mpbtaglib" %>
<html>
<head>

    <link rel="stylesheet" type="text/css" href="/css/layout.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/account.css">
    <script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.navButton.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.navButton.register" var="register"/>
    <fmt:message bundle="${loc}" key="local.navButton.home" var="home"/>
    <fmt:message bundle="${loc}" key="local.navButton.login" var="toLogin"/>
    <fmt:message bundle="${loc}" key="local.title.profile" var="profile"/>

    <fmt:message bundle="${loc}" key="local.message.needToLogin" var="needToLogin"/>
    <fmt:message bundle="${loc}" key="local.info.user.status" var="status"/>
    <fmt:message bundle="${loc}" key="local.info.user.hereSince" var="since"/>
    <title> ${profile} | ${sessionScope.user.userName} | MotionPicture Bank [MPB]</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="main">
    <nav class="side-nav"></nav>
    <article>
        <c:choose>
        <c:when test="${not empty sessionScope.user}">

            <table>
                <tbody>
                    <tr>
                        <td>

                        </td>
                    </tr>

                </tbody>
            </table>
        <div class="container ">
            <div class="row">
                <div class="col-sm-2 col-lg-offset-2">
                    <img class="img-responsive user-avatar" src="${pageContext.request.contextPath}/images/avatar.jpg">
                </div>
                <div class="small-div">
                    ${sessionScope.user.userName}
                </div>
                <div class="small-div">
                    <span class="key">${status}</span> <span><mpb:user-status
                        userStatus="${sessionScope.user.userStatus}"/></span>
                </div>
                <div class="small-div">
                    <span class="key">${since}</span> <span class="value"><mpb:reg-date
                        user="${sessionScope.user}"/></span>
                </div>

            </div>
        </div>
        </c:when>
        <c:otherwise>
            <div class="container login-message">
                <div class="row text-center">
                    <div class="col-md-6 col-lg-offset-3 alert alert-warning text-center">${needToLogin} <a class="alert-link" href="${pageContext.request.contextPath}/login">${fn:toLowerCase(toLogin)}</a> </div>
                </div>
            </div>
        </c:otherwise>
        </c:choose>
    </article>
    <aside>
        <a href="javascript:" id="return-to-top"><i class="icon-chevron-up"></i></a>
    </aside>

</div>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
