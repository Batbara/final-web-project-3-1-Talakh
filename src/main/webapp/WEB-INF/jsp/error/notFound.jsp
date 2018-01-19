<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/error-page.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.title.forbidden" var="title"/>
    <fmt:message bundle="${loc}" key="local.message.forbidden" var="forbiddenMessage"/>
    <fmt:message bundle="${loc}" key="local.navButton.home" var="home"/>
    <title>${title} | MotionPicture Bank [MPB]</title>
</head>
<body>
<div class="container">
    <div class="errorImage">
        <div>
            <img src="../../../images/403.png" alt="404" class="img-responsive center-block">
        </div>
    </div>
    <div class="row text-center">
        <div class="message">
            ${forbiddenMessage}
        </div>
    </div>
    <div class="row text-center">
        <div class="message">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
                <span class="glyphicon glyphicon-home"></span><span class="text"><c:out value="${home}"/></span>
            </a>

        </div>
    </div>
</div>
</body>
</html>
