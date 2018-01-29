<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.show.sidenav.rating" var="ratingNav"/>
<fmt:message bundle="${loc}" key="local.show.sidenav.reviews" var="reviewsNav"/>
<fmt:message bundle="${loc}" key="local.show.sidenav.synopsis" var="synopsisNav"/>
<fmt:message bundle="${loc}" key="local.show.sidenav.add.review" var="addReview"/>

<fmt:message bundle="${loc}" key="local.show.sidenav.info" var="infoNav"/>
<ul class="nav nav-stacked" >
    <li>
        <a href="#info" class = "nav_button">
            <c:out value="${infoNav}"/>
        </a>
    </li>
    <li>
        <a href="#rating" class = "nav_button">
            <c:out value="${ratingNav}"/>
        </a>
    </li>
    <li>
        <a href="#synopsis" class = "nav_button">
            <c:out value="${synopsisNav}"/>
        </a>
    </li>
    <li>
        <a href="#reviews" class = "nav_button">
            <c:out value="${reviewsNav}"/>
        </a>
    </li>
    <li>
        <a href="#addReview" class = "nav_button">
            <c:out value="${addReview}"/>
        </a>
    </li>
</ul>