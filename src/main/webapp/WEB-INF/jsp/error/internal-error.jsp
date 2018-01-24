<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.error.internal" var="errorMessage"/>
    <fmt:message bundle="${loc}" key="local.title.error" var="errorTitle"/>
    <fmt:message bundle="${loc}" key="local.navButton.home" var="home" />

    <title><c:out value="${errorMessage}"/></title>
</head>
<body>
    <div>
        <p style="color: #ff2c34; font-size: 16px">
            <c:out value="${errorMessage}"/>
        </p>
        <a href="${pageContext.request.contextPath}/index.jsp">
            <div><c:out value="${home}" /></div>
        </a>
    </div>
</body>
</html>
