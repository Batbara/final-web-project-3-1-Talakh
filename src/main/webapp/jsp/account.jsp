
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>User profile - MotionPicture Bank [MPB]</title>
    <style>
        <jsp:include  page="/WEB-INF/css/stylesheet.css"/>
    </style>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local" var="loc" />
    <fmt:message bundle="${loc}" key="local.navButton.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.navButton.register" var="register" />
    <fmt:message bundle="${loc}" key="local.navButton.home" var="home" /></head>
<body>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
<h1>This is your account <c:out value="${sessionScope.user.userName}"/>, welcome!</h1>

</body>
</html>
