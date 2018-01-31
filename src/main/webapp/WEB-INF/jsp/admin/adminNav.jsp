<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.admin.sidenav.users" var="usersNavButton"/>
<fmt:message bundle="${loc}" key="local.admin.sidenav.content" var="contentNavButton"/>

<ul class="nav nav-stacked">
    <li>
        <form id="users-form" action="${pageContext.request.contextPath}/mpb" method="get">
            <input type="hidden" name="command" value="take_user_list"/>
            <a href="javascript:{}" class="side-nav-button"
               onclick="document.getElementById('users-form').submit(); return false;">
                <c:out value="${usersNavButton}"/>
            </a>

        </form>
        <ul></ul>
    </li>
    <li>
        <form id="reviews-moder-form" action="${pageContext.request.contextPath}/mpb" method="get">
            <input type="hidden" name="command" value="take_reviews_on_moder"/>
            <a href="javascript:{}" class="side-nav-button"
               onclick="document.getElementById('reviews-moder-form').submit(); return false;">
               <c:out value="${contentNavButton}"/>
            </a>
        </form>
    </li>
</ul>

