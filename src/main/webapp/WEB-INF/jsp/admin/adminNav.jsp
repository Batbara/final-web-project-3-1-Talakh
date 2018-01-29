<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.admin.sidenav.users" var="usersNavButton"/>
    <fmt:message bundle="${loc}" key="local.admin.sidenav.content" var="contentNavButton"/>
</head>
<body>
<nav class="side-nav">
    <ul>
        <li>
            <form id="users-form" action="${pageContext.request.contextPath}/mpb" method="get">
                <input type="hidden" name="command" value="take_user_list"/>
                <a href="javascript:{}"
                   onclick="document.getElementById('users-form').submit(); return false;">
                    <div class="nav_button"><c:out value="${usersNavButton}"/></div>
                </a>

            </form>
            <ul></ul>
        </li>
        <li>
            <form id="content-form" action="${pageContext.request.contextPath}/mpb" method="get">
                <input type="hidden" name="command" value="take_content_list"/>
                <a href="javascript:{}"
                   onclick="document.getElementById('content-form').submit(); return false;">
                    <div class="nav_button"><c:out value="${contentNavButton}"/></div>
                </a>
            </form>
        </li>
    </ul>
</nav>
</body>
</html>
