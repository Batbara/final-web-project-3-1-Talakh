<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.admin.link.users" var="usersNavButton"/>
<fmt:message bundle="${loc}" key="local.admin.link.content" var="contentNavButton"/>
<fmt:message bundle="${loc}" key="local.admin.link.reviews" var="reviewsModerNavButton"/>
<fmt:message bundle="${loc}" key="local.admin.add.movie" var="addMovieButton"/>
<fmt:message bundle="${loc}" key="local.admin.add.tv.show" var="addTvShowButton"/>
<ul class="nav nav-stacked">
    <li>
        <form id="users-form" action="${pageContext.request.contextPath}/mpb" method="get">
            <input type="hidden" name="command" value="take_user_list"/>
            <a href="javascript:{}" class="side-nav-button"
               onclick="document.getElementById('users-form').submit(); return false;">
                <c:out value="${usersNavButton}"/>
            </a>

        </form>
    </li>
    <li>
        <form id="reviews-moder-form" action="${pageContext.request.contextPath}/mpb" method="get">
            <input type="hidden" name="command" value="take_reviews_on_moder"/>
            <a href="javascript:{}" class="side-nav-button"
               onclick="document.getElementById('reviews-moder-form').submit(); return false;">
                <c:out value="${reviewsModerNavButton}"/>
            </a>
        </form>
    </li>
    <li>
        <form id="show-content-form" action="${pageContext.request.contextPath}/mpb" method="get">
            <input type="hidden" name="command" value="take_show_list"/>
            <a href="javascript:{}" class="side-nav-button"
               onclick="document.getElementById('show-content-form').submit(); return false;">
               ${contentNavButton}
            </a>
        </form>
    </li>
    <li>

        <a href="${pageContext.request.contextPath}/add/movie" target="_blank" class="side-nav-button">
            ${addMovieButton}
        </a>
    </li>
    <li>

        <a href="${pageContext.request.contextPath}/add/tv-show" target="_blank" class="side-nav-button">
            ${addTvShowButton}
        </a>
    </li>
</ul>

